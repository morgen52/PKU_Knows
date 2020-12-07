package cn.pkucloud.wxa.service;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxa.entity.SceneInfo;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface WxaService {
    Result<String> getScene(String ip, String ua) throws JsonProcessingException;

    byte[] getWxaCode(String ip, String ua, String scene) throws JsonProcessingException;

    boolean connectScene(String ip, String ua, String scene) throws JsonProcessingException;

    Result<SceneInfo> getSceneInfo(String scene) throws JsonProcessingException;

    Result<?> loginByWxa(String appid, String wxaUserInfo, String pkuUserInfo, String scene, String nonceStr, int timestamp, String sign);
}
