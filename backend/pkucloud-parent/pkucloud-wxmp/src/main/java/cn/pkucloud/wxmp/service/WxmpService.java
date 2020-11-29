package cn.pkucloud.wxmp.service;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxmp.entity.wx.Signature;

import java.security.NoSuchAlgorithmException;

public interface WxmpService {
    Result<Signature> getSignature();
}
