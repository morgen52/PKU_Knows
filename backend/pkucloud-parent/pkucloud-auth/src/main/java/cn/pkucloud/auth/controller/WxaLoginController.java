package cn.pkucloud.auth.controller;

import cn.pkucloud.auth.entity.WxaScene;
import cn.pkucloud.auth.service.AuthService;
import cn.pkucloud.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/wxa")
@CrossOrigin
@Api(tags = {"微信小程序登录 API"})
public class WxaLoginController {
    private final AuthService authService;

    public WxaLoginController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation("获取微信小程序登录 scene")
    @GetMapping("scene")
    public Result<String> getScene(@RequestHeader("X-Real-IP") String ip,
                                   @RequestHeader("User-Agent") String ua) {
        return authService.getScene(ip, ua);
    }

    @ApiOperation("获取 scene 对应的微信小程序码")
    @GetMapping(value = "code/{scene}", produces = "image/jpeg")
    public byte[] getWxaCode(@RequestHeader("X-Real-IP") String ip,
                             @RequestHeader("User-Agent") String ua,
                             @ApiParam("scene") @PathVariable String scene) {
        return authService.getWxaCode(ip, ua, scene);
    }

    @ApiOperation("获取 scene 对应的 PC 端登录信息")
    @GetMapping("scene/{scene}")
    public Result<WxaScene> getSceneInfo(@ApiParam("scene") @PathVariable String scene) {
        return authService.getSceneInfo(scene);
    }

    @ApiOperation("微信小程序登录")
    @PostMapping(value = "token", consumes = "application/x-www-form-urlencoded")
    public Result<?> wxaLogin(@RequestHeader("X-Real-IP") String ip,
                              @RequestHeader("User-Agent") String ua,
                              @ApiParam("密文") @RequestParam String encrypt,
                              @ApiParam("AES 加密的初始向量") @RequestParam String iv,
                              @ApiParam("消息体签名") @RequestParam String signature,
                              @ApiParam("时间戳") @RequestParam int timestamp,
                              @ApiParam("随机串") @RequestParam String nonce) throws Exception {
        return authService.wxaLogin(ip, ua, encrypt, iv, signature, timestamp, nonce);
    }
}
