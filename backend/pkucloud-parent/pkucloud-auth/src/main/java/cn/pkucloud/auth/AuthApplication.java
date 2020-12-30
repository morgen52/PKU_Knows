package cn.pkucloud.auth;

import cn.pkucloud.auth.crypto.CryptoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableOpenApi
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Value("${wx.wxa.cloudbase.token}")
    private String TOKEN;

    @Value("${wx.wxa.cloudbase.key}")
    private String KEY;

    @Bean
    public CryptoUtil cryptoUtil() {
        return new CryptoUtil(TOKEN, KEY);
    }
}
