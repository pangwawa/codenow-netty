package fun.codenow.netty.socket.heartbeat;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.stomp.StompHeaders.HEART_BEAT;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/1 10:20
 **/
@Slf4j
public class HeartBeatSimpleHandle extends SimpleChannelInboundHandler<CustomProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CustomProtocol customProtocol) throws Exception {
        log.info("收到customProtocol：{}",customProtocol);
        NettySocketHolder.put(customProtocol.getId(), (NioSocketChannel) channelHandlerContext.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettySocketHolder.remove((NioSocketChannel) ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;

            if (idleStateEvent.state() == IdleState.READER_IDLE){
                log.info("已经5秒没有收到信息！");
                log.info("NettySocketHolder MAP{}",NettySocketHolder.getMAP());
                //向客户端发送消息
                ctx.writeAndFlush(HEART_BEAT).addListener(ChannelFutureListener.CLOSE_ON_FAILURE) ;
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}
