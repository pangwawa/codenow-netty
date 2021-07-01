package fun.codenow.netty.heartbeat.client.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/7 17:22
 **/
@Slf4j
@Component
public class Client {
    private NioEventLoopGroup eventLoopGroup;
    private Bootstrap bootstrap;
    public Channel channel;
    private String serverAddr="localhost";
    private int port=8888;
    @Autowired
    CustomClientChannelInitializer customClientChannelInitializer;
    @PostConstruct
    public  void run() throws InterruptedException {
        connect();
        //ChannelFuture channelFuture=bootstrap.connect().sync();
        //channelFuture.channel().closeFuture().sync();
        //客户端自动注册、在断开连接后定期进行重连操作
    }
    @PreDestroy
    public void destroy(){
        eventLoopGroup.shutdownGracefully();
    }

    public void connect() throws InterruptedException {
        if (channel!=null&&channel.isActive()){
            return;
        }
        eventLoopGroup=new NioEventLoopGroup();
        bootstrap=new Bootstrap();
        bootstrap
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(customClientChannelInitializer);
        ChannelFuture channelFuture=bootstrap.connect(serverAddr,port).sync();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    channel=channelFuture.channel();
                    log.info("服务器启动成功");
                }else {
                    log.error("服务器启动失败，正在进行重连……");
                    try {
                        connect();
                    } catch (InterruptedException e) {
                        log.error("连接出现异常，message：{}",e.getMessage());
                    }
                }
            }
        });
    }
}
