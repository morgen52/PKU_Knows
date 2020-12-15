package cn.pkucloud.auth.service.impl;

import cn.pkucloud.auth.dto.PkuUserInfoDto;
import cn.pkucloud.auth.dto.WxaUserInfoDto;
import cn.pkucloud.auth.entity.*;
import cn.pkucloud.auth.entity.wx.AccessToken;
import cn.pkucloud.auth.feign.MsgClient;
import cn.pkucloud.auth.feign.WxSnsClient;
import cn.pkucloud.auth.feign.WxspClient;
import cn.pkucloud.auth.mapper.AuthMapper;
import cn.pkucloud.auth.mapper.PkuUserInfoMapper;
import cn.pkucloud.auth.mapper.WxUserInfoMapper;
import cn.pkucloud.auth.service.AuthService;
import cn.pkucloud.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static cn.pkucloud.auth.entity.wx.WxLoginType.*;
import static cn.pkucloud.common.ResultCode.*;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthMapper authMapper;

    private final WxUserInfoMapper wxUserInfoMapper;

    private final PkuUserInfoMapper pkuUserInfoMapper;

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

    public AuthServiceImpl(StringRedisTemplate redisTemplate, ObjectMapper objectMapper, WxspClient wxspClient, MsgClient msgClient, WxSnsClient wxSnsClient, AuthMapper authMapper, WxUserInfoMapper wxUserInfoMapper, PkuUserInfoMapper pkuUserInfoMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.wxspClient = wxspClient;
        this.msgClient = msgClient;
        this.wxSnsClient = wxSnsClient;
        this.authMapper = authMapper;
        this.wxUserInfoMapper = wxUserInfoMapper;
        this.pkuUserInfoMapper = pkuUserInfoMapper;
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
                // 登录
                String jws = signJwt(auth.getId());
                return new Result<>(jws);
            } else {
                // 要求注册
                return new Result<>(AUTHORIZATION_REQUIRED, "please register first");
            }
        }
        return new Result<>(BAD_REQUEST, "bad request");
    }

    @Override
    public Result<String> wxaLogin(String wxaUserInfoStr, String pkuUserInfoStr) throws JsonProcessingException {
        IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
        WxaUserInfoDto wxaUserInfoDto = objectMapper.readValue(wxaUserInfoStr, WxaUserInfoDto.class);
        PkuUserInfoDto pkuUserInfoDto = objectMapper.readValue(pkuUserInfoStr, PkuUserInfoDto.class);
        String wxUnionId = wxaUserInfoDto.getUnionId();
        String pkuId = pkuUserInfoDto.getPkuId();
        Auth wxAuth = getByWxUnionId(wxUnionId);
        Auth pkuAuth = getByPkuId(pkuId);
        long id = 0;
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        WxUserInfo wxUserInfo = new WxUserInfo(wxaUserInfoDto, timestamp, timestamp);
        if (null == wxAuth) {
            wxUserInfoMapper.insert(wxUserInfo);
        } else {
            wxUserInfo.setCreateTime(0);
            wxUserInfoMapper.updateById(wxUserInfo);
        }
        PkuUserInfo pkuUserInfo = new PkuUserInfo(pkuUserInfoDto, timestamp, timestamp);
        if (null == pkuAuth) {
            id = (long) identifierGenerator.nextId(null);
            Auth auth = Auth.builder()
                    .id(id)
                    .status(0)
                    .risk(0)
                    .createTime(timestamp)
                    .accessTime(timestamp)
                    .pkuId(pkuId)
                    .wxUnionId(wxUnionId)
                    .build();
            authMapper.insert(auth);

            pkuUserInfoMapper.insert(pkuUserInfo);
        } else {
            id = pkuAuth.getId();
            Auth auth = Auth.builder()
                    .id(id)
                    .accessTime(timestamp)
                    .build();
            authMapper.updateById(auth);

            pkuUserInfo.setCreateTime(0);
            pkuUserInfoMapper.updateById(pkuUserInfo);
        }
        String jws = signJwt(id);
        return new Result<>(jws);
    }

    @Override
    public Result<Auth> getAuthByWxUnionId(String wxUnionId) {
        Auth auth = getByWxUnionId(wxUnionId);
        if (null == auth) {
            return new Result<>(NOT_FOUND, "not found");
        }
        return new Result<>(auth);
    }

    private Auth getByWxUnionId(String wxUnionId) {
        QueryWrapper<Auth> wrapper = new QueryWrapper<>();
        wrapper.eq("wx_union_id", wxUnionId);
        return authMapper.selectOne(wrapper);
    }

    private Auth getByPkuId(String pkuId) {
        QueryWrapper<Auth> wrapper = new QueryWrapper<>();
        wrapper.eq("pku_id", pkuId);
        return authMapper.selectOne(wrapper);
    }

    private Auth getByUserName(String userName) {
        QueryWrapper<Auth> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return authMapper.selectOne(wrapper);
    }

    private String signJwt(long id) {
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .signWith(key)
                .compact();
    }
}
