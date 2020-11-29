package cn.pkucloud.msg.service.impl;

import cn.pkucloud.common.Result;
import cn.pkucloud.msg.service.SmsService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static cn.pkucloud.common.ResultCode.INTERNAL_SERVER_ERROR;
import static com.tencentcloudapi.common.profile.Region.Beijing;

@Service
public class SmsServiceImpl implements SmsService {
    @Value("${tencentcloud.sms.secretId}")
    private String secretId;

    @Value("${tencentcloud.sms.secretKey}")
    private String secretKey;

    @Value("${tencentcloud.sms.appid}")
    private String appid;

    @Value("${tencentcloud.sms.sign}")
    private String sign;

    @Value("${tencentcloud.sms.templateID}")
    private String templateID;

    @Override
    public Result<?> sendSmsCode(String phone, String code, String ttl) throws TencentCloudSDKException {
        String[] phoneNumbers = {"+86" + phone};
        String[] templateParams = {code, ttl};

        Credential credential = new Credential(secretId, secretKey);
        SmsClient client = new SmsClient(credential, Beijing.toString());
        SendSmsRequest request = new SendSmsRequest();
        request.setSmsSdkAppid(appid);
        request.setSign(sign);
        request.setTemplateID(templateID);
        request.setPhoneNumberSet(phoneNumbers);
        request.setTemplateParamSet(templateParams);
        SendSmsResponse response = client.SendSms(request);
        SendStatus status = response.getSendStatusSet()[0];
        if ("Ok".equals(status.getCode())) {
            return new Result<>();
        }
        return new Result<>(INTERNAL_SERVER_ERROR, status.getMessage());
    }
}
