package fun.codenow.netty.heartbeat.client.handler;

import fun.codenow.netty.common.CustomMessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/11 14:19
 **/
@Slf4j
@Component
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {
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
        CustomMessageProto.CustomMessage.Builder heartBeatMsg=CustomMessageProto.CustomMessage.newBuilder()
                .setHeader(
                        CustomMessageProto.CustomMessage.CustomHeader.newBuilder()
                        .setTypeValue(0xABEF)
                        .setType(CustomMessageProto.CustomMessage.CustomHeader.MessgeType.PING)
                );
        ctx.channel().writeAndFlush(heartBeatMsg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("连接断开，channelInactive() call ");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CustomMessageProto.CustomMessage message= (CustomMessageProto.CustomMessage) msg;
        if (message.getHeader().getType().equals(CustomMessageProto.CustomMessage.CustomHeader.MessgeType.PONG)){
            log.info("接收到心跳消息：{}",message);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            log.info("心跳检测触发了");
            IdleStateEvent idleStateEvent= (IdleStateEvent) evt;
            if (idleStateEvent.state().equals(IdleState.WRITER_IDLE)){
                log.warn("已经5秒钟没有写操作了，发个心跳消息检测下");
                CustomMessageProto.CustomMessage.Builder heartBeatReqMsg=CustomMessageProto.CustomMessage.newBuilder()
                        .setHeader(
                                CustomMessageProto.CustomMessage.CustomHeader.newBuilder()
                                .setTypeValue(0xABEF)
                                .setType(CustomMessageProto.CustomMessage.CustomHeader.MessgeType.PING)
                        );
                ctx.channel().writeAndFlush(heartBeatReqMsg);
            }
        }
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
