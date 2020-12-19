package cn.pkucloud.obs.service.impl;

import cn.pkucloud.common.Result;
import cn.pkucloud.obs.service.ObsService;
import com.obs.services.ObsClient;
import com.obs.services.model.PostSignatureRequest;
import com.obs.services.model.PostSignatureResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ObsServiceImpl implements ObsService {
    @Value("${huaweicloud.obs.endpoint}")
    private String endPoint;

    @Value("${huaweicloud.obs.ak}")
    private String ak;

    @Value("${huaweicloud.obs.sk}")
    private String sk;

    @Override
    public Result<?> getPostSignature() {

        return null;
    }
}
