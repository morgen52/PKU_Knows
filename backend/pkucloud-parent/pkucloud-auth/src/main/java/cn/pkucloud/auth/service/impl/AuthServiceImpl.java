package cn.pkucloud.auth.service.impl;

import cn.pkucloud.auth.entity.Auth;
import cn.pkucloud.auth.entity.CodeInfo;
import cn.pkucloud.auth.entity.WxspResult;
import cn.pkucloud.auth.entity.wx.AccessToken;
import cn.pkucloud.auth.feign.MsgClient;
import cn.pkucloud.auth.feign.WxSnsClient;
import cn.pkucloud.auth.feign.WxspClient;
import cn.pkucloud.auth.mapper.AuthMapper;
import cn.pkucloud.auth.service.AuthService;
import cn.pkucloud.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static cn.pkucloud.auth.entity.wx.WxLoginType.*;
import static cn.pkucloud.common.ResultCode.BAD_REQUEST;
import static cn.pkucloud.common.ResultCode.NOT_FOUND;

@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth> implements AuthService {
    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final WxspClient wxspClient;

    private final MsgClient msgClient;

    private final WxSnsClient wxSnsClient;

    @Value("${sms.ttl}")
    private int ttl;

    @Value("${wx.app.appid}")
    private String appAppid;

    @Value("${wx.app.secret}")
    private String appSecret;

    @Value("${wx.web.appid}")
    private String webAppid;

    @Value("${wx.web.secret}")
    private String webSecret;

    @Value("${wx.wxh5.appid}")
    private String wxh5Appid;

    @Value("${wx.wxh5.secret}")
    private String wxh5Secret;

    public AuthServiceImpl(StringRedisTemplate redisTemplate, ObjectMapper objectMapper, WxspClient wxspClient, MsgClient msgClient, WxSnsClient wxSnsClient) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.wxspClient = wxspClient;
        this.msgClient = msgClient;
        this.wxSnsClient = wxSnsClient;
    }

    @Override
    public Result<?> sendSmsCode(String ip, String ua, String phone) throws JsonProcessingException {
        WxspResult wxspResult = wxspClient.getPkuId("M MicroMessenger", phone);
        if (wxspResult.isSuccess()) {
            String pkuId = wxspResult.getUserid();
            String code = String.format("%06d", new Random().nextInt(1000000));
            BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(phone);
            CodeInfo codeInfo = new CodeInfo(ip, ua, code, pkuId);
            ops.set(objectMapper.writeValueAsString(codeInfo), 60 * ttl, TimeUnit.SECONDS);
            return msgClient.sendSmsCode(phone, code, String.valueOf(ttl));
        }
        return new Result<>(NOT_FOUND, "no pkuId found for the phone");
    }

    @Override
    public Result<?> wxLogin(int type, String code, String state) throws JsonProcessingException {
        String appid;
        String secret;
        switch (type) {
            case APP:
                appid = appAppid;
                secret = appSecret;
                break;
            case WEB:
                appid = webAppid;
                secret = webSecret;
                break;
            case WXH5:
                appid = wxh5Appid;
                secret = wxh5Secret;
                break;
            default:
                return new Result<>(BAD_REQUEST, "bad request");
        }
        String accessTokenStr = wxSnsClient.getAccessToken(appid, secret, code, "authorization_code");
        System.out.println("accessTokenStr = " + accessTokenStr);
        AccessToken accessToken = objectMapper.readValue(accessTokenStr, AccessToken.class);
        System.out.println("accessToken = " + accessToken.toString());
        String access_token = accessToken.getAccess_token();
        String openid = accessToken.getOpenid();
        String unionid = accessToken.getUnionid();
        System.out.println("unionid = " + unionid);
        if (null != unionid) {
            Auth auth = getByWxUnionId(unionid);
            if (null != auth) {

            } else {

            }
            return new Result<>();
        }
        return new Result<>(BAD_REQUEST, "bad request");
    }

    private Auth getByWxUnionId(String wxUnionId) {
        QueryWrapper<Auth> wrapper = new QueryWrapper<>();
        wrapper.eq("wx_union_id", wxUnionId);
        return getOne(wrapper);
    }

    private Auth getByPhone(String phone) {
        QueryWrapper<Auth> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return getOne(wrapper);
    }

    private Auth getByPkuId(String pkuId) {
        QueryWrapper<Auth> wrapper = new QueryWrapper<>();
        wrapper.eq("pku_id", pkuId);
        return getOne(wrapper);
    }

    private Auth getByUserName(String userName) {
        QueryWrapper<Auth> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return getOne(wrapper);
    }

    private String signJwt(String id) {
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        return null;
    }
}
