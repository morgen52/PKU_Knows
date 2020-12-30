package cn.pkucloud.auth.sms.service.impl;

import cn.pkucloud.auth.sms.dto.WxspResult;
import cn.pkucloud.auth.sms.entity.SmsCode;
import cn.pkucloud.auth.sms.feign.MsgClient;
import cn.pkucloud.auth.sms.feign.WxspClient;
import cn.pkucloud.auth.sms.service.SmsAuthService;
import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static cn.pkucloud.common.ResultCode.AUTHORIZATION_REQUIRED;
import static cn.pkucloud.common.ResultCode.NOT_FOUND;

@Service
public class SmsAuthServiceImpl implements SmsAuthService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MsgClient msgClient;

    @Autowired
    private WxspClient wxspClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${sms.ttl}")
    private int ttl;

    @Override
    public Result<?> sendCode(String ip, String ua, String phone) throws JsonProcessingException {
        WxspResult wxspResult = wxspClient.getPkuId("M MicroMessenger", phone);
        if (wxspResult.isSuccess()) {
            String pkuId = wxspResult.getUserid();
            Random random = new Random();
            String code = String.format("%06d", random.nextInt(1000000));
            Result<?> smsCodeResult = msgClient.sendSmsCode(phone, code, String.valueOf(ttl));
            if (0 == smsCodeResult.getCode()) {
                SmsCode smsCode = new SmsCode(pkuId, code, ip, ua);
                String smsCodeStr = objectMapper.writeValueAsString(smsCode);
                BoundValueOperations<String, String> boundValueOps = stringRedisTemplate.boundValueOps(phone);
                boundValueOps.set(smsCodeStr, 60 * ttl, TimeUnit.SECONDS);
                return new Result<>();
            }
            return smsCodeResult;
        } else {
            return new Result<>(NOT_FOUND, "no pkuId found for the phone");
        }
    }

    @Override
    public Result<String> getPkuId(String ip, String ua, String phone, String code) throws JsonProcessingException {
        BoundValueOperations<String, String> boundValueOps = stringRedisTemplate.boundValueOps(phone);
        String smsCodeStr = boundValueOps.get();
        if (null != smsCodeStr) {
            SmsCode smsCode = objectMapper.readValue(smsCodeStr, SmsCode.class);
            if (smsCode.getIp().equals(ip) && smsCode.getUa().equals(ua) && smsCode.getCode().equals(code)) {
                String pkuId = smsCode.getPkuId();
                stringRedisTemplate.delete(phone);
                return new Result<>(pkuId);
            }
        }
        return new Result<>(AUTHORIZATION_REQUIRED, "authorization required");
    }
}
