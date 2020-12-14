package fun.codenow.netty.socket.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/1 10:20
 **/
public class HeartbeatDecoder  extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        long id = byteBuf.readLong() ;
        byte[] bytes = new byte[byteBuf.readableBytes()] ;
        byteBuf.readBytes(bytes) ;
        String content = new String(bytes) ;

        CustomProtocol customProtocol = new CustomProtocol() ;
        customProtocol.setId(id);
        customProtocol.setContent(content) ;
        list.add(customProtocol) ;
    }
}
