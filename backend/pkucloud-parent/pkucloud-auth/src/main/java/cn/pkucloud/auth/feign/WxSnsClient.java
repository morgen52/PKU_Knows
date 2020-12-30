package cn.pkucloud.auth.feign;

import cn.pkucloud.auth.entity.wx.AccessToken;
import cn.pkucloud.auth.entity.wx.BaseResult;
import cn.pkucloud.auth.entity.wx.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "wxsns", url = "https://api.weixin.qq.com/sns/")
public interface WxSnsClient {
    @GetMapping("oauth2/access_token")
    String getAccessToken(@RequestParam String appid,
                               @RequestParam String secret,
                               @RequestParam String code,
                               @RequestParam String grant_type);

    @GetMapping("oauth2/refresh_token")
    AccessToken getRefreshToken(@RequestParam String appid,
                                 @RequestParam String grant_type,
                                 @RequestParam String refresh_token);

    @GetMapping("auth")
    BaseResult getAuth(@RequestParam String access_token,
                       @RequestParam String openid);

    @GetMapping("userinfo")
    String getUserInfo(@RequestParam String access_token,
                         @RequestParam String openid,
                         @RequestParam String lang);
}
