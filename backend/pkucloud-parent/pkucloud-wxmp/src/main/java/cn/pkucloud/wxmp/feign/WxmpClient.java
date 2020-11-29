package cn.pkucloud.wxmp.feign;

import cn.pkucloud.wxmp.entity.wx.AccessToken;
import cn.pkucloud.wxmp.entity.wx.TemplateMessageResult;
import cn.pkucloud.wxmp.entity.wx.Ticket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wxmp", url = "https://api.weixin.qq.com/cgi-bin/")
public interface WxmpClient {
    @GetMapping("token")
    AccessToken getAccessToken(@RequestParam String grant_type,
                               @RequestParam String appid,
                               @RequestParam String secret);

    @GetMapping("ticket/getticket?access_token={access_token}&type={type}")
    Ticket getTicket(@PathVariable String access_token,
                     @PathVariable String type);

    @PostMapping(value = "message/template/send?access_token={access_token}", consumes = "application/json")
    TemplateMessageResult sendTemplateMessage(@PathVariable String access_token);
}
