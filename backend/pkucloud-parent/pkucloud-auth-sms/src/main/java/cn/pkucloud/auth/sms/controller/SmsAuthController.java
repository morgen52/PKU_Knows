package cn.pkucloud.auth.sms.controller;

import cn.pkucloud.auth.sms.service.SmsAuthService;
import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsAuthController {
    @Autowired
    private SmsAuthService smsAuthService;

    @GetMapping("code")
    public Result<?> sendCode(String ip, String ua, String phone) throws JsonProcessingException {
        return smsAuthService.sendCode(ip, ua, phone);
    }

    @GetMapping("pkuid")
    public Result<String> getPkuId(String ip, String ua, String phone, String code) throws JsonProcessingException {
        return smsAuthService.getPkuId(ip, ua, phone, code);
    }
}
