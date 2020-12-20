package cn.pkucloud.qa.controller;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Answer;
import cn.pkucloud.qa.entity.Comment;
import cn.pkucloud.qa.entity.Report;
import cn.pkucloud.qa.service.QaService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("qa/answer")
@CrossOrigin
@Api(tags = {"答案 API"})
public class AnswerController {
    private final QaService qaService;

    public AnswerController(QaService qaService) {
        this.qaService = qaService;
    }

    @GetMapping("{id}")
    public Result<Answer> getAnswerById(@PathVariable String id) {
        return qaService.getAnswerById(id);
    }

    @GetMapping("{id}/comment")
    public PageResult<Comment> getCommentByAnswerId(@PathVariable String id) {
        return qaService.getCommentByAnswerId(id);
    }

    @GetMapping("{id}/report")
    public PageResult<Report> getReportByAnswerId(@PathVariable String id) {
        return qaService.getReportByAnswerId(id);
    }

    @PostMapping("{id}/report")
    public Result<?> postReportByAnswerId(@PathVariable String id) {
        return qaService.postReportByAnswerId(id);
    }

    @PostMapping("{id}/comment")
    public Result<?> postComment(@PathVariable String id) {
        return qaService.postCommentByCommentId(id);
    }

    @PutMapping("{id}")
    public Result<?> putAnswerById(@PathVariable String id) {
        return qaService.putAnswerById(id);
    }

    @DeleteMapping("{id}")
    public Result<?> deleteAnswerById(@PathVariable String id) {
        return qaService.deleteAnswerById(id);
    }
}
