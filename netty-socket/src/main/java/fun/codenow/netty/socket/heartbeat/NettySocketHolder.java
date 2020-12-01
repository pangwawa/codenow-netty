package fun.codenow.netty.socket.heartbeat;

import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Jack Wu
 * @Description
 * @Version V1.0
 * @Date2020/12/1 10:30
 **/
public class NettySocketHolder {
    private final static Map<Long, NioSocketChannel> MAP=new ConcurrentHashMap<>();

    public static void put(Long id, NioSocketChannel socketChannel) {
        MAP.put(id, socketChannel);
    }

    public static NioSocketChannel get(Long id) {
        return MAP.get(id);
    }

    public static Map<Long, NioSocketChannel> getMAP() {
        return MAP;
    }

    public static void remove(NioSocketChannel nioSocketChannel) {
        MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).forEach(entry -> MAP.remove(entry.getKey()));
    }
}
