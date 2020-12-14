package fun.codenow.netty.socket.subscribebook;

import fun.codenow.netty.socket.protobuf.SubscribeReqProto;
import fun.codenow.netty.socket.protobuf.SubscribeRespProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/2 17:51
 **/
@Slf4j
public class SubscribeBookServerHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req= (SubscribeReqProto.SubscribeReq) msg;
        if ("Lilinfeng".equalsIgnoreCase(req.getUserName())){
            log.info("打印信息： 收到客户端的订书请求：{}",req.toString());
            channelHandlerContext.writeAndFlush(resp(req.getSubReqID()));
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        log.error("发生异常，关闭链路,message{}",cause.getMessage());
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqID){
        SubscribeRespProto.SubscribeResp.Builder builder=SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode(0);
        builder.setDesc("图书下单成功，三天后将邮寄到你的地址");
        return builder.build();
    }
}
