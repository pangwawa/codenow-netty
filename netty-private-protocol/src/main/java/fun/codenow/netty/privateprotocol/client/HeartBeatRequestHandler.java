package fun.codenow.netty.privateprotocol.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/3 17:16
 **/
@Slf4j
public class HeartBeatRequestHandler extends ChannelInboundHandlerAdapter {
    private static Integer WRITEIDLE =0;

    private final ScheduledExecutorService executorService=new ScheduledThreadPoolExecutor(2, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("custom HeartBeat sender");
            return thread;
        }
    });

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
        ByteBuf messageBuf= (ByteBuf) msg;
        byte[] messageByte = new byte[messageBuf.readableBytes()];
        messageBuf.readBytes(messageByte);
        String messageStr = new String(messageByte, "UTF-8");
        if (messageStr.equalsIgnoreCase("heartbeat response")){
           log.info("心跳消息，{}",messageStr);
           /*Thread.sleep(3000);
            byte[] requestByte="heartbeat request".getBytes();
            ByteBuf requestByteBuf= Unpooled.buffer(requestByte.length);
            requestByteBuf.writeBytes(requestByte);
            ctx.writeAndFlush(requestByteBuf);*/
        }else {
            log.warn("消息未处理：{}",messageStr);
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
            byte[] requestByte="heartbeat request".getBytes();
            ByteBuf requestByteBuf= Unpooled.buffer(requestByte.length);
            requestByteBuf.writeBytes(requestByte);
            ctx.writeAndFlush(requestByteBuf);

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
