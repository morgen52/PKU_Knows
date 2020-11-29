package cn.pkucloud.wxmp.controller;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxmp.service.WxmpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class WxmpController {
    private final WxmpService wxmpService;

    public WxmpController(WxmpService wxmpService) {
        this.wxmpService = wxmpService;
    }

    @GetMapping("jsapi_ticket")
    public Result<String> getJsapiTicket() {
        return wxmpService.getJsapiTicket();
    }
}
