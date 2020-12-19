package cn.pkucloud.qa.controller;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.qa.entity.Answer;
import cn.pkucloud.qa.entity.Question;
import cn.pkucloud.qa.service.QaService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/favorite")
@CrossOrigin
@Api(tags = {"收藏 API"})
public class FavoriteController {
    private final QaService qaService;

    public FavoriteController(QaService qaService) {
        this.qaService = qaService;
    }

    @GetMapping("question")
    public PageResult<Question> getFavoriteQuestion() {
        return qaService.getFavoriteQuestion();
    }

    @GetMapping("answer")
    public PageResult<Answer> getFavoriteAnswer() {
        return qaService.getFavoriteAnswer();
    }
}
