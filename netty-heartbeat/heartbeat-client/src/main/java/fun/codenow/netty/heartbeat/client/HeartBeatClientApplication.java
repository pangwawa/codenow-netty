package fun.codenow.netty.heartbeat.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Jack Wu
 * @Description
 *
 *      初始化时获取应用名称，设置 应用ID，在心跳中加入应用ID和应用名称信息
 *
 * @Version V1.0
 * @Date2020/12/4 14:27
 **/
@SpringBootApplication
public class HeartBeatClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(HeartBeatClientApplication.class);
    }
}
