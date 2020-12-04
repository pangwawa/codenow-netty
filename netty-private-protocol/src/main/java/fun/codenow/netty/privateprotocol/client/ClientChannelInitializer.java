package fun.codenow.netty.privateprotocol.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/3 17:15
 **/
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new HeartBeatRequestHandler())
               /* .addLast(new IdleStateHandler(0,4,0, TimeUnit.SECONDS))*/;
    }
}
