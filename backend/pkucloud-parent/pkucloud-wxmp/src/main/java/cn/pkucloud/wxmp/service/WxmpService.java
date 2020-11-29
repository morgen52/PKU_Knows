package cn.pkucloud.wxmp.service;

import cn.pkucloud.common.Result;

public interface WxmpService {
    Result<String> getJsapiTicket();
}
