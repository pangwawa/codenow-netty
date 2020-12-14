package fun.codenow.netty.socket.timeserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/2 9:18
 **/
public class TimeServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new TimeServerHandler());
    }
}
