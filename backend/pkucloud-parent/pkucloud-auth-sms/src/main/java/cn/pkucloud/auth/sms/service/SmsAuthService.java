package cn.pkucloud.auth.sms.service;

import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SmsAuthService {
    Result<?> sendCode(String ip, String ua, String phone) throws JsonProcessingException;

    Result<String> getPkuId(String ip, String ua, String phone, String code) throws JsonProcessingException;
}
