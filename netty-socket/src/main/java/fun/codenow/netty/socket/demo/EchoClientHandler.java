package fun.codenow.netty.socket.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author Jack Wu
 * @Description
 *
 * 客户端的工作内容：
 * 连接服务器
 * 发送信息
 * 发送的每个信息，等待和接收从服务器返回的同样的信息
 * 关闭连接
 *
 * 用 ChannelHandler 实现客户端逻辑
 * 跟写服务器一样，我们提供 ChannelInboundHandler 来处理数据。下面例子，我们用 SimpleChannelInboundHandler 来处理所有的任务，需要覆盖三个方法：
 *
 * channelActive() - 服务器的连接被建立后调用
 * channelRead0() - 数据后从服务器接收到调用
 * exceptionCaught() - 捕获一个异常时调用
 *
 *
 * @Version V1.0
 * @Date2020/11/25 17:11
 **/
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",
                CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             ByteBuf in) {
        System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
