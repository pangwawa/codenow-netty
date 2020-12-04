package fun.codenow.netty.privateprotocol.client;

import fun.codenow.netty.privateprotocol.protobuf.CustomMessageProto;
import fun.codenow.netty.privateprotocol.struct.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/3 17:16
 **/
@Slf4j
public class HeartBeatRequestHandler extends ChannelInboundHandlerAdapter {

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
        log.info("客户端 active");
        byte[] requestByte="client ready".getBytes();
        ByteBuf requestByteBuf= Unpooled.buffer(requestByte.length);
        requestByteBuf.writeBytes(requestByte);
        ctx.writeAndFlush(requestByteBuf);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CustomMessageProto.CustomMessage message= (CustomMessageProto.CustomMessage) msg;
        if (message.getHeader().getType().getNumber()== MessageType.PONG.value()){
            log.info("接收心跳消息：{}",message);
        }else {
            log.warn("消息未处理：{}",message);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.warn("触发userEventTriggered 事件");

        if (evt instanceof IdleStateEvent){
            IdleStateEvent event= (IdleStateEvent) evt;
            if (event.state().equals(IdleState.WRITER_IDLE)) {
                CustomMessageProto.CustomMessage.Builder heartBeatMsg=
                        CustomMessageProto.CustomMessage.newBuilder().setHeader(
                                CustomMessageProto.CustomMessage.CustomHeader.newBuilder()
                                        .setTypeValue(0xABEF)
                                        .setType(CustomMessageProto.CustomMessage.CustomHeader.MessgeType.PING)
                        );
                ctx.channel().writeAndFlush(heartBeatMsg);
            }
        }
        super.userEventTriggered(ctx, evt);
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
