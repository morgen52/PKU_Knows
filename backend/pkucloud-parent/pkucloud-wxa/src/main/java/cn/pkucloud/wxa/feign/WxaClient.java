package cn.pkucloud.wxa.feign;

import cn.pkucloud.wxa.dto.wx.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wxa", url = "https://api.weixin.qq.com/")
public interface WxaClient {
    @GetMapping("cgi-bin/token")
    AccessToken getAccessToken(@RequestParam String grant_type,
                               @RequestParam String appid,
                               @RequestParam String secret);

    @PostMapping(value = "cgi-bin/message/wxopen/template/uniform_send?access_token={access_token}")
    BaseResult sendUniformMessage(@PathVariable String access_token,
                                  @RequestParam String touser,
                                  @RequestParam MpTemplateMsg mp_template_msg);

    @PostMapping("cgi-bin/message/subscribe/send?access_token={access_token}")
    BaseResult sendSubscribeMessage(@PathVariable String access_token,
                                    @RequestParam String touser,
                                    @RequestParam String template_id,
                                    @RequestParam String page,
                                    @RequestParam String data,
                                    @RequestParam String miniprogram_state,
                                    @RequestParam String lang);

    @PostMapping(value = "wxa/getwxacodeunlimit?access_token={access_token}", consumes = "application/json")
    byte[] getWxaCode(@PathVariable String access_token, WxaCodeRequest wxaCodeRequest);
}
