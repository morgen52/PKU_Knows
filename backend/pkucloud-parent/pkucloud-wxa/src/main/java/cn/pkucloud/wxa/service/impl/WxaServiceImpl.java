package cn.pkucloud.wxa.service.impl;

import cn.pkucloud.wxa.dto.wx.WxaCodeRequest;
import cn.pkucloud.wxa.feign.WxaClient;
import cn.pkucloud.wxa.service.AccessTokenService;
import cn.pkucloud.wxa.service.WxaService;
import org.springframework.stereotype.Service;

@Service
public class WxaServiceImpl implements WxaService {
    private final AccessTokenService accessTokenService;

    private final WxaClient wxaClient;

    public WxaServiceImpl(AccessTokenService accessTokenService, WxaClient wxaClient) {
        this.accessTokenService = accessTokenService;
        this.wxaClient = wxaClient;
    }

    @Override
    public byte[] getWxaCode(String scene) {
        String access_token = accessTokenService.getAccessToken();
        return wxaClient.getWxaCode(access_token, new WxaCodeRequest(scene, "pages/auth/index"));
    }
}
