import cn.pkucloud.auth.service.AuthService;
import cn.pkucloud.auth.AuthApplication;
import cn.pkucloud.auth.dto.Wxh5Signature;
import cn.pkucloud.auth.entity.WxaScene;
import cn.pkucloud.common.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AuthApplication.class})
public class AuthTest {
    @Autowired
    private AuthService authService;

    @Test
    public  void  AuthTest() throws JsonProcessingException{
        byte[] r;
        String ip = "123456";
        String ua = "123456";
        String tmps = "123456";
        String scene = "123456";
        int tmpi = 1;
        Result<WxaScene> a;
        Result<?> b;
        r = authService.getWxaCode(ip, ua, scene);
        System.out.println(r);
        a = authService.getSceneInfo(scene);
        System.out.println(a);
//        b = authService.wxaLogin(ip, ua, tmps, tmps, tmps, tmpi, tmps);
//        System.out.println(b);
        Result<String> c;
        Result<Wxh5Signature> d;
//        c = authService.wxLogin(tmpi, ip, ua, tmps, tmps);
//        System.out.println(c);
        b = authService.sendSmsCode(ip, ua, tmps);
        System.out.println(b);
        c = authService.smsLogin(ip, ua, tmps, tmps);
        System.out.println(c);
        c = authService.passwordLogin("LiuMG", "liumugeng");
        System.out.println(c);
        d = authService.getWxh5Signature(tmps);
        System.out.println(d);
        b = authService.getUserInfo(tmps);
        System.out.println(b);
        b = authService.setPassword(tmps, tmps, tmps);
        System.out.println(b);
    }
}
