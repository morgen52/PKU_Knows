package cn.pkucloud.wxmp.service.impl;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxmp.dto.wx.Signature;
import cn.pkucloud.wxmp.dto.wx.Ticket;
import cn.pkucloud.wxmp.feign.MpClient;
import cn.pkucloud.wxmp.service.AccessTokenService;
import cn.pkucloud.wxmp.service.PublicService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PublicServiceImpl implements PublicService {
    private final AccessTokenService accessTokenService;

    private final StringRedisTemplate redisTemplate;

    private final MpClient mpClient;

    public PublicServiceImpl(AccessTokenService accessTokenService, StringRedisTemplate redisTemplate, MpClient mpClient) {
        this.accessTokenService = accessTokenService;
        this.redisTemplate = redisTemplate;
        this.mpClient = mpClient;
    }

    private String getJsapiTicket() {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps("jsapi_ticket");
        String jsapi_ticket = ops.get();
        if (null == jsapi_ticket) {
            String access_token = accessTokenService.getAccessToken();
            Ticket jsapiTicket = mpClient.getTicket(access_token, "jsapi");
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

    @Override
    public Result<Signature> getSignature(String url) {
        String jsapi_ticket = getJsapiTicket();
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        String strToSign =
                "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        String signature = DigestUtils.sha1Hex(strToSign);
        Signature sign = new Signature(nonceStr, timestamp, signature);
        return new Result<>(sign);
    }
}
