package cn.pkucloud.wxmp.controller;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxmp.dto.wx.Signature;
import cn.pkucloud.wxmp.service.PublicService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(value = "api", produces = "application/json")
@Validated
@CrossOrigin(origins = {"https://sso.pkucloud.cn"})
public class PublicController {
    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @GetMapping("signature")
    public Result<Signature> getSignature(@NotBlank String url) {
        return publicService.getSignature(url);
    }
}
