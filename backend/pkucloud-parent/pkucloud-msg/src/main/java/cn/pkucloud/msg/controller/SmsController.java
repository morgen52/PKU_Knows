package cn.pkucloud.msg.controller;

import cn.pkucloud.common.Result;
import cn.pkucloud.msg.service.SmsService;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
public class SmsController {
    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("code")
    public Result<?> sendSmsCode(String phone, String code, String ttl) throws TencentCloudSDKException {
        return smsService.sendSmsCode(phone, code, ttl);
    }
}
