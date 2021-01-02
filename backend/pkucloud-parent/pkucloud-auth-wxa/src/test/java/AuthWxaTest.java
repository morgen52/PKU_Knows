import cn.pkucloud.auth.wxa.WxaAuthApplication;
import cn.pkucloud.auth.wxa.entity.WxaScene;
import cn.pkucloud.auth.wxa.service.WxaAuthService;
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
@SpringBootTest(classes = {WxaAuthApplication.class})
public class AuthWxaTest {
    @Autowired
    private WxaAuthService authwxaService;

    @Test
    public  void  AuthWxaTest() throws JsonProcessingException {

        Result<String> a = authwxaService.getScene("123456", "123456") ;
        System.out.println(a);
        Result<WxaScene> b = authwxaService.getSceneInfo("123456");
        System.out.println(b);
        boolean c = authwxaService.connectScene("123456", "123456", "123456");
        System.out.println(c);
        Result<?> d = authwxaService.sendToken("123456", "123456");
        System.out.println(d);
        authwxaService.deleteScene("123456");
        d = authwxaService.checkScene("123456", "123456", "123456");
        System.out.println(d);
    }
}