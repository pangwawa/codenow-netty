package fun.codenow.netty.socket.test;

import fun.codenow.netty.socket.demo.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/11/26 10:21
 **/
public class EchoServerTest {
    public static void main(String[] args) throws InterruptedException {
        /**
         * Bootstrap是应用程序的开始，作用是配置整个netty程序，串联各个组件
         */
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        NioEventLoopGroup nioEventLoopGroup=new NioEventLoopGroup();
        serverBootstrap
                .group(nioEventLoopGroup)
                /**
                 *  Channel  代表一个Socket连接，或者其他和IO操作相关的组件，它和EventLoop一起参加IO处理
                 */
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(9999))
                /**
                 * Handler 是为了支持各种协议和处理数据的方式; 主要是处理连接、数据接收、异常、数据转换等事件
                 *
                 * ChannelInitializer 用于配置Handler， 它提供ChannelPipeline,并把设置的Handler加到ChannelPipeline
                 */
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        /**
                         * ChannelPipeline  一个Netty应用基于ChannelPipeline机制，这种机制依赖于EventLoop和EventLoopGroup
                         */
                        socketChannel.pipeline().addLast(new EchoServerHandler());
                    }
                });
        try {
            /**
             *  Future和ChannelFuture ，注册监听，当操作成功或失败时自动触发，所有的操作都会返回一个ChannelFuture， 服务端用bind()设置占用的端口号
             */
            ChannelFuture channelFuture= serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            /**
             * 关闭连接
             */
            nioEventLoopGroup.shutdownGracefully().sync();
        }
    }
}
