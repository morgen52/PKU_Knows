package cn.pkucloud.auth.wxa.service;

import cn.pkucloud.auth.wxa.entity.WxaScene;
import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface WxaAuthService {
    Result<String> getScene(String ip, String ua) throws JsonProcessingException;

    Result<WxaScene> getSceneInfo(String scene) throws JsonProcessingException;

    boolean connectScene(String ip, String ua, String scene) throws JsonProcessingException;

    Result<?> sendMsg(String scene, String msg);

    void deleteScene(String scene);
}
