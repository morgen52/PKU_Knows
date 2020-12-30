package cn.pkucloud.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "pkucloud-wxa")
@RequestMapping("private")
public interface WxaClient {
    @GetMapping("code/{scene}")
    byte[] getWxaCode(@PathVariable String scene);
}
