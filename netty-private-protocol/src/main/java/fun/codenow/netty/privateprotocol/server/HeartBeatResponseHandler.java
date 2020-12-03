package fun.codenow.netty.privateprotocol.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/3 17:13
 **/
@Slf4j
public class HeartBeatResponseHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf messageByteBuf= (ByteBuf) msg;
        byte[] messageByte=new byte[messageByteBuf.readableBytes()];
        messageByteBuf.readBytes(messageByte);
        String messageStr=new String(messageByte,"UTF-8");
        log.info(" 服务端消息：{}",messageStr);
        if (messageStr.equalsIgnoreCase("client ready")||messageStr.equalsIgnoreCase("heartbeat request")){
            byte[] requestByte="heartbeat response".getBytes();
            ByteBuf requestByteBuf= Unpooled.buffer(requestByte.length);
            requestByteBuf.writeBytes(requestByte);
            ctx.writeAndFlush(requestByteBuf);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.warn("已经3秒未收到客户端消息");
        //发送心跳消息
        byte[] requestByte="heartbeat response".getBytes();
        ByteBuf requestByteBuf= Unpooled.buffer(requestByte.length);
        requestByteBuf.writeBytes(requestByte);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
