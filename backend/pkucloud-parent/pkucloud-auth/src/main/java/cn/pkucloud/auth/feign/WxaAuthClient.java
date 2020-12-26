package cn.pkucloud.auth.feign;

import cn.pkucloud.auth.entity.WxaScene;
import cn.pkucloud.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "pkucloud-auth-wxa")
public interface WxaAuthClient {
    @GetMapping("scene")
    Result<String> getScene(@RequestParam String ip,
                            @RequestParam String ua);

    @GetMapping("scene/{scene}")
    Result<?> checkScene(@RequestParam String ip,
                         @RequestParam String ua,
                         @PathVariable String scene);

    @GetMapping("sceneinfo/{scene}")
    Result<WxaScene> getSceneInfo(@PathVariable String scene);

    @GetMapping("token")
    Result<?> sendToken(@RequestParam String scene,
                        @RequestParam String token);
}
