package cn.pkucloud.auth.controller;

import cn.pkucloud.auth.service.AuthService;
import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import static cn.pkucloud.auth.entity.wx.WxLoginType.*;

@RestController
@RequestMapping("api")
@CrossOrigin
public class AuthPublicController {
    private final AuthService authService;

    public AuthPublicController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("sms/code")
    public Result<?> sendSmsCode(@RequestHeader("X-Real-IP") String ip,
                                 @RequestHeader("User-Agent") String ua,
                                 String phone) throws JsonProcessingException {
        return authService.sendSmsCode(ip, ua, phone);
    }

    @GetMapping("wx/app")
    public Result<?> wxLoginByApp(String code, String state) throws JsonProcessingException {
        return authService.wxLogin(APP, code, state);
    }

    @GetMapping("wx/web")
    public Result<?> wxLoginByWeb(String code, String state) throws JsonProcessingException {
        return authService.wxLogin(WEB, code, state);
    }

    @GetMapping("wx/wxh5")
    public Result<?> wxLoginByWxH5(String code, String state) throws JsonProcessingException {
        return authService.wxLogin(WXH5, code, state);
    }

    @PostMapping("token")
    public Result<?> formLogin(@RequestHeader("X-Real-IP") String ip,
                               @RequestHeader("User-Agent") String ua,
                               String userName,
                               String password,
                               String phone,
                               String smsCode) {
        return null;
    }
}
