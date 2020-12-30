package cn.pkucloud.auth.controller;

import cn.pkucloud.auth.dto.Wxh5Signature;
import cn.pkucloud.auth.service.AuthService;
import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import static cn.pkucloud.auth.entity.wx.WxLoginType.*;

@RestController
@RequestMapping("api/wx")
@CrossOrigin
@Api(tags = {"微信登录 API"})
public class WxLoginController {
    private final AuthService authService;

    public WxLoginController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation("APP 端微信登录")
    @GetMapping("app/token")
    public Result<String> wxLoginByApp(@RequestHeader("X-Real-IP") String ip,
                                       @RequestHeader("User-Agent") String ua,
                                       @ApiParam("微信服务器返回的 code") String code,
                                       @ApiParam("微信服务器返回的 state") String state) throws JsonProcessingException {
        return authService.wxLogin(APP, ip, ua, code, state);
    }

    @ApiOperation("WEB 端微信登录")
    @GetMapping("web/token")
    public Result<String> wxLoginByWeb(@RequestHeader("X-Real-IP") String ip,
                                       @RequestHeader("User-Agent") String ua,
                                       @ApiParam("微信服务器返回的 code") String code,
                                       @ApiParam("微信服务器返回的 state") String state) throws JsonProcessingException {
        return authService.wxLogin(WEB, ip, ua, code, state);
    }

    @ApiOperation("微信内 H5 微信登录")
    @GetMapping("wxh5/token")
    public Result<String> wxLoginByWxh5(@RequestHeader("X-Real-IP") String ip,
                                        @RequestHeader("User-Agent") String ua,
                                        @ApiParam("微信服务器返回的 code") String code,
                                        @ApiParam("微信服务器返回的 state") String state) throws JsonProcessingException {
        return authService.wxLogin(WXH5, ip, ua, code, state);
    }

    @GetMapping("wxh5/signature")
    public Result<Wxh5Signature> getWxh5Signature(@RequestParam String url) {
        return authService.getWxh5Signature(url);
    }
}
