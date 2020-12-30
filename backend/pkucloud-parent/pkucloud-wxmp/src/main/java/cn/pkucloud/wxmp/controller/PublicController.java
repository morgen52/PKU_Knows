package cn.pkucloud.wxmp.controller;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxmp.service.MpService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.pkucloud.common.ResultCode.AUTHORIZATION_REQUIRED;

@RestController
@RequestMapping(value = "api", produces = "application/json")
@Validated
public class PublicController {
    private final MpService mpService;

    public PublicController(MpService mpService) {
        this.mpService = mpService;
    }

    @PostMapping("msg/login")
    public Result<?> sendLoginMsg(@RequestHeader("X-Real-IP") String ip) {
        return null;
    }

    @PostMapping("msg/answer")
    public Result<?> sendAnswerMsg(@RequestHeader("X-Real-IP") String ip,
                                   @RequestParam String[] ids,
                                   @RequestParam String first,
                                   @RequestParam String title,
                                   @RequestParam String userName,
                                   @RequestParam String time,
                                   @RequestParam String remark) {
        if ("124.70.77.13".equals(ip)) {
            return mpService.sendAnswerMsg(ids, first, title, userName, time, remark);
        }
        return new Result<>(AUTHORIZATION_REQUIRED, "authorization required");
    }

    @PostMapping("msg/report")
    public Result<?> sendReportMsg(@RequestHeader("X-Real-IP") String ip) {
        return null;
    }
}
