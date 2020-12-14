package fun.codenow.netty.socket.demo;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/11/25 10:14
 **/
@ServerEndpoint("/socket/{connectionId}")
@Component
@Slf4j
public class WebSocketServer {
    private static Set<String> connectionIdSet= new  ConcurrentHashSet();
    private static ConcurrentHashMap<String , Session> socketSessionPools=new ConcurrentHashMap<>();

    @OnOpen
    public void onSocketOpen(Session session, @PathParam(value = "connectionId") String connectionId){
        socketSessionPools.put(connectionId,session);
        connectionIdSet.add(connectionId);
        log.info("当前在线人数:{}",connectionIdSet.size());
        log.info("所有connctionId:{}",new Gson().toJson(connectionIdSet));
    }

    @OnMessage
    public void onMessage(String message, @PathParam(value = "connectionId") String connectionId){
        log.info("收到客户端{}的消息：{}",connectionId,message);
        //回复消息
        sendMessageBySessionId(connectionId,"服务端已接收消息："+message);
    }

    @OnClose
    public void onClose( @PathParam(value = "connectionId") String connectionId){
        socketSessionPools.remove(connectionId);
        connectionIdSet.remove(connectionId);
        log.info("客户端{}断开连接，当前连接数：{}",connectionId,connectionIdSet.size());
    }

    @OnError
    public void OnError(Session session, Throwable throwable, @PathParam(value = "connectionId") String connectionId){
        log.error("socket连接异常,connectionId:{},errormessage:{}",connectionId,throwable.getMessage());
    }

    /**
     *  send message by connectionId
     */
    public void sendMessageBySessionId(String connectionId,String message){
        Session session=socketSessionPools.get(connectionId);
        session.getAsyncRemote().sendText(message);
    }
    /**
     *  send message to all connectionId
     */
    public void sendMessageToAll(String message){
        for (Session session: socketSessionPools.values()){
            if (session!=null){
                synchronized (session){
                    session.getAsyncRemote().sendText(message);
                }
            }
        }
    }
}
