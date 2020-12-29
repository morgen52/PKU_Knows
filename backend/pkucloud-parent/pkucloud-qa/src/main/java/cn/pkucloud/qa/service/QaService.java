package cn.pkucloud.qa.service;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Answer;
import cn.pkucloud.qa.entity.Comment;
import cn.pkucloud.qa.entity.Question;
import cn.pkucloud.qa.entity.Report;

public interface QaService {
    PageResult<Question> getQuestionByPage(String issuer, String uid, String role, String mod, int size, int page);

    Result<Question> getQuestionById(String issuer, String uid, String role, String mod, String id);

    Result<?> postQuestion(String issuer, String uid, String role, String mod, String title, String txt, String[] img, String[] topic, String[] tag, int setting, boolean subscribe);

    PageResult<Answer> getAnswerByQid(String issuer, String uid, String role, String mod, String qid, int size, int page);

    Result<?> postAnswer(String issuer, String uid, String role, String mod, String qid, String txt, String[] img, int setting, boolean subscribe);

    Result<Answer> getAnswerById(String issuer, String uid, String role, String mod, String id);

    Result<?> postComment(String issuer, String uid, String role, String mod, String aid, String pid, String txt, String[] img, int setting);

    Result<Comment> getCommentById(String issuer, String uid, String role, String mod, String id);

    PageResult<Report> getReportByPage(String issuer, String uid, String role, String mod, int size, int page);

    Result<Report> getReportById(String issuer, String uid, String role, String mod, String id);

    PageResult<Comment> getCommentByAid(String issuer, String uid, String role, String mod, String aid, int size, int page);

    Result<?> postReport(String issuer, String uid, String role, String mod, String type, String id, String txt);

    Result<?> putQuestionLike(String issuer, String uid, String role, String mod, String id, int like);

    PageResult<?> getFavoriteByPage(String issuer, String uid, String role, String mod, String type, int size, int page);

    Result<?> postFavorite(String issuer, String uid, String role, String mod, String type, String id);

    Result<?> deleteFavoriteById(String issuer, String uid, String role, String mod, String id);

    PageResult<Question> getSubscriptionQuestionByPage(String issuer, String uid, String role, String mod, int size, int page);

    Result<?> postSubscription(String issuer, String uid, String role, String mod, String qid);

    Result<?> deleteSubscriptionById(String issuer, String uid, String role, String mod, String id);
}
