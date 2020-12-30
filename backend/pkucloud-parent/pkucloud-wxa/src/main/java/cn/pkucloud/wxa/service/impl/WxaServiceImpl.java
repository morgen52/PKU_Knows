package cn.pkucloud.wxa.service.impl;

import cn.pkucloud.wxa.dto.wx.AccessToken;
import cn.pkucloud.wxa.dto.wx.WxaCodeRequest;
import cn.pkucloud.wxa.feign.WxaClient;
import cn.pkucloud.wxa.service.WxaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class WxaServiceImpl implements WxaService {
    private final StringRedisTemplate stringRedisTemplate;

    private final WxaClient wxaClient;

    @Value("${wx.wxa.appid}")
    private String APPID;

    @Value("${wx.wxa.secret}")
    private String SECRET;

    public WxaServiceImpl(StringRedisTemplate stringRedisTemplate, WxaClient wxaClient) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.wxaClient = wxaClient;
    }

    @Override
    public byte[] getWxaCode(String scene) {
        String access_token = getAccessToken();
        return wxaClient.getWxaCode(access_token, new WxaCodeRequest(scene, "pages/auth/index"));
    }

    private String getAccessToken() {
        BoundValueOperations<String, String> ops = stringRedisTemplate.boundValueOps("access_token");
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
