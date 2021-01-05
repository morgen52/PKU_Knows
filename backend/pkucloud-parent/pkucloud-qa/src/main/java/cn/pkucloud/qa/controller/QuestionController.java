package cn.pkucloud.qa.controller;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Answer;
import cn.pkucloud.qa.entity.Question;
import cn.pkucloud.qa.service.QaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("qa/question")
public class QuestionController {
    private final QaService qaService;

    public QuestionController(QaService qaService) {
        this.qaService = qaService;
    }

    @GetMapping
    public PageResult<Question> getQuestionByPage(@RequestHeader("iss") String issuer,
                                                  @RequestHeader("uid") String uid,
                                                  @RequestHeader("role") String role,
                                                  @RequestHeader("mod") String mod,
                                                  @RequestParam(required = false) String topic,
                                                  @RequestParam(required = false) String[] tag,
                                                  @RequestParam(required = false) String regex,
                                                  @RequestParam int size,
                                                  @RequestParam int page) {
        if (null != topic) {
            return qaService.getQuestionByTopic(issuer, uid, role, mod, topic, size, page);
        }
        if (null != tag) {
            return qaService.getQuestionByTag(issuer, uid, role, mod, tag, size, page);
        }
        if (null != regex) {
            return qaService.getQuestionByRegex(issuer, uid, role, mod, regex, size, page);
        }
        return qaService.getQuestionByPage(issuer, uid, role, mod, size, page);
    }

    @GetMapping("{id}")
    public Result<Question> getQuestionById(@RequestHeader("iss") String issuer,
                                            @RequestHeader("uid") String uid,
                                            @RequestHeader("role") String role,
                                            @RequestHeader("mod") String mod,
                                            @PathVariable String id) {
        return qaService.getQuestionById(issuer, uid, role, mod, id);
    }

    @PostMapping
    public Result<?> postQuestion(@RequestHeader("iss") String issuer,
                                  @RequestHeader("uid") String uid,
                                  @RequestHeader("role") String role,
                                  @RequestHeader("mod") String mod,
                                  @RequestParam String title,
                                  @RequestParam String txt,
                                  @RequestParam(required = false) String[] img,
                                  @RequestParam String topic,
                                  @RequestParam String tag,
                                  @RequestParam int setting,
                                  @RequestParam boolean subscribe) {
        return qaService.postQuestion(issuer, uid, role, mod, title, txt, img, topic, tag, setting, subscribe);
    }

    @GetMapping("{qid}/answer")
    public PageResult<Answer> getAnswerByQid(@RequestHeader("iss") String issuer,
                                             @RequestHeader("uid") String uid,
                                             @RequestHeader("role") String role,
                                             @RequestHeader("mod") String mod,
                                             @PathVariable String qid,
                                             @RequestParam int size,
                                             @RequestParam int page) {
        return qaService.getAnswerByQid(issuer, uid, role, mod, qid, size, page);
    }

    @PostMapping("{qid}/answer")
    public Result<?> postAnswer(@RequestHeader("iss") String issuer,
                                @RequestHeader("uid") String uid,
                                @RequestHeader("role") String role,
                                @RequestHeader("mod") String mod,
                                @PathVariable String qid,
                                @RequestParam String txt,
                                @RequestParam(required = false) String[] img,
                                @RequestParam int setting,
                                @RequestParam boolean subscribe) {
        return qaService.postAnswer(issuer, uid, role, mod, qid, txt, img, setting, subscribe);
    }

    @PostMapping("{qid}/report")
    public Result<?> postReport(@RequestHeader("iss") String issuer,
                                @RequestHeader("uid") String uid,
                                @RequestHeader("role") String role,
                                @RequestHeader("mod") String mod,
                                @PathVariable String qid,
                                @RequestParam String txt) {
        return qaService.postReport(issuer, uid, role, mod, "question", qid, txt);
    }

    @PutMapping("{id}")
    public Result<?> putLike(@RequestHeader("iss") String issuer,
                             @RequestHeader("uid") String uid,
                             @RequestHeader("role") String role,
                             @RequestHeader("mod") String mod,
                             @PathVariable String id,
                             @RequestParam int like) {
        return qaService.putQuestionLike(issuer, uid, role, mod, id, like);
    }
}
