package cn.pkucloud.tl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TlApplication {
    public static void main(String[] args) {
        SpringApplication.run(TlApplication.class, args);
    }
}
