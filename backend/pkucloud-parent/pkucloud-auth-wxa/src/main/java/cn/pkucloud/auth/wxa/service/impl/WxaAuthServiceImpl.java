package cn.pkucloud.auth.wxa.service.impl;

import cn.pkucloud.auth.wxa.entity.WxaScene;
import cn.pkucloud.auth.wxa.netty.SceneChannelMap;
import cn.pkucloud.auth.wxa.service.WxaAuthService;
import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static cn.pkucloud.auth.wxa.entity.WxaSceneState.CONNECTED;
import static cn.pkucloud.auth.wxa.entity.WxaSceneState.GENERATED;
import static cn.pkucloud.common.ResultCode.NOT_FOUND;

@Service
public class WxaAuthServiceImpl implements WxaAuthService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Result<String> getScene(String ip, String ua) throws JsonProcessingException {
        String scene = UUID.randomUUID().toString().replace("-", "");
        BoundValueOperations<String, String> boundValueOps = stringRedisTemplate.boundValueOps(scene);
        WxaScene wxaScene = new WxaScene(GENERATED, ip, ua);
        String wxaSceneStr = objectMapper.writeValueAsString(wxaScene);
        boundValueOps.set(wxaSceneStr, 60, TimeUnit.SECONDS);
        return new Result<>(scene);
    }

    @Override
    public Result<WxaScene> getSceneInfo(String scene) throws JsonProcessingException {
        BoundValueOperations<String, String> boundValueOps = stringRedisTemplate.boundValueOps(scene);
        String wxaSceneStr = boundValueOps.get();
        if (null != wxaSceneStr) {
            WxaScene wxaScene = objectMapper.readValue(wxaSceneStr, WxaScene.class);
            return new Result<>(wxaScene);
        }
        return new Result<>(NOT_FOUND, "not found");
    }

    @Override
    public boolean connectScene(String ip, String ua, String scene) throws JsonProcessingException {
        BoundValueOperations<String, String> boundValueOps = stringRedisTemplate.boundValueOps(scene);
        String wxaSceneStr = boundValueOps.get();
        if (null != wxaSceneStr) {
            WxaScene wxaScene = objectMapper.readValue(wxaSceneStr, WxaScene.class);
            if (wxaScene.getIp().equals(ip) && wxaScene.getUa().equals(ua) && wxaScene.getState() == GENERATED) {
                wxaScene.setState(CONNECTED);
                return true;
            }
        }
        return false;
    }

    @Override
    public Result<?> sendMsg(String scene, String msg) {
        Channel channel = SceneChannelMap.get(scene);
        if (null != channel) {
            channel.writeAndFlush(new TextWebSocketFrame(msg));
            SceneChannelMap.removeByScene(scene);
            channel.close();
            return new Result<>();
        }
        return new Result<>(NOT_FOUND, "not found");
    }

    @Override
    public void deleteScene(String scene) {
        stringRedisTemplate.delete(scene);
    }
}
