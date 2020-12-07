package cn.pkucloud.auth.controller;

import cn.pkucloud.auth.entity.Auth;
import cn.pkucloud.auth.service.AuthService;
import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("private")
public class AuthPrivateController {
    private final AuthService authService;

    public AuthPrivateController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("token")
    public Result<String> loginByWxa(String wxaUserInfoStr, String pkuUserInfoStr) throws JsonProcessingException {
        return authService.wxaLogin(wxaUserInfoStr, pkuUserInfoStr);
    }

    @GetMapping("auth")
    public Result<Auth> getAuthByWxUnionId(String wxUnionId) {
        return authService.getAuthByWxUnionId(wxUnionId);
    }
}
