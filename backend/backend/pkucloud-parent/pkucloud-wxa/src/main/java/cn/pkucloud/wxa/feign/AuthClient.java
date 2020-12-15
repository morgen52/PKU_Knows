package cn.pkucloud.wxa.feign;

import cn.pkucloud.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "pkucloud-auth")
public interface AuthClient {
    @PostMapping("private/token")
    Result<?> loginByWxa(@RequestParam String wxaUserInfoStr,
                         @RequestParam String pkuUserInfoStr);
}
