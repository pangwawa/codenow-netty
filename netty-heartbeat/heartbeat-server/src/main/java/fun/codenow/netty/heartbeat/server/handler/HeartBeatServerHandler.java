package fun.codenow.netty.heartbeat.server.handler;

import fun.codenow.netty.common.CustomMessageProto;
import io.netty.channel.ChannelHandler;
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
 * @Date2020/12/7 17:56
 **/
@Slf4j
@Component
@ChannelHandler.Sharable
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {
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
        CustomMessageProto.CustomMessage message= (CustomMessageProto.CustomMessage) msg;
        if (message.getHeader().getType().getNumber()== CustomMessageProto.CustomMessage.CustomHeader.MessgeType.PING_VALUE){
            log.info("接收心跳消息：{}",message);
            CustomMessageProto.CustomMessage.Builder heartBeatRespMsg=CustomMessageProto.CustomMessage.newBuilder()
                    .setHeader(
                            CustomMessageProto.CustomMessage.CustomHeader.newBuilder()
                            .setTypeValue(0xABEF)
                            .setType(CustomMessageProto.CustomMessage.CustomHeader.MessgeType.PONG)
                    );
            ctx.channel().writeAndFlush(heartBeatRespMsg);
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
        //超过40s没接收心跳消息则强制断开连接，从列表移除
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event= (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)){
                //判断上一次心跳时间，如果超过40s则直接断开连接
                //否则尝试发送心跳
                log.info("已经5秒没有收到客户端心跳消息了，发送心跳检测给客户端");
                CustomMessageProto.CustomMessage.Builder heartBeatRespMsg=CustomMessageProto.CustomMessage.newBuilder()
                        .setHeader(
                                CustomMessageProto.CustomMessage.CustomHeader.newBuilder()
                                .setTypeValue(0xABEF)
                                .setType(CustomMessageProto.CustomMessage.CustomHeader.MessgeType.PONG)
                        );
                ctx.channel().writeAndFlush(heartBeatRespMsg);
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
        log.error("发生异常，message：{}",cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}
