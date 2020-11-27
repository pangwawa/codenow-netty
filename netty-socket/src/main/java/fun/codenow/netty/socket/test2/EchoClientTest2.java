package fun.codenow.netty.socket.test2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/11/27 9:30
 **/
public class EchoClientTest2 {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup nioEventLoopGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                .channel(SocketChannel.class)
                .remoteAddress(new InetSocketAddress(8777))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                    }
                });
        try {
            ChannelFuture channelFuture= bootstrap.connect().sync();
            channelFuture.channel().close().sync();
        } finally {
            nioEventLoopGroup.shutdownGracefully().sync();
        }

    }
}
