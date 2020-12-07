package cn.pkucloud.auth.service;

import cn.pkucloud.auth.entity.Auth;
import cn.pkucloud.auth.entity.WxUserInfo;
import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AuthService {
    Result<?> sendSmsCode(String ip, String ua, String phone) throws JsonProcessingException;

    Result<?> wxLogin(int type, String code, String state) throws JsonProcessingException;

    Result<String> wxaLogin(String wxaUserInfoStr, String pkuUserInfoStr) throws JsonProcessingException;

    Result<Auth> getAuthByWxUnionId(String wxUnionId);
}
