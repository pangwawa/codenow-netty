package fun.codenow.netty.socket.test;

import fun.codenow.netty.socket.demo.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/11/26 10:21
 **/
public class EchoClientTest {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup nioEventLoopGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap
                .group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                /**
                 * 与服务端的localAddress不同，这里是remoteAddress，配置远程连接 服务器地址和端口
                 */
                .remoteAddress(new InetSocketAddress(12120))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        /**
                         * 这里配置的Handler是客户端需要使用的Handler
                         */
                        socketChannel.pipeline().addLast(new EchoClientHandler());
                    }
                });
        try {
            /**
             * 这里是进行连接，客户端使用connect() 方法进行服务端连接
             */
            ChannelFuture channelFuture=bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            nioEventLoopGroup.shutdownGracefully().sync();
        }

    }
}
