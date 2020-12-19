package cn.pkucloud.auth.controller;

import cn.pkucloud.auth.service.AuthService;
import cn.pkucloud.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/sms")
@CrossOrigin(origins = {"https://sso.pkucloud.cn"})
@Api(tags = {"短信验证码登录 API"})
public class SmsLoginController {
    private final AuthService authService;

    public SmsLoginController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation("获取短信验证码")
    @PostMapping(value = "code", consumes = "application/x-www-form-urlencoded")
    public Result<?> sendSmsCode(@RequestHeader("X-Real-IP") String ip,
                                 @RequestHeader("User-Agent") String ua,
                                 @ApiParam("手机号") @RequestParam String phone) {
        return authService.sendSmsCode(ip, ua, phone);
    }

    @ApiOperation("短信验证码登录")
    @PostMapping(value = "token", consumes = "application/x-www-form-urlencoded")
    public Result<String> smsLogin(@RequestHeader("X-Real-IP") String ip,
                                   @RequestHeader("User-Agent") String ua,
                                   @ApiParam("手机号") @RequestParam String phone,
                                   @ApiParam("短信验证码") @RequestParam String code) {
        return authService.smsLogin(ip, ua, phone, code);
    }
}
