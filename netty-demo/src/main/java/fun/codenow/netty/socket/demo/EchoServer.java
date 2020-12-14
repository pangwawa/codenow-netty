package fun.codenow.netty.socket.demo;

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
 *
 * 1.设置端口值（抛出一个 NumberFormatException 如果该端口参数的格式不正确）
 *
 * 2.呼叫服务器的 start() 方法
 *
 * 3.创建 EventLoopGroup
 *
 * 4.创建 ServerBootstrap
 *
 * 5.指定使用 NIO 的传输 Channel
 *
 * 6.设置 socket 地址使用所选的端口
 *
 * 7.添加 EchoServerHandler 到 Channel 的 ChannelPipeline
 *
 * 8.绑定的服务器;sync 等待服务器关闭
 *
 * 9.关闭 channel 和 块，直到它被关闭
 *
 * 10.关机的 EventLoopGroup，释放所有资源。
 *
 * @Version V1.0
 * @Date2020/11/25 16:58
 **/
public class EchoServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup nioEventLoopGroup=new NioEventLoopGroup();

        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap
                .group(nioEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(8999))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                            throws Exception {
                        ch.pipeline().addLast(
                                new EchoServerHandler());
                    }
                });
        try {
            ChannelFuture f = serverBootstrap.bind().sync();
            System.out.println(EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            nioEventLoopGroup.shutdownGracefully().sync();
        }


    }
}
