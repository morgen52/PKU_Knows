package cn.pkucloud.qa.controller;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.qa.entity.Question;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("subscription")
public class SubscriptionController {
    private final QaService qaService;

    public SubscriptionController(QaService qaService) {
        this.qaService = qaService;
    }

    @GetMapping("question")
    public PageResult<Question> getSubscriptionQuestion() {
        return qaService.getSubscriptionQuestion();
    }
}
