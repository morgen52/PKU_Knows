package cn.pkucloud.qa.controller;

import cn.pkucloud.common.Result;
import cn.pkucloud.qa.service.QaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("qa/like")
public class LikeController {
    @Autowired
    private QaService qaService;

    @GetMapping("question")
    public Result<Map<String, String>> getQuestionLike(@RequestHeader("iss") String issuer,
                                               @RequestHeader("uid") String uid,
                                               @RequestHeader("role") String role,
                                               @RequestHeader("mod") String mod) {
        return qaService.getQuestionLike(uid);
    }

    @GetMapping("answer")
    public Result<Map<String, String>> getAnswerLike(@RequestHeader("iss") String issuer,
                                              @RequestHeader("uid") String uid,
                                              @RequestHeader("role") String role,
                                              @RequestHeader("mod") String mod) {
        return qaService.getAnswerLike(uid);
    }
}
