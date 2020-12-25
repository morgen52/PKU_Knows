package cn.pkucloud.qa.controller;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Answer;
import cn.pkucloud.qa.entity.Question;
import cn.pkucloud.qa.service.QaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("qa/favorite")
public class FavoriteController {
    @Autowired
    private QaService qaService;

    @GetMapping("question")
    public PageResult<Question> getFavoriteQuestionByPage(@RequestHeader("iss") String issuer,
                                                          @RequestHeader("uid") String uid,
                                                          @RequestHeader("role") String role,
                                                          @RequestHeader("mod") String mod,
                                                          @RequestParam int size,
                                                          @RequestParam int page) {
        return qaService.getFavoriteQuestionByPage(issuer, uid, role, mod, size, page);
    }

    @GetMapping("answer")
    public PageResult<Answer> getFavoriteAnswerByPage(@RequestHeader("iss") String issuer,
                                                      @RequestHeader("uid") String uid,
                                                      @RequestHeader("role") String role,
                                                      @RequestHeader("mod") String mod,
                                                      @RequestParam int size,
                                                      @RequestParam int page) {
        return qaService.getFavoriteAnswerByPage(issuer, uid, role, mod, size, page);
    }

    @PostMapping
    public Result<?> postFavorite(@RequestHeader("iss") String issuer,
                                  @RequestHeader("uid") String uid,
                                  @RequestHeader("role") String role,
                                  @RequestHeader("mod") String mod,
                                  @RequestParam String type,
                                  @RequestParam String id) {
        return qaService.postFavorite(issuer, uid, role, mod, type, id);
    }

    @DeleteMapping
    public Result<?> deleteFavoriteById(@RequestHeader("iss") String issuer,
                                        @RequestHeader("uid") String uid,
                                        @RequestHeader("role") String role,
                                        @RequestHeader("mod") String mod,
                                        @RequestParam String id) {
        return qaService.deleteFavoriteById(issuer, uid, role, mod, id);
    }
}
