package cn.pkucloud.wxa.controller;

import cn.pkucloud.wxa.service.WxaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("private")
public class WxaPrivateController {
    private final WxaService wxaService;

    public WxaPrivateController(WxaService wxaService) {
        this.wxaService = wxaService;
    }

    @GetMapping(value = "code/{scene}", produces = "image/jpeg")
    private byte[] getWxaCode(@PathVariable String scene) {
        return wxaService.getWxaCode(scene);
    }
}
