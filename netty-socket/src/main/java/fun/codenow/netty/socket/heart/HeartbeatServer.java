package fun.codenow.netty.socket.heart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/11/26 17:58
 **/
public class HeartbeatServer {
    private int port;
    private String host;
    public HeartbeatServer(int port,String host){
        this.port=port;
        this.host=host;
    }

    public void run() throws InterruptedException {
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        EventLoopGroup bossEventLoopGroup=new NioEventLoopGroup();
        EventLoopGroup workerEventLoopGroup=new NioEventLoopGroup();
        serverBootstrap
                .group(bossEventLoopGroup,workerEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new CustomHeartBeatInitializer());
        try {
            ChannelFuture channelFuture=serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossEventLoopGroup.shutdownGracefully();
            workerEventLoopGroup.shutdownGracefully();
        }


    }
    public static void main(String[] args) throws InterruptedException {
        HeartbeatServer heartbeatServer=new HeartbeatServer(8888,"localhost");
        heartbeatServer.run();
    }
}
