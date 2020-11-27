package fun.codenow.netty.socket.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/11/27 17:19
 **/
public class CustomHeartBeatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String string) throws Exception {
        System.out.println("客户端收到："+string);
    }

}
