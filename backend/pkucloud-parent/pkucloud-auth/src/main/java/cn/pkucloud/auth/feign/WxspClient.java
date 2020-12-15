package cn.pkucloud.auth.feign;

import cn.pkucloud.auth.entity.WxspResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wxsp", url = "http://wxsp.pku.edu.cn/wechat/svc/")
public interface WxspClient {
    @GetMapping("user/getUserid.do")
    WxspResult getPkuId(@RequestHeader("User-Agent") String ua,
                        @RequestParam("loginid") String phone);
}
