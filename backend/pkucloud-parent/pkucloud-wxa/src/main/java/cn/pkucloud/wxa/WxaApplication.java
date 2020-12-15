package cn.pkucloud.wxa;

import cn.pkucloud.wxa.netty.WebSocketServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class WxaApplication implements CommandLineRunner {
    private final WebSocketServer webSocketServer;

    public WxaApplication(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(WxaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        webSocketServer.run();
    }
}
