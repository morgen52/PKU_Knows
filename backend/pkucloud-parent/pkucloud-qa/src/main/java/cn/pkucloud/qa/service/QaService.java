package cn.pkucloud.qa.service;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Answer;
import cn.pkucloud.qa.entity.Comment;
import cn.pkucloud.qa.entity.Question;
import cn.pkucloud.qa.entity.Report;

public interface QaService {
    Result<Question> getQuestionById(String id);

    PageResult<Question> getQuestionByPage(String page);

    PageResult<Question> getQuestionByTag(String tag);

    PageResult<Answer> getAnswerByQuestionId(String id);

    PageResult<Report> getReportByQuestionId(String id);

    Result<?> postQuestion();

    Result<?> postAnswerByQuestionId(String id);

    Result<?> postReportByQuestionId(String id);

    Result<?> putQuestionById(String id);

    Result<?> deleteQuestionById(String id);

    Result<Answer> getAnswerById(String id);

    PageResult<Comment> getCommentByAnswerId(String id);

    PageResult<Report> getReportByAnswerId(String id);

    Result<?> postReportByAnswerId(String id);

    Result<?> postCommentByCommentId(String id);

    Result<?> putAnswerById(String id);

    Result<?> deleteAnswerById(String id);

    Result<Comment> getCommentById(String id);

    PageResult<Report> getReportByCommentId(String id);

    Result<?> postReportByCommentId(String id);

    Result<?> putCommentById(String id);

    Result<?> deleteCommentById(String id);

    Result<Report> getReportById(String id);

    PageResult<Report> getReportByPage(String page);

    Result<?> putReportById(String id);

    Result<?> deleteReportById(String id);

    PageResult<Question> getFavoriteQuestion();

    PageResult<Answer> getFavoriteAnswer();

    PageResult<Question> getSubscriptionQuestion();
}
