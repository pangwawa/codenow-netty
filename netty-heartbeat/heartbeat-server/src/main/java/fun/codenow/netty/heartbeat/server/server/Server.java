package fun.codenow.netty.heartbeat.server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/7 17:23
 **/
@Component
public class Server {
    private NioEventLoopGroup bossGroup=new NioEventLoopGroup();
    private NioEventLoopGroup workerGroup=new NioEventLoopGroup();
    private ServerBootstrap serverBootstrap=new ServerBootstrap();
    @Autowired
    CustomServerChannelInitializer customServerChannelInitializer;
    @PostConstruct
    public  void run() throws InterruptedException {
        serverBootstrap
                .group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(8983))
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(customServerChannelInitializer);
            ChannelFuture channelFuture=serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
            //服务端监控客户端连接是否健康，定期进行健康检查

    }
    @PreDestroy
    public void destroy(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
