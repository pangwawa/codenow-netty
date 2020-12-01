package fun.codenow.netty.socket.coder.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/1 11:50
 **/
public class ServerOne {
    public static void main(String[] args) {
        EventLoopGroup bossEventGroup=new NioEventLoopGroup();
        EventLoopGroup workerEventGroup=new NioEventLoopGroup();

        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap
                .group(bossEventGroup,workerEventGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(8686))
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {

                    }
                });
    }
}
