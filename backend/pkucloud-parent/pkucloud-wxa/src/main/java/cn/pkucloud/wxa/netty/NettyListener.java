package cn.pkucloud.wxa.netty;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class NettyListener implements ApplicationListener<ApplicationReadyEvent> {
    private final WebSocketServer webSocketServer;

    public NettyListener(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        if (event.getApplicationContext().getParent() == null) {
            webSocketServer.run();
//        }
    }
}
