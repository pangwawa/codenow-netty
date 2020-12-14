package fun.codenow.netty.socket.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/11/26 18:04
 **/
public class CustomHeartBeathandler extends SimpleChannelInboundHandler<String> {
    int readIdleTimes=0;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(" Server HeartBeat received message:"+s);
        channelHandlerContext.writeAndFlush(" client reply : get message "+s);

    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event= (IdleStateEvent) evt;
        String eventType=null;
        switch (event.state()){
            case WRITER_IDLE:
                eventType=" 读空闲";
                readIdleTimes++;
                break;
            case READER_IDLE:
                eventType="写空闲";
                break;
            case ALL_IDLE:
                eventType="读写空闲";
                break;
        }
        System.out.println("eventType:"+eventType);
        if (readIdleTimes>3){
            System.out.println("读空闲超过三次，关闭连接");
            ctx.channel().writeAndFlush("you are out");
            /*ctx.channel().close().sync();*/
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("= = = "+ctx.channel().remoteAddress()+"is active = = =");
    }
}
