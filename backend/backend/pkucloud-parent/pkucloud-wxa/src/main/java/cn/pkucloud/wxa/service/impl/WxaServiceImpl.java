package cn.pkucloud.wxa.service.impl;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxa.entity.SceneInfo;
import cn.pkucloud.wxa.entity.wx.AccessToken;
import cn.pkucloud.wxa.entity.wx.WxaCodeRequest;
import cn.pkucloud.wxa.feign.AuthClient;
import cn.pkucloud.wxa.feign.WxaClient;
import cn.pkucloud.wxa.service.WxaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static cn.pkucloud.common.ResultCode.AUTHORIZATION_REQUIRED;
import static cn.pkucloud.common.ResultCode.NOT_FOUND;
import static cn.pkucloud.wxa.entity.SceneState.*;

@Service
public class WxaServiceImpl implements WxaService {
    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final WxaClient wxaClient;

    private final AuthClient authClient;

    @Value("${wx.wxa.appid}")
    private String APPID;

    @Value("${wx.wxa.secret}")
    private String SECRET;

    @Value("${wx.wxa.cloudbase.appid}")
    private String CLOUDBASE_APPID;

    @Value("${wx.wxa.cloudbase.secret}")
    private String CLOUDBASE_SECRET;

    @Value("${scene.ttl}")
    private int ttl;

    public WxaServiceImpl(StringRedisTemplate redisTemplate, ObjectMapper objectMapper, WxaClient wxaClient, AuthClient authClient) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.wxaClient = wxaClient;
        this.authClient = authClient;
    }

    @Override
    public Result<String> getScene(String ip, String ua) throws JsonProcessingException {
        String scene = UUID.randomUUID().toString().replace("-", "");
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(scene);
        String s = objectMapper.writeValueAsString(new SceneInfo(ip, ua, GENERATED));
        ops.set(s, ttl, TimeUnit.SECONDS);
        return new Result<>(scene);
    }

    @Override
    public boolean connectScene(String ip, String ua, String scene) throws JsonProcessingException {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(scene);
        String s = ops.get();
        if (null != s) {
            SceneInfo sceneInfo = objectMapper.readValue(s, SceneInfo.class);
            if (sceneInfo.getIp().equals(ip) && sceneInfo.getUa().equals(ua) && GENERATED == sceneInfo.getState()) {
                sceneInfo.setState(CONNECTED);
                s = objectMapper.writeValueAsString(sceneInfo);
                ops.set(s, ttl, TimeUnit.SECONDS);
                return true;
            }
        }
        return false;
    }

    @Override
    public byte[] getWxaCode(String ip, String ua, String scene) throws JsonProcessingException {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(scene);
        String s = ops.get();
        if (null != s) {
            SceneInfo sceneInfo = objectMapper.readValue(s, SceneInfo.class);
            if (sceneInfo.getIp().equals(ip) && sceneInfo.getUa().equals(ua) && CONNECTED >= sceneInfo.getState()) {
                String access_token = getAccessToken();
                WxaCodeRequest request = new WxaCodeRequest(scene, "pages/auth/index");
                return wxaClient.getWxaCode(access_token, request);
            }
        }
        return null;
    }

    @Override
    public Result<SceneInfo> getSceneInfo(String scene) throws JsonProcessingException {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(scene);
        String s = ops.get();
        if (null != s) {
            SceneInfo sceneInfo = objectMapper.readValue(s, SceneInfo.class);
            if (CONNECTED == sceneInfo.getState()) {
                sceneInfo.setState(SCANNED);
                s = objectMapper.writeValueAsString(sceneInfo);
                ops.set(s, ttl, TimeUnit.SECONDS);
                return new Result<>(sceneInfo);
            }
        }
        return new Result<>(NOT_FOUND, "not found");
    }

    @Override
    public Result<?> loginByWxa(String appid, String wxaUserInfo, String pkuUserInfo, String scene, String nonceStr, int timestamp, String sign) {
        if (CLOUDBASE_APPID.equals(appid)) {
            String strToSign =
                    "appid=" + appid +
                    "&nonceStr=" + nonceStr +
                    "&pkuUserInfo=" + pkuUserInfo +
                    "&scene=" + scene +
                    "&timestamp=" + timestamp +
                    "&wxaUserInfo=" + wxaUserInfo +
                    "&secret=" + CLOUDBASE_SECRET;
            System.out.println("strToSign = " + strToSign);
            String sha1 = DigestUtils.sha1Hex(strToSign);
            if (sha1.equals(sign)) {
                Result<?> result = authClient.loginByWxa(wxaUserInfo, pkuUserInfo);
                return new Result<>();
            }
        }
        return new Result<>(AUTHORIZATION_REQUIRED, "authorization required");
    }

    private String getAccessToken() {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps("access_token");
        String access_token = ops.get();
        if (access_token == null) {
            AccessToken accessToken = wxaClient.getAccessToken("client_credential", APPID, SECRET);
            access_token = accessToken.getAccess_token();
            if (null != access_token) {
                int expires_in = accessToken.getExpires_in();
                ops.set(access_token, expires_in, TimeUnit.SECONDS);
            }
        }
        return access_token;
    }
}
