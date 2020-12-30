package cn.pkucloud.wxmp.service;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxmp.dto.wx.Signature;

public interface PublicService {
    Result<Signature> getSignature(String url);
}
