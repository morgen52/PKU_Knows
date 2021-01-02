package cn.pkucloud.wxmp.controller;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxmp.dto.wx.Signature;
import cn.pkucloud.wxmp.service.PublicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("private")
public class PrivateController {
    private final PublicService publicService;

    public PrivateController(PublicService publicService) {
        this.publicService = publicService;
    }

    @GetMapping("signature")
    public Result<Signature> getSignature(@NotBlank @RequestParam String url) {
        return publicService.getSignature(url);
    }
}
