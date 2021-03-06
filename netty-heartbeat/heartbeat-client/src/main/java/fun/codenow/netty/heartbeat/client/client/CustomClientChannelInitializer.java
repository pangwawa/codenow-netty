package fun.codenow.netty.heartbeat.client.client;

import fun.codenow.netty.common.CustomMessageProto;
import fun.codenow.netty.heartbeat.client.handler.HeartBeatClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/11 14:14
 **/
@Component
public class CustomClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    HeartBeatClientHandler heartBeatClientHandler;
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel
                .pipeline()
                .addLast(new IdleStateHandler(0,5,0, TimeUnit.SECONDS))
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(CustomMessageProto.CustomMessage.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(heartBeatClientHandler);
    }
}
