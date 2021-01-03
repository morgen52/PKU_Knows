package cn.pkucloud.wxmp;

import cn.pkucloud.wxmp.crypto.CryptoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableAsync
@EnableDiscoveryClient
@EnableFeignClients
@EnableOpenApi
public class WxmpApplication {
    @Value("${wx.mp.appid}")
    private String APPID;

    @Value("${wx.mp.wxid}")
    private String WXID;

    @Value("${wx.mp.token}")
    private String TOKEN;

    @Value("${wx.mp.encoding_aes_key}")
    private String ENCODING_AES_KEY;

    public static void main(String[] args) {
        SpringApplication.run(WxmpApplication.class, args);
    }

    @Bean
    public CryptoUtil cryptoUtil() {
        return new CryptoUtil(APPID, TOKEN, ENCODING_AES_KEY);
    }
}
