package cn.pkucloud.qa.controller;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Answer;
import cn.pkucloud.qa.entity.Question;
import cn.pkucloud.qa.entity.Report;
import cn.pkucloud.qa.service.QaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("qa/question")
@CrossOrigin
@Api(tags = {"问题 API"})
public class QuestionController {
    private final QaService qaService;

    public QuestionController(QaService qaService) {
        this.qaService = qaService;
    }

    @ApiOperation("根据问题 id 获取问题")
    @GetMapping("{id}")
    public Result<Question> getQuestionById(@RequestHeader("id") String userId,
                                            @PathVariable String id) {
        return qaService.getQuestionById(id);
    }

    @ApiOperation("分页获取问题")
    @GetMapping("page/{page}")
    public PageResult<Question> getQuestionByPage(@RequestHeader("id") String userId,
                                                  @PathVariable String page) {
        return qaService.getQuestionByPage(page);
    }

    @ApiOperation("根据标签获取问题")
    @GetMapping("tag/{tag}")
    public PageResult<Question> getQuestionByTag(@RequestHeader("id") String userId,
                                                 @PathVariable String tag) {
        return qaService.getQuestionByTag(tag);
    }

    @ApiOperation("根据问题 id 获取答案")
    @GetMapping("{id}/answer")
    public PageResult<Answer> getAnswerByQuestionId(@RequestHeader("id") String userId,
                                                    @PathVariable String id) {
        return qaService.getAnswerByQuestionId(id);
    }

    @ApiOperation("根据问题 id 获取投诉")
    @GetMapping("{id}/report")
    public PageResult<Report> getReportByQuestionId(@RequestHeader("id") String userId,
                                                    @PathVariable String id) {
        return qaService.getReportByQuestionId(id);
    }

    @ApiOperation(value = "发布问题")
    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public Result<?> postQuestion(@RequestHeader("id") String userId,
                                  @ApiParam("问题标题") @RequestParam String title,
                                  @ApiParam("问题文字描述") @RequestParam String txt,
                                  @ApiParam("问题图片描述") @RequestParam String[] img) {
        return qaService.postQuestion();
    }

    @ApiOperation("发布答案")
    @PostMapping("{id}/answer")
    public Result<?> postAnswerByQuestionId(@RequestHeader("id") String userId,
                                            @PathVariable String id,
                                            @ApiParam(value = "答案文字描述") String txt,
                                            @ApiParam(value = "答案图片描述") String[] img) {
        return qaService.postAnswerByQuestionId(id);
    }

    @ApiOperation("发布问题投诉")
    @PostMapping("{id}/report")
    public Result<?> postReportByQuestionId(@RequestHeader("id") String userId,
                                            @PathVariable String id,
                                            @ApiParam("投诉文字描述") String txt) {
        return qaService.postReportByQuestionId(id);
    }

    @ApiOperation("赞同 / 反对问题")
    @PutMapping("{id}")
    public Result<?> putQuestionById(@RequestHeader("id") String userId,
                                     @PathVariable String id) {
        return qaService.putQuestionById(id);
    }

    @ApiOperation("根据问题 id 删除问题")
    @DeleteMapping("{id}")
    public Result<?> deleteQuestionById(@RequestHeader("id") String userId,
                                        @PathVariable String id) {
        return qaService.deleteQuestionById(id);
    }
}
