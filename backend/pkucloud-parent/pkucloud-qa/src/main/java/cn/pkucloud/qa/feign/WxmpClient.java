package cn.pkucloud.qa.feign;

import cn.pkucloud.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "wxmp", url = "https://mp.pkucs.cn/api")
public interface WxmpClient {
    @PostMapping("msg/answer")
    Result<?> sendAnswerMsg(@RequestParam List<String> idList,
                            @RequestParam String first,
                            @RequestParam String title,
                            @RequestParam String userName,
                            @RequestParam String time,
                            @RequestParam String remark);
}
