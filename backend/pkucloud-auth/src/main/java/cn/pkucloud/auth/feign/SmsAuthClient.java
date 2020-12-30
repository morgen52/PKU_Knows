package cn.pkucloud.auth.feign;

import cn.pkucloud.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "pkucloud-auth-sms")
public interface SmsAuthClient {
    @GetMapping("code")
    Result<?> sendCode(@RequestParam String ip,
                       @RequestParam String ua,
                       @RequestParam String phone);

    @GetMapping("pkuid")
    Result<String> getPkuId(@RequestParam String ip,
                            @RequestParam String ua,
                            @RequestParam String phone,
                            @RequestParam String code);
}
