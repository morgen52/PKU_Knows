package cn.pkucloud.auth.wxa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WxaAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxaAuthApplication.class, args);
    }
}
