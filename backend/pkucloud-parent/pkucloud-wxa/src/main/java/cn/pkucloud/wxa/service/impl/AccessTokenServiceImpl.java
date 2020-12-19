package cn.pkucloud.wxa.service.impl;

import cn.pkucloud.wxa.feign.WxaClient;
import cn.pkucloud.wxa.dto.wx.AccessToken;
import cn.pkucloud.wxa.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {
    private final StringRedisTemplate redisTemplate;

    private final WxaClient wxaClient;

    @Value("${wx.wxa.appid}")
    private String APPID;

    @Value("${wx.wxa.secret}")
    private String SECRET;

    public AccessTokenServiceImpl(StringRedisTemplate redisTemplate, WxaClient wxaClient) {
        this.redisTemplate = redisTemplate;
        this.wxaClient = wxaClient;
    }

    @Override
    public String getAccessToken() {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps("access_token");
        String access_token = ops.get();
        if (null == access_token) {
            AccessToken accessToken = wxaClient.getAccessToken("client_credential", APPID, SECRET);
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
}
