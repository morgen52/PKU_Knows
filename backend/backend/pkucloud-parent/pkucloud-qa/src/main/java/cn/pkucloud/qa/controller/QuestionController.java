package cn.pkucloud.qa.controller;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Answer;
import cn.pkucloud.qa.entity.Question;
import cn.pkucloud.qa.entity.Report;
import cn.pkucloud.qa.service.QaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("question")
@CrossOrigin
public class QuestionController {
    private final QaService qaService;

    public QuestionController(QaService qaService) {
        this.qaService = qaService;
    }

    @GetMapping("{id}")
    public Result<Question> getQuestionById(@PathVariable String id) {
        return qaService.getQuestionById(id);
    }

    @GetMapping("page/{page}")
    public PageResult<Question> getQuestionByPage(@PathVariable String page) {
        return qaService.getQuestionByPage(page);
    }

    @GetMapping("tag/{tag}")
    public PageResult<Question> getQuestionByTag(@PathVariable String tag) {
        return qaService.getQuestionByTag(tag);
    }

    @GetMapping("{id}/answer")
    public PageResult<Answer> getAnswerByQuestionId(@PathVariable String id) {
        return qaService.getAnswerByQuestionId(id);
    }

    @GetMapping("{id}/report")
    public PageResult<Report> getReportByQuestionId(@PathVariable String id) {
        return qaService.getReportByQuestionId(id);
    }

    @PostMapping
    public Result<?> postQuestion() {
        return qaService.postQuestion();
    }

    @PostMapping("{id}/answer")
    public Result<?> postAnswerByQuestionId(@PathVariable String id) {
        return qaService.postAnswerByQuestionId(id);
    }

    @PostMapping("{id}/report")
    public Result<?> postReportByQuestionId(@PathVariable String id) {
        return qaService.postReportByQuestionId(id);
    }

    @PutMapping("{id}")
    public Result<?> putQuestionById(@PathVariable String id) {
        return qaService.putQuestionById(id);
    }

    @DeleteMapping("{id}")
    public Result<?> deleteQuestionById(@PathVariable String id) {
        return qaService.deleteQuestionById(id);
    }
}
