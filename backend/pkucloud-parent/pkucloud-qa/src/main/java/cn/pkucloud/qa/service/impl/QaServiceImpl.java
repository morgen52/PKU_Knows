package cn.pkucloud.qa.service.impl;

import cn.pkucloud.common.Result;
import cn.pkucloud.qa.dao.AnswerDao;
import cn.pkucloud.qa.dao.CommentDao;
import cn.pkucloud.qa.dao.QuestionDao;
import cn.pkucloud.qa.dao.ReportDao;
import cn.pkucloud.qa.entity.Question;
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
        questionDao.findById(id)

        return null;
    }
}
