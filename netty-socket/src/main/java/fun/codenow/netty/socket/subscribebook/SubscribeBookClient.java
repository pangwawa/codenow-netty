package fun.codenow.netty.socket.subscribebook;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import fun.codenow.netty.socket.protobuf.SubscribeRespProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/2 18:04
 **/
public class SubscribeBookClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new ProtobufVarint32FrameDecoder())
                                .addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()))
                                .addLast(new ProtobufVarint32LengthFieldPrepender())
                                .addLast(new ProtobufDecoder(new MessageLite() {
                                    @Override
                                    public MessageLite getDefaultInstanceForType() {
                                        return null;
                                    }

                                    @Override
                                    public boolean isInitialized() {
                                        return false;
                                    }

                                    @Override
                                    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {

                                    }

                                    @Override
                                    public int getSerializedSize() {
                                        return 0;
                                    }

                                    @Override
                                    public Parser<? extends MessageLite> getParserForType() {
                                        return null;
                                    }

                                    @Override
                                    public ByteString toByteString() {
                                        return null;
                                    }

                                    @Override
                                    public byte[] toByteArray() {
                                        return new byte[0];
                                    }

                                    @Override
                                    public void writeTo(OutputStream outputStream) throws IOException {

                                    }

                                    @Override
                                    public void writeDelimitedTo(OutputStream outputStream) throws IOException {

                                    }

                                    @Override
                                    public Builder newBuilderForType() {
                                        return null;
                                    }

                                    @Override
                                    public Builder toBuilder() {
                                        return null;
                                    }
                                }))
                                .addLast(new SubscribeBookClientHandler());
                    }
                });
        try {
            ChannelFuture channelFuture=bootstrap.connect("localhost",8888).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
