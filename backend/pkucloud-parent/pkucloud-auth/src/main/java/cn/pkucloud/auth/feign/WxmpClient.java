package cn.pkucloud.auth.feign;

import cn.pkucloud.auth.dto.Wxh5Signature;
import cn.pkucloud.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "pkucloud-wxmp")
@RequestMapping("private")
public interface WxmpClient {
    @GetMapping("signature")
    Result<Wxh5Signature> getWxh5Signature(@RequestParam String url);
}
