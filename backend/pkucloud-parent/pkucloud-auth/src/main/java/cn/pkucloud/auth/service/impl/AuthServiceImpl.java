package cn.pkucloud.auth.service.impl;

import cn.pkucloud.auth.crypto.CryptoUtil;
import cn.pkucloud.auth.crypto.SHA1;
import cn.pkucloud.auth.dto.PkuUserInfoDto;
import cn.pkucloud.auth.dto.WxaLoginDto;
import cn.pkucloud.auth.dto.WxaUserInfoDto;
import cn.pkucloud.auth.entity.Auth;
import cn.pkucloud.auth.entity.PkuUserInfo;
import cn.pkucloud.auth.entity.WxUserInfo;
import cn.pkucloud.auth.entity.WxaScene;
import cn.pkucloud.auth.entity.wx.AccessToken;
import cn.pkucloud.auth.feign.SmsAuthClient;
import cn.pkucloud.auth.feign.WxSnsClient;
import cn.pkucloud.auth.feign.WxaAuthClient;
import cn.pkucloud.auth.feign.WxaClient;
import cn.pkucloud.auth.mapper.AuthMapper;
import cn.pkucloud.auth.mapper.PkuUserInfoMapper;
import cn.pkucloud.auth.mapper.WxUserInfoMapper;
import cn.pkucloud.auth.service.AuthService;
import cn.pkucloud.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

import static cn.pkucloud.auth.entity.wx.WxLoginType.*;
import static cn.pkucloud.common.ResultCode.AUTHORIZATION_REQUIRED;
import static cn.pkucloud.common.ResultCode.NOT_FOUND;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private PkuUserInfoMapper pkuUserInfoMapper;

    @Autowired
    private WxUserInfoMapper wxUserInfoMapper;

    @Autowired
    private WxaAuthClient wxaAuthClient;

    @Autowired
    private SmsAuthClient smsAuthClient;

    @Autowired
    private WxaClient wxaClient;

    @Autowired
    private WxSnsClient wxSnsClient;

    @Autowired
    private CryptoUtil cryptoUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${wx.wxa.cloudbase.token}")
    private String TOKEN;

    @Value("${wx.wxa.cloudbase.key}")
    private String KEY;

    @Value("${wx.app.appid}")
    private String APP_APPID;

    @Value("${wx.app.secret}")
    private String APP_SECRET;

    @Value("${wx.web.appid}")
    private String WEB_APPID;

    @Value("${wx.web.secret}")
    private String WEB_SECRET;

    @Value("${wx.wxh5.appid}")
    private String WXH5_APPID;

    @Value("${wx.wxh5.secret}")
    private String WXH5_SECRET;

    @Value("${jwt.key}")
    private String JWT_KEY;

    @Override
    public Result<String> getScene(String ip, String ua) {
        return wxaAuthClient.getScene(ip, ua);
    }

    @Override
    public byte[] getWxaCode(String ip, String ua, String scene) {
        Result<WxaScene> sceneInfoResult = wxaAuthClient.getSceneInfo(scene);
        if (0 == sceneInfoResult.getCode()) {
            WxaScene wxaScene = sceneInfoResult.getData();
            if (ip.equals(wxaScene.getIp()) && ua.equals(wxaScene.getUa())) {
                return wxaClient.getWxaCode(scene);
            }
        }
        return null;
    }

    @Override
    public Result<WxaScene> getSceneInfo(String scene) {
        Result<WxaScene> sceneInfo = wxaAuthClient.getSceneInfo(scene);
        wxaAuthClient.sendMsg(scene, "SCANNED");
        System.out.println("sceneInfo = " + sceneInfo);
        return sceneInfo;
    }

    @Override
    public Result<?> wxaLogin(String ip, String ua, String encrypt, String iv, String signature, int timestamp, String nonce) throws Exception {
        if ("TCB".equals(ua)) {
            String sign = SHA1.calcSHA1(TOKEN, timestamp, nonce, encrypt);
            if (signature.equals(sign)) {
                String decrypt = cryptoUtil.decrypt(encrypt, iv);
                WxaLoginDto wxaLoginDto = objectMapper.readValue(decrypt, WxaLoginDto.class);
                String scene = wxaLoginDto.getScene();
                PkuUserInfoDto pkuUserInfoDto = wxaLoginDto.getPkuUserInfo();
                WxaUserInfoDto wxaUserInfoDto = wxaLoginDto.getWxaUserInfo();
                Long id;
                String pkuId = pkuUserInfoDto.getPkuId();
                String unionId = wxaUserInfoDto.getUnionId();
                int access = (int) (System.currentTimeMillis() / 1000);

                // auth
                Auth auth = getAuthByPkuId(pkuId);
                Auth newAuth = Auth.builder()
                        .status(0)
                        .risk(0)
                        .access(access)
                        .pkuId(pkuId)
                        .wxUnionId(unionId)
                        .build();
                if (null == auth) {
                    newAuth.setRegister(access);
                    authMapper.insert(newAuth);
                    id = newAuth.getId();
                } else {
                    id = auth.getId();
                    newAuth.setId(id);
                    authMapper.updateById(newAuth);
                }

                // pkuUserInfo
                PkuUserInfo pkuUserInfo = getPkuUserInfoById(pkuId);
                PkuUserInfo newPkuUserInfo = new PkuUserInfo(pkuUserInfoDto);
                newPkuUserInfo.setAccess(access);
                if (null == pkuUserInfo) {
                    newPkuUserInfo.setRegister(access);
                    pkuUserInfoMapper.insert(newPkuUserInfo);
                } else {
                    pkuUserInfoMapper.updateById(newPkuUserInfo);
                }

                // wxUserInfo
                WxUserInfo wxUserInfo = getWxUserInfoById(unionId);
                WxUserInfo newWxUserInfo = new WxUserInfo(wxaUserInfoDto);
                newWxUserInfo.setAccess(access);
                if (null == wxUserInfo) {
                    newWxUserInfo.setRegister(access);
                    wxUserInfoMapper.insert(newWxUserInfo);
                } else {
                    wxUserInfoMapper.updateById(newWxUserInfo);
                }

                System.out.println("auth = " + auth);
                System.out.println("newAuth = " + newAuth);

                String jws = signJwt(id, "wxa");
                System.out.println("jws = " + jws);

                wxaAuthClient.sendMsg(scene, jws);
                return new Result<>();
            }
        }
        return new Result<>(AUTHORIZATION_REQUIRED, "authorization required");
    }

    @Override
    public Result<String> wxLogin(int type, String ip, String ua, String code, String state) throws JsonProcessingException {
        String appid;
        String secret;
        String typeStr = "wx-";
        switch (type) {
            case APP:
                appid = APP_APPID;
                secret = APP_SECRET;
                typeStr += "app";
                break;
            case WEB:
                appid = WEB_APPID;
                secret = WEB_SECRET;
                typeStr += "web";
                break;
            case WXH5:
                appid = WXH5_APPID;
                secret = WXH5_SECRET;
                typeStr += "wxh5";
                break;
            default:
                return new Result<>(AUTHORIZATION_REQUIRED, "authorization required");
        }
        String accessTokenStr = wxSnsClient.getAccessToken(appid, secret, code, "authorization_code");
        AccessToken accessToken = objectMapper.readValue(accessTokenStr, AccessToken.class);
        String unionid = accessToken.getUnionid();
        if (null != unionid) {
            Auth auth = getAuthByWxUnionId(unionid);
            if (null == auth) {
                return new Result<>(NOT_FOUND, "please register first");
            } else {
                Long id = auth.getId();
                String jws = signJwt(id, typeStr);
                return new Result<>(jws);
            }
        } else {
            return new Result<>(AUTHORIZATION_REQUIRED, "authorization required");
        }
    }

    @Override
    public Result<?> sendSmsCode(String ip, String ua, String phone) {
        return smsAuthClient.sendCode(ip, ua, phone);
    }

    @Override
    public Result<String> smsLogin(String ip, String ua, String phone, String code) {
        Result<String> result = smsAuthClient.getPkuId(ip, ua, phone, code);
        if (0 == result.getCode()) {
            String pkuId = result.getData();
            Long id;
            int access = (int) (System.currentTimeMillis() / 1000);
            Auth auth = getAuthByPkuId(pkuId);
            Auth newAuth = Auth.builder()
                    .status(0)
                    .risk(0)
                    .access(access)
                    .pkuId(pkuId)
                    .phone(phone)
                    .build();
            if (null == auth) {
                newAuth.setRegister(access);
                authMapper.insert(newAuth);
                id = newAuth.getId();
            } else {
                id = auth.getId();
                newAuth.setId(id);
                authMapper.updateById(newAuth);
            }

            String jws = signJwt(id, "sms");
            return new Result<>(jws);
        }
        return result;
    }

    @Override
    public Result<String> passwordLogin(String userName, String password) {
        return null;
    }

    private Auth getAuthById(long id) {
        return authMapper.selectById(id);
    }

    private Auth getAuthByPkuId(String pkuId) {
        QueryWrapper<Auth> wrapper = new QueryWrapper<>();
        wrapper.eq("pku_id", pkuId);
        return authMapper.selectOne(wrapper);
    }

    private Auth getAuthByWxUnionId(String wxUnionId) {
        QueryWrapper<Auth> wrapper = new QueryWrapper<>();
        wrapper.eq("wx_union_id", wxUnionId);
        return authMapper.selectOne(wrapper);
    }

    private PkuUserInfo getPkuUserInfoById(String pkuId) {
        return pkuUserInfoMapper.selectById(pkuId);
    }

    private WxUserInfo getWxUserInfoById(String unionId) {
        return wxUserInfoMapper.selectById(unionId);
    }

    private String signJwt(Long id, String type) {
        byte[] keyBytes = Base64.decodeBase64(JWT_KEY);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "HMACSHA256");
        long timestamp = System.currentTimeMillis();
        return Jwts.builder()
                .setIssuer(type)
                .setExpiration(new Date(timestamp + 7200000))
                .setSubject(String.valueOf(id))
                .claim("role", "user")
                .claim("mod", 6)
                .signWith(keySpec)
                .compact();
    }
}
