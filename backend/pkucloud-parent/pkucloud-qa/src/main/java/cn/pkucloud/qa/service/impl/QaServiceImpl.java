package cn.pkucloud.qa.service.impl;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.dao.AnswerDao;
import cn.pkucloud.qa.dao.CommentDao;
import cn.pkucloud.qa.dao.QuestionDao;
import cn.pkucloud.qa.dao.ReportDao;
import cn.pkucloud.qa.entity.Answer;
import cn.pkucloud.qa.entity.Comment;
import cn.pkucloud.qa.entity.Question;
import cn.pkucloud.qa.entity.Report;
import cn.pkucloud.qa.service.QaService;
import org.springframework.stereotype.Service;

@Service
public class QaServiceImpl implements QaService {
    private final QuestionDao questionDao;

    private final AnswerDao answerDao;

    private final CommentDao commentDao;

    private final ReportDao reportDao;

    public QaServiceImpl(QuestionDao questionDao, AnswerDao answerDao, CommentDao commentDao, ReportDao reportDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.commentDao = commentDao;
        this.reportDao = reportDao;
    }

    @Override
    public Result<Question> getQuestionById(String id) {
        return null;
    }

    @Override
    public PageResult<Question> getQuestionByPage(String page) {
        return null;
    }

    @Override
    public PageResult<Question> getQuestionByTag(String tag) {
        return null;
    }

    @Override
    public PageResult<Answer> getAnswerByQuestionId(String id) {
        return null;
    }

    @Override
    public PageResult<Report> getReportByQuestionId(String id) {
        return null;
    }

    @Override
    public Result<?> postQuestion() {
        return null;
    }

    @Override
    public Result<?> postAnswerByQuestionId(String id) {
        return null;
    }

    @Override
    public Result<?> postReportByQuestionId(String id) {
        return null;
    }

    @Override
    public Result<?> putQuestionById(String id) {
        return null;
    }

    @Override
    public Result<?> deleteQuestionById(String id) {
        return null;
    }

    @Override
    public Result<Answer> getAnswerById(String id) {
        return null;
    }

    @Override
    public PageResult<Comment> getCommentByAnswerId(String id) {
        return null;
    }

    @Override
    public PageResult<Report> getReportByAnswerId(String id) {
        return null;
    }

    @Override
    public Result<?> postReportByAnswerId(String id) {
        return null;
    }

    @Override
    public Result<?> postCommentByCommentId(String id) {
        return null;
    }

    @Override
    public Result<?> putAnswerById(String id) {
        return null;
    }

    @Override
    public Result<?> deleteAnswerById(String id) {
        return null;
    }

    @Override
    public Result<Comment> getCommentById(String id) {
        return null;
    }

    @Override
    public PageResult<Report> getReportByCommentId(String id) {
        return null;
    }

    @Override
    public Result<?> postReportByCommentId(String id) {
        return null;
    }

    @Override
    public Result<?> putCommentById(String id) {
        return null;
    }

    @Override
    public Result<?> deleteCommentById(String id) {
        return null;
    }

    @Override
    public Result<Report> getReportById(String id) {
        return null;
    }

    @Override
    public PageResult<Report> getReportByPage(String page) {
        return null;
    }

    @Override
    public Result<?> putReportById(String id) {
        return null;
    }

    @Override
    public Result<?> deleteReportById(String id) {
        return null;
    }

    @Override
    public PageResult<Question> getFavoriteQuestion() {
        return null;
    }

    @Override
    public PageResult<Answer> getFavoriteAnswer() {
        return null;
    }

    @Override
    public PageResult<Question> getSubscriptionQuestion() {
        return null;
    }
}
