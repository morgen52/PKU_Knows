package cn.pkucloud.auth.wxa.controller;

import cn.pkucloud.auth.wxa.entity.WxaScene;
import cn.pkucloud.auth.wxa.service.WxaAuthService;
import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WxaAuthController {
    private final WxaAuthService wxaAuthService;

    public WxaAuthController(WxaAuthService wxaAuthService) {
        this.wxaAuthService = wxaAuthService;
    }

    @GetMapping("scene")
    public Result<String> getScene(String ip, String ua) throws JsonProcessingException {
        return wxaAuthService.getScene(ip, ua);
    }

    @GetMapping("scene/{scene}")
    public Result<?> checkScene(String ip, String ua, @PathVariable String scene) {
        return wxaAuthService.checkScene(ip, ua, scene);
    }

    @GetMapping("sceneinfo/{scene}")
    public Result<WxaScene> getSceneInfo(@PathVariable String scene) throws JsonProcessingException {
        return wxaAuthService.getSceneInfo(scene);
    }

    @GetMapping("token")
    public Result<?> sendToken(String scene, String token) {
        return wxaAuthService.sendToken(scene, token);
    }
}
