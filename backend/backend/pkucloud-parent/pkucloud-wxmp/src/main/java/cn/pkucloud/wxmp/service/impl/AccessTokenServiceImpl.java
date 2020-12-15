package cn.pkucloud.wxmp.service.impl;

import cn.pkucloud.wxmp.dto.wx.AccessToken;
import cn.pkucloud.wxmp.feign.MpClient;
import cn.pkucloud.wxmp.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {
    private final StringRedisTemplate redisTemplate;

    private final MpClient mpClient;

    @Value("${wx.mp.appid}")
    private String APPID;

    @Value("${wx.mp.secret}")
    private String SECRET;

    public AccessTokenServiceImpl(StringRedisTemplate redisTemplate, MpClient mpClient) {
        this.redisTemplate = redisTemplate;
        this.mpClient = mpClient;
    }

    @Override
    public String getAccessToken() {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps("access_token");
        String access_token = ops.get();
        if (null == access_token) {
            AccessToken accessToken = mpClient.getAccessToken("client_credential", APPID, SECRET);
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
