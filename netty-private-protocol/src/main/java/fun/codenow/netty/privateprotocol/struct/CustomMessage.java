package fun.codenow.netty.privateprotocol.struct;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/3 10:33
 **/
public final class CustomMessage {
    private CustomHeader header;
    private Object body;

    public final CustomHeader getHeader() {
        return header;
    }

    public final void setHeader(CustomHeader header) {
        this.header = header;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "CustomMessage{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
