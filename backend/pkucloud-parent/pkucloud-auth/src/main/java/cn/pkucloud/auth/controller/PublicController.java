package cn.pkucloud.auth.controller;

import cn.pkucloud.auth.entity.UserInfoDto;
import cn.pkucloud.auth.service.AuthService;
import cn.pkucloud.common.Result;
import org.springframework.web.bind.annotation.*;

import static cn.pkucloud.common.ResultCode.AUTHORIZATION_REQUIRED;

@RestController
@RequestMapping("api")
public class PublicController {
    private final AuthService authService;

    public PublicController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("userinfo")
    public Result<UserInfoDto> getUserInfo(@RequestHeader("X-Real-IP") String ip,
                                           @RequestParam Long id) {
        if ("124.70.77.13".equals(ip)) {
            return authService.getPrivateUserInfo(id);
        }
        return new Result<>(AUTHORIZATION_REQUIRED, "authorization required");
    }
}
