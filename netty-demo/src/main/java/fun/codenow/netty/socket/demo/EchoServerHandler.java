package fun.codenow.netty.socket.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Jack Wu
 * @Description
 *
 *  一个Echo服务器 需要：
 *  handler：这个组件实现了服务器的业务逻辑，决定了连接创建后和接收到信息后该如何处理
 * Bootstrapping： 这个是配置服务器的启动代码。最少需要设置服务器绑定的端口，用来监听连接请求。
 *
 *
 *     实现 ChannelInboundHandler 接口
 *
 *      继承ChannelInboundHandlerAdapter （提供了默认 ChannelInboundHandler 的实现）
 *
 *          channelRead() - 每个信息入站都会调用
 *          channelReadComplete() - 通知处理器最后的
 *          channelread() 是当前批处理中的最后一条消息时调用
 *          exceptionCaught()- 读操作时捕获到异常时调用
 *
 * @Version V1.0
 * @Date2020/11/25 16:43
 **/
@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    public EchoServerHandler() {
        super();
    }

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
        ByteBuf byteBuf=(ByteBuf) msg;
        log.info("channelRead 调用：{}",byteBuf.toString(CharsetUtil.UTF_8));
        ctx.write(byteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Echo 抛出异常，{}",cause.getMessage());
        ctx.close();
    }
}
