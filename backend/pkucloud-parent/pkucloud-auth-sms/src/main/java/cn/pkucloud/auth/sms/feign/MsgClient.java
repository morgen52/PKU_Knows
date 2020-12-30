package cn.pkucloud.auth.sms.feign;

import cn.pkucloud.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "pkucloud-msg")
public interface MsgClient {
    @PostMapping("sms/code")
    Result<?> sendSmsCode(@RequestParam String phone,
                          @RequestParam String code,
                          @RequestParam String ttl);
}
