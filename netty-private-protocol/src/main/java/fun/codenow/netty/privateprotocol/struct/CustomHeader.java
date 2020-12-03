package fun.codenow.netty.privateprotocol.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/3 10:33
 **/
public final class CustomHeader {
    /**
     *  消息的校验码，由三部分组成 ：
     *      1、0xABEF  （固定值，表面是netty协议，2个字节） ；
     *      2、主版本号 1~255，1个字节；
     *      3、次版本号，1~255，1个字节。也就是： crcCode= 0xABEF + 主版本号 + 次版本号
     */
    private int crcCode=0xabef0101;
    /**
     *  消息长度
     */
    private int length;
    /**
     *  会话ID 节点内全局唯一，由会话ID生成器生成
     */
    private long sessionID;
    /**
     *  消息类型
     *         0:业务请求消息
     *         1:业务响应消息
     *         2:业务ONE WAY消息（既是请求又是响应消息)
     *         3:握手请求消息
     *         4:握手应答消息
     *         5:心跳请求消息
     *         6:心跳应答消息
     */
    private byte type;
    /**
     *  消息优先级  0~255
     */
    private byte priority;
    /**
     *  可选字段，用于扩展请求头
     */
    private Map<String,Object> attachment=new HashMap<>();

    public final int getCrcCode() {
        return crcCode;
    }

    public final void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public final int getLength() {
        return length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public long getSessionID() {
        return sessionID;
    }

    public final void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public final byte getType() {
        return type;
    }

    public final void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public final void setPriority(byte priority) {
        this.priority = priority;
    }

    public final Map<String, Object> getAttachment() {
        return attachment;
    }

    public final void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "CustomHeader{" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionID=" + sessionID +
                ", type=" + type +
                ", priority=" + priority +
                ", attachment=" + attachment +
                '}';
    }
}
