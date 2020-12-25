package cn.pkucloud.qa.controller;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Question;
import cn.pkucloud.qa.service.QaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("qa/subscription")
public class SubscriptionController {
    @Autowired
    private QaService qaService;

    @GetMapping
    public PageResult<Question> getSubscriptionQuestionByPage(@RequestHeader("iss") String issuer,
                                                              @RequestHeader("uid") String uid,
                                                              @RequestHeader("role") String role,
                                                              @RequestHeader("mod") String mod,
                                                              @RequestParam int size,
                                                              @RequestParam int page) {
        return qaService.getSubscriptionQuestionByPage(issuer, uid, role, mod, size, page);
    }

    @PostMapping
    public Result<?> postSubscription(@RequestHeader("iss") String issuer,
                                      @RequestHeader("uid") String uid,
                                      @RequestHeader("role") String role,
                                      @RequestHeader("mod") String mod,
                                      @RequestParam String qid) {
        return qaService.postSubscription(issuer, uid, role, mod, qid);
    }

    @DeleteMapping("{id}")
    public Result<?> deleteSubscriptionById(@RequestHeader("iss") String issuer,
                                            @RequestHeader("uid") String uid,
                                            @RequestHeader("role") String role,
                                            @RequestHeader("mod") String mod,
                                            @PathVariable String id) {
        return qaService.deleteSubscriptionById(issuer, uid, role, mod, id);
    }
}
