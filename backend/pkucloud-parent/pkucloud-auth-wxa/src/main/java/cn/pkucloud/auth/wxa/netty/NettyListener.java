package cn.pkucloud.auth.wxa.netty;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class NettyListener implements ApplicationListener<ContextRefreshedEvent> {
    private final WebSocketServer webSocketServer;

    public NettyListener(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            webSocketServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
