package cn.pkucloud.qa.controller;

import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Comment;
import cn.pkucloud.qa.service.QaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("qa/comment")
public class CommentController {
    private final QaService qaService;

    public CommentController(QaService qaService) {
        this.qaService = qaService;
    }

    @GetMapping("{id}")
    public Result<Comment> getCommentById(@RequestHeader("iss") String issuer,
                                          @RequestHeader("uid") String uid,
                                          @RequestHeader("role") String role,
                                          @RequestHeader("mod") String mod,
                                          @PathVariable String id) {
        return qaService.getCommentById(issuer, uid, role, mod, id);
    }

    @PostMapping("{cid}/report")
    public Result<?> postReport(@RequestHeader("iss") String issuer,
                                @RequestHeader("uid") String uid,
                                @RequestHeader("role") String role,
                                @RequestHeader("mod") String mod,
                                @PathVariable String cid,
                                @RequestParam String txt) {
        return qaService.postReport(issuer, uid, role, mod, "comment", cid, txt);
    }

    @PutMapping("{id}")
    public Result<?> putLike(@RequestHeader("iss") String issuer,
                             @RequestHeader("uid") String uid,
                             @RequestHeader("role") String role,
                             @RequestHeader("mod") String mod,
                             @PathVariable String id,
                             @RequestParam int like) {
        return qaService.putCommentLike(issuer, uid, role, mod, id, like);
    }
}
