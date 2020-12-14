package fun.codenow.netty.socket.test2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/11/27 9:30
 **/
public class EchoServerTest2 {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup nioEventLoopGroup=new NioEventLoopGroup();
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap
                .group(nioEventLoopGroup)
                .channel(ServerSocketChannel.class)
                .localAddress(new InetSocketAddress(8777))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast();
                    }
                });
        try {
            ChannelFuture channelFuture= serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            nioEventLoopGroup.shutdownGracefully().sync();
        }


    }
}
