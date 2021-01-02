package cn.pkucloud.qa.controller;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.service.QaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("qa/favorite")
public class FavoriteController {
    @Autowired
    private QaService qaService;

    @GetMapping("question")
    public PageResult<?> getFavoriteQuestionByPage(@RequestHeader("iss") String issuer,
                                                          @RequestHeader("uid") String uid,
                                                          @RequestHeader("role") String role,
                                                          @RequestHeader("mod") String mod,
                                                          @RequestParam int size,
                                                          @RequestParam int page) {
        return qaService.getFavoriteByPage(issuer, uid, role, mod, "question", size, page);
    }

    @GetMapping("question/ids")
    public Result<List<String>> getFavoriteQuestionIds(@RequestHeader("iss") String issuer,
                                                       @RequestHeader("uid") String uid,
                                                       @RequestHeader("role") String role,
                                                       @RequestHeader("mod") String mod) {
        return qaService.getFavoriteIds(uid, "question");
    }

    @GetMapping("answer")
    public PageResult<?> getFavoriteAnswerByPage(@RequestHeader("iss") String issuer,
                                                      @RequestHeader("uid") String uid,
                                                      @RequestHeader("role") String role,
                                                      @RequestHeader("mod") String mod,
                                                      @RequestParam int size,
                                                      @RequestParam int page) {
        return qaService.getFavoriteByPage(issuer, uid, role, mod, "answer", size, page);
    }

    @GetMapping("answer/ids")
    public Result<List<String>> getFavoriteAnswerIds(@RequestHeader("iss") String issuer,
                                                     @RequestHeader("uid") String uid,
                                                     @RequestHeader("role") String role,
                                                     @RequestHeader("mod") String mod) {
        return qaService.getFavoriteIds(uid, "answer");
    }

    @PostMapping
    public Result<?> postFavorite(@RequestHeader("iss") String issuer,
                                  @RequestHeader("uid") String uid,
                                  @RequestHeader("role") String role,
                                  @RequestHeader("mod") String mod,
                                  @RequestParam String type,
                                  @RequestParam String oid) {
        return qaService.postFavorite(issuer, uid, role, mod, type, oid);
    }

    @DeleteMapping("{id}")
    public Result<?> deleteFavoriteById(@RequestHeader("iss") String issuer,
                                        @RequestHeader("uid") String uid,
                                        @RequestHeader("role") String role,
                                        @RequestHeader("mod") String mod,
                                        @PathVariable String id) {
        return qaService.deleteFavoriteById(issuer, uid, role, mod, id);
    }
}
