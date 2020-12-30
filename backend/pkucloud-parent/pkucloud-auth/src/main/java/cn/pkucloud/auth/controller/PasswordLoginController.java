package cn.pkucloud.auth.controller;

import cn.pkucloud.auth.service.AuthService;
import cn.pkucloud.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/password")
@CrossOrigin
@Api(tags = {"密码登录 API"})
public class PasswordLoginController {
    private final AuthService authService;

    public PasswordLoginController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation("用户名密码登录")
    @PostMapping(value = "token", consumes = "application/x-www-form-urlencoded")
    public Result<String> passwordLogin(@ApiParam("用户名") @RequestParam String userName,
                                        @ApiParam("密码") @RequestParam String password) {
        return authService.passwordLogin(userName, password);
    }
}
