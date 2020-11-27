package fun.codenow.netty.socket.test2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/11/27 9:38
 **/
public class EchoClientTest2ChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {

    }
}
