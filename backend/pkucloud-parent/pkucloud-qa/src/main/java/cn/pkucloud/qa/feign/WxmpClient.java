package cn.pkucloud.qa.feign;

import cn.pkucloud.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wxmp-msg", url = "https://mp.pkucs.cn/api/msg/")
public interface WxmpClient {
    @PostMapping("answer")
    Result<?> sendAnswerMsg(@RequestParam String[] ids,
                            @RequestParam String first,
                            @RequestParam String title,
                            @RequestParam String userName,
                            @RequestParam String time,
                            @RequestParam String remark);
}
