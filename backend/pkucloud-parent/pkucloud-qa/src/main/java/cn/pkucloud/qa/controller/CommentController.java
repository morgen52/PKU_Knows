package cn.pkucloud.qa.controller;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Comment;
import cn.pkucloud.qa.entity.Report;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
@CrossOrigin
public class CommentController {
    private final QaService qaService;

    public CommentController(QaService qaService) {
        this.qaService = qaService;
    }

    @GetMapping("{id}")
    public Result<Comment> getCommentById(@PathVariable String id) {
        return qaService.getCommentById(id);
    }

    @GetMapping("page/{page}")
    public PageResult<Comment> getCommentByPage(@PathVariable String page) {
        return qaService.getCommentByPage(page);
    }

    @GetMapping("{id}/report")
    public PageResult<Report> getReportByCommentId(@PathVariable String id) {
        return qaService.getReportByCommentId(id);
    }

    @PostMapping("{id}/report")
    public Result<?> postReportByCommentId(@PathVariable String id) {
        return qaService.postReportByCommentId(id);
    }

    @PutMapping("{id}")
    public Result<?> putCommentById(@PathVariable String id) {
        return qaService.putCommentById(id);
    }

    @DeleteMapping("{id}")
    public Result<?> deleteCommentById(@PathVariable String id) {
        return qaService.deleteCommentById(id);
    }
}
