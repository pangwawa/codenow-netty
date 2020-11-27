package fun.codenow.netty.socket.heart;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/11/27 17:14
 **/
public class HeartbeatClient  {
    private int port;
    private String host;
    public HeartbeatClient(String host,int port){
        this.port=port;
        this.host=host;
    }
    public void run() throws InterruptedException {
        Bootstrap bootstrap=new Bootstrap();
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        bootstrap
                .group(eventLoopGroup)
                .remoteAddress(new InetSocketAddress(port))
                .channel(NioSocketChannel.class)
                .handler(new CustomHeartbeatClientInitializer());
        try {
            ChannelFuture channelFuture= bootstrap.connect().sync();

            /*channelFuture.channel().close().sync();*/
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        HeartbeatClient heartbeatClient=new HeartbeatClient("localhost",8888);
        heartbeatClient.run();
    }
}
