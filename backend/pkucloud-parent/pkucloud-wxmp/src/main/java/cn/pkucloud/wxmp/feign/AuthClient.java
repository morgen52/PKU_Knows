package cn.pkucloud.wxmp.feign;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxmp.entity.Auth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "pkucloud-auth")
public interface AuthClient {
    @GetMapping("private/auth")
    Result<Auth> getAuthByWxUnionId(String wxUnionId);
}
