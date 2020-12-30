package cn.pkucloud.qa.controller;

import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Comment;
import cn.pkucloud.qa.service.QaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("qa/comment")
public class CommentController {
    @Autowired
    private QaService qaService;

    @GetMapping("{id}")
    public Result<Comment> getCommentById(@RequestHeader("iss") String issuer,
                                          @RequestHeader("uid") String uid,
                                          @RequestHeader("role") String role,
                                          @RequestHeader("mod") String mod,
                                          @PathVariable String id) {
        return qaService.getCommentById(issuer, uid, role, mod, id);
    }
}
