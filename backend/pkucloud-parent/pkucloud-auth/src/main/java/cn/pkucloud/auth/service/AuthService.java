package cn.pkucloud.auth.service;

import cn.pkucloud.auth.entity.WxaScene;
import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AuthService {
    Result<String> getScene(String ip, String ua);

    byte[] getWxaCode(String ip, String ua, String scene);

    Result<WxaScene> getSceneInfo(String scene);

    Result<?> wxaLogin(String ip, String ua, String encrypt, String iv, String signature, int timestamp, String nonce) throws Exception;

    Result<String> wxLogin(int wxh5, String ip, String ua, String code, String state) throws JsonProcessingException;

    Result<?> sendSmsCode(String ip, String ua, String phone);

    Result<String> smsLogin(String ip, String ua, String phone, String smsCode);

    Result<String> passwordLogin(String userName, String password);
}
