package fun.codenow.netty.socket.heartbeat;

import java.io.Serializable;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/1 10:21
 **/

public class CustomProtocol implements Serializable {
    private static final long serialVersionUID = 4671171056588401542L;
    private long id ;
    private String content ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
