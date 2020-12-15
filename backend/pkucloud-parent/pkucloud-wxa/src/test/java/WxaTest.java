

import cn.pkucloud.common.Result;
import cn.pkucloud.wxa.WxaApplication;
import cn.pkucloud.wxa.service.WxaService;
import cn.pkucloud.wxa.service.impl.WxaServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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
@SpringBootTest(classes = {WxaApplication.class})
public class WxaTest {
    @Autowired
    private WxaService wxaService;

    @Test
    public  void  WxaTest() throws JsonProcessingException {

        byte[] r=wxaService.getWxaCode("123","123","123");
        System.out.println(r);

    }
}
