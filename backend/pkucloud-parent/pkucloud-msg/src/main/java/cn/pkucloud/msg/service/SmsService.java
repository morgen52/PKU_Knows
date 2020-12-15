package cn.pkucloud.msg.service;

import cn.pkucloud.common.Result;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

public interface SmsService {
    Result<?> sendSmsCode(String phone, String code, String ttl) throws TencentCloudSDKException;
}
