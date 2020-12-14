package fun.codenow.netty.socket.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author Jack Wu
 * @Description
 *
 * 创建一个 Bootstrap 来初始化客户端
 * 一个 NioEventLoopGroup 实例被分配给处理该事件的处理，这包括创建新的连接和处理入站和出站数据
 * 创建一个 InetSocketAddress 以连接到服务器
 * 连接好服务器之时，将安装一个 EchoClientHandler 在 pipeline
 * 之后 Bootstrap.connect（）被调用连接到远程的 - 本例就是 echo(回声)服务器。
 *
 * @Version V1.0
 * @Date2020/11/25 16:43
 **/
public class EchoClient {
    public static void main(String[] args) {
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();

        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress( new InetSocketAddress(8999))
                .handler(new ChannelInitializer<SocketChannel>() {    //5
                    @Override
                    public void initChannel(SocketChannel ch)
                            throws Exception {
                        ch.pipeline().addLast(
                                new EchoClientHandler());
                    }
                });
        try {
            ChannelFuture channelFuture=bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
