package cn.pkucloud.wxmp.controller;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxmp.entity.wx.Signature;
import cn.pkucloud.wxmp.service.WxmpService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@CrossOrigin
public class WxmpController {
    private final WxmpService wxmpService;

    public WxmpController(WxmpService wxmpService) {
        this.wxmpService = wxmpService;
    }

    @GetMapping("signature")
    public Result<Signature> getSignature() {
        return wxmpService.getSignature();
    }
}
