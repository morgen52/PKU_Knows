package cn.pkucloud.wxmp.feign;

import cn.pkucloud.wxmp.dto.wx.AccessToken;
import cn.pkucloud.wxmp.dto.wx.custom.CustomMessage;
import cn.pkucloud.wxmp.dto.wx.template.TemplateMessageResult;
import cn.pkucloud.wxmp.dto.wx.Ticket;
import cn.pkucloud.wxmp.dto.wx.WxmpUserInfoDto;
import cn.pkucloud.wxmp.dto.wx.template.TemplateMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mp", url = "https://api.weixin.qq.com/cgi-bin/")
public interface MpClient {
    @GetMapping("token")
    AccessToken getAccessToken(@RequestParam String grant_type,
                               @RequestParam String appid,
                               @RequestParam String secret);

    @GetMapping("ticket/getticket")
    Ticket getTicket(@RequestParam String access_token,
                     @RequestParam String type);

    @PostMapping("message/custom/send")
    String sendCustomMessage(@RequestParam String access_token,
                             @RequestBody CustomMessage customMessage);

    @PostMapping("message/template/send")
    TemplateMessageResult sendTemplateMessage(@RequestParam String access_token,
                                              @RequestBody TemplateMessage templateMessage);

    @GetMapping("user/info")
    WxmpUserInfoDto getUserInfo(@RequestParam String access_token,
                                @RequestParam String openid,
                                @RequestParam String lang);
}
