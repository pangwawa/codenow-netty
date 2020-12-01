package fun.codenow.netty.socket.heartbeat.client;

import fun.codenow.netty.socket.heartbeat.CustomProtocol;
import fun.codenow.netty.socket.heartbeat.HeartBeatServer;
import fun.codenow.netty.socket.heartbeat.util.SpringBeanFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/1 10:47
 **/
@Slf4j
public class EchoClientHandle extends SimpleChannelInboundHandler<ByteBuf> {
    @Autowired
    HeartBeatServer heartBeatServer;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        log.info("收到服务端消息:{}"+byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;

            if (idleStateEvent.state() == IdleState.WRITER_IDLE){
                log.info("已经 10 秒没有发送信息！");
                //向服务端发送消息
                CustomProtocol heartBeat = SpringBeanFactory.getBean("heartBeat", CustomProtocol.class);
                ctx.writeAndFlush(heartBeat).addListener(ChannelFutureListener.CLOSE_ON_FAILURE) ;
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}
