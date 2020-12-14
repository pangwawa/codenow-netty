package fun.codenow.netty.socket.heartbeat.client;

import fun.codenow.netty.socket.heartbeat.HeartbeatEncode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/1 10:46
 **/
public class CustomerHandleInitializer  extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                //10 秒没发送消息 将IdleStateHandler 添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(0, 10, 0))
                .addLast(new HeartbeatEncode())
                .addLast(new EchoClientHandle());
    }
}
