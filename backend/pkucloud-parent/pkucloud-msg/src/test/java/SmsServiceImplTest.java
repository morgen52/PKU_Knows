package cn.pkucloud.msg.service.impl;
import cn.pkucloud.msg.service.impl.SmsServiceImpl;
import cn.pkucloud.msg.service.SmsService;
import cn.pkucloud.common.Result;
import cn.pkucloud.msg.MsgApplication;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MsgApplication.class})
public class SmsServiceImplTest {
    @Autowired
    private SmsService smsService;

    @Test
    public  void  implTest() throws TencentCloudSDKException {

        String phone="15321536852";
        String code="123456";
        String ttl="5";

        Result<?> r=smsService.sendSmsCode(phone, code, ttl);
        System.out.println(r);

    }
}