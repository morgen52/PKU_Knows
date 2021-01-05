package cn.pkucloud.qa.feign;

import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth", url = "https://auth.pkucs.cn/api/")
public interface AuthClient {
    @GetMapping("userinfo")
    Result<User> getUserInfo(@RequestParam String id);
}
