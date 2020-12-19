package cn.pkucloud.auth.wxa.service;

import cn.pkucloud.auth.wxa.entity.WxaScene;
import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface WxaAuthService {
    Result<String> getScene(String ip, String ua) throws JsonProcessingException;

    Result<WxaScene> getSceneInfo(String scene) throws JsonProcessingException;
}
