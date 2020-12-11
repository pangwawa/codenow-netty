package fun.codenow.netty.heartbeat.client.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/7 17:22
 **/
@Slf4j
@Component
public class Client {
    private NioEventLoopGroup eventLoopGroup=new NioEventLoopGroup();
    private Bootstrap bootstrap=new Bootstrap();
    @Autowired
    CustomClientChannelInitializer customClientChannelInitializer;
    @PostConstruct
    public  void run() throws InterruptedException {
        bootstrap
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(8983))
                .handler(customClientChannelInitializer);
        ChannelFuture channelFuture=bootstrap.connect().sync();
        channelFuture.channel().closeFuture().sync();
        //客户端自动注册、在断开连接后定期进行重连操作
    }
    @PreDestroy
    public void destroy(){
        eventLoopGroup.shutdownGracefully();
    }
}
