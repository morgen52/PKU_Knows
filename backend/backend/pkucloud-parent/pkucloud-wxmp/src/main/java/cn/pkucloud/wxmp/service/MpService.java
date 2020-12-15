package cn.pkucloud.wxmp.service;

import cn.pkucloud.wxmp.crypto.CryptoException;
import cn.pkucloud.wxmp.dto.wx.xml.XmlRequest;
import cn.pkucloud.wxmp.dto.wx.xml.XmlResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface MpService {
    String echo(String signature, String echostr, int timestamp, String nonce);

    XmlResponse msgHandler(String signature, int timestamp, String nonce, String openid, String encrypt_type, String msg_signature, XmlRequest request) throws JsonProcessingException, CryptoException;
}
