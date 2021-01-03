package cn.pkucloud.auth.controller;

import cn.pkucloud.auth.service.AuthService;
import cn.pkucloud.common.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("userinfo")
    public Result<?> getUserInfo(@RequestHeader("Authorization") String jws) {
        return authService.getUserInfo(jws);
    }

    @PostMapping("password")
    public Result<?> setPassword(@RequestHeader("Authorization") String jws,
                                 @RequestParam String userName,
                                 @RequestParam String password,
                                 @RequestParam(required = false) String motto) {
        return authService.setPassword(jws, userName, password);
    }

    @PostMapping("profile")
    public Result<?> setProfile(@RequestHeader("Authorization") String jws,
                                @RequestParam String userName,
                                @RequestParam String motto) {
        return authService.setProfile(jws, userName, motto);
    }
}
