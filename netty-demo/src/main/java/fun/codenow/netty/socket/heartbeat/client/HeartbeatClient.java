package fun.codenow.netty.socket.heartbeat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/1 10:43
 **/
@Component
@Slf4j
public class HeartbeatClient {
    private EventLoopGroup group = new NioEventLoopGroup();
    private SocketChannel channel;

    @PostConstruct
    public void start() throws InterruptedException {
        Bootstrap bootstrap=new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new CustomerHandleInitializer());

        ChannelFuture future=bootstrap.connect("192.168.5.93",9696).sync();
        if (future.isSuccess()){
            log.info("启动netty 客户端成功");
        }
        channel = (SocketChannel) future.channel();
    }
}
