package fun.codenow.netty.handler;

import fun.codenow.netty.message.CustomMessageProto;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2021/6/30 15:51
 **/
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new IdleStateHandler(10,0,0, TimeUnit.SECONDS))

                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(CustomMessageProto.CustomMessage.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(new HeartBeatResponseHandler());
    }
}
