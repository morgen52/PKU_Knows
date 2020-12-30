import cn.pkucloud.auth.sms.SmsAuthApplication;
import cn.pkucloud.auth.sms.service.SmsAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import cn.pkucloud.common.Result;
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
@SpringBootTest(classes = {SmsAuthApplication.class})
public class AuthSmsTest {
    @Autowired
    private SmsAuthService authsmsService;

    @Test
    public  void  AuthSmsTest() throws JsonProcessingException {

        Result<?> a = authsmsService.sendCode("123456", "123456", "123456") ;
        System.out.println(a);

        Result<String> b = authsmsService.getPkuId("123456", "123456","123456", "123456") ;
        System.out.println(b);
    }
}