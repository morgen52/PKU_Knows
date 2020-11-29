package cn.pkucloud.wxmp.service.impl;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxmp.entity.wx.AccessToken;
import cn.pkucloud.wxmp.entity.wx.Signature;
import cn.pkucloud.wxmp.entity.wx.Ticket;
import cn.pkucloud.wxmp.feign.WxmpClient;
import cn.pkucloud.wxmp.service.WxmpService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class WxmpServiceImpl implements WxmpService {
    private final StringRedisTemplate redisTemplate;

    private final WxmpClient wxmpClient;

    @Value("${wx.mp.appid}")
    private String APPID;

    @Value("${wx.mp.secret}")
    private String SECRET;

    public WxmpServiceImpl(StringRedisTemplate redisTemplate, WxmpClient wxmpClient) {
        this.redisTemplate = redisTemplate;
        this.wxmpClient = wxmpClient;
    }

    private String getJsapiTicket() {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps("jsapi_ticket");
        String jsapi_ticket = ops.get();
        if (null == jsapi_ticket) {
            String access_token = getAccessToken();
            Ticket jsapiTicket = wxmpClient.getTicket(access_token, "jsapi");
            if (null != jsapiTicket) {
                jsapi_ticket = jsapiTicket.getTicket();
                if (null != jsapi_ticket) {
                    int expires_in = jsapiTicket.getExpires_in();
                    ops.set(jsapi_ticket, expires_in, TimeUnit.SECONDS);
                }
            }
        }
        return jsapi_ticket;
    }

    private String getAccessToken() {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps("access_token");
        String access_token = ops.get();
        if (null == access_token) {
            AccessToken accessToken = wxmpClient.getAccessToken("client_credential", APPID, SECRET);
            if (null != accessToken) {
                access_token = accessToken.getAccess_token();
                if (null != access_token) {
                    int expires_in = accessToken.getExpires_in();
                    ops.set(access_token, expires_in, TimeUnit.SECONDS);
                }
            }
        }
        return access_token;
    }

    @Override
    public Result<Signature> getSignature() {
        String jsapi_ticket = getJsapiTicket();
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String url = "https://sso.pkucloud.cn/index.html";
        String strToSign = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        String signature = DigestUtils.sha1Hex(strToSign);
        return new Result<>(new Signature(nonceStr, timestamp, signature));
    }
}
