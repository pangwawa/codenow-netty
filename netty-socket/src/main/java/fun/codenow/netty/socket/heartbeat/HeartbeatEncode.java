package fun.codenow.netty.socket.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/1 10:22
 **/
public class HeartbeatEncode  extends MessageToByteEncoder<CustomProtocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CustomProtocol customProtocol, ByteBuf byteBuf) throws Exception {
        byteBuf.writeLong(customProtocol.getId()) ;
        byteBuf.writeBytes(customProtocol.getContent().getBytes()) ;
    }
}
