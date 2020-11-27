package fun.codenow.netty.socket.heart;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/11/27 17:20
 **/
public class CustomHeartbeatClientInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline channelPipeline=channel.pipeline();
        channelPipeline.addLast("decoder",new StringDecoder());
        channelPipeline.addLast("encoder",new StringEncoder());
        channelPipeline.addLast(new CustomHeartBeatClientHandler());

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(" client is alive");
    }
}
