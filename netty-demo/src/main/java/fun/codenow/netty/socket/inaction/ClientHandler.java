package fun.codenow.netty.socket.inaction;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/3 16:04
 **/
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("  日志： ClientHandler 调用channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("  日志： ClientHandler 调用channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("  日志： ClientHandler 调用channelActive");
        byte[] req = "client ready".getBytes();
        ByteBuf firstMessage  = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("  日志： ClientHandler 调用channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("  日志： ClientHandler 调用channelRead");
        log.info("msg:" +msg);
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("client 接收到消息 : " + body);
        if (body.equalsIgnoreCase("heartbeat request")){
            byte[] respbytes="heartbeat response".getBytes();
            ByteBuf respMsg=Unpooled.buffer(respbytes.length);
            respMsg.writeBytes(respbytes);
            Thread.sleep(2000);
            ctx.writeAndFlush(respMsg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("  日志： ClientHandler 调用channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("  日志： ClientHandler 调用userEventTriggered");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.info("  日志： ClientHandler 调用channelWritabilityChanged");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("  日志： ClientHandler 调用exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }
}
