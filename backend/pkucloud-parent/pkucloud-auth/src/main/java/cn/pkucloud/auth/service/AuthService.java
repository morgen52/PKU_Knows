package cn.pkucloud.auth.service;

import cn.pkucloud.auth.entity.Auth;
import cn.pkucloud.common.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AuthService extends IService<Auth> {
    Result<?> sendSmsCode(String ip, String ua, String phone) throws JsonProcessingException;

    Result<?> wxLogin(int type, String code, String state) throws JsonProcessingException;

}
