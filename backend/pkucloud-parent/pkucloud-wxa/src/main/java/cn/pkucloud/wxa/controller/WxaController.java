package cn.pkucloud.wxa.controller;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxa.entity.SceneInfo;
import cn.pkucloud.wxa.service.WxaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CrossOrigin
public class WxaController {
    private final WxaService wxaService;

    public WxaController(WxaService wxaService) {
        this.wxaService = wxaService;
    }

    @GetMapping("scene")
    public Result<String> getScene(@RequestHeader("X-Real-IP") String ip,
                              @RequestHeader("User-Agent") String ua) throws JsonProcessingException {
        return wxaService.getScene(ip, ua);
    }

    @GetMapping(value = "code/{scene}", produces = "image/jpeg")
    public byte[] getWxaCode(@RequestHeader("X-Real-IP") String ip,
                             @RequestHeader("User-Agent") String ua,
                             @PathVariable String scene) throws JsonProcessingException {
        return wxaService.getWxaCode(ip, ua, scene);
    }

    @GetMapping("scene/{scene}")
    public Result<SceneInfo> getSceneInfo(@PathVariable String scene) throws JsonProcessingException {
        return wxaService.getSceneInfo(scene);
    }

    @PostMapping("token")
    public Result<?> loginByWxa(String data) {
        return wxaService.loginByWxa(data);
    }
}
