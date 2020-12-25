package cn.pkucloud.qa.service.impl;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Answer;
import cn.pkucloud.qa.entity.Comment;
import cn.pkucloud.qa.entity.Question;
import cn.pkucloud.qa.entity.Report;
import cn.pkucloud.qa.repository.AnswerRepository;
import cn.pkucloud.qa.repository.CommentRepository;
import cn.pkucloud.qa.repository.QuestionRepository;
import cn.pkucloud.qa.repository.ReportRepository;
import cn.pkucloud.qa.service.QaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static cn.pkucloud.common.ResultCode.BAD_REQUEST;
import static cn.pkucloud.common.ResultCode.NOT_FOUND;

@Service
public class QaServiceImpl implements QaService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public PageResult<Question> getQuestionByPage(String issuer, String uid, String role, String mod, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Question> questionPage = questionRepository.findAll(pageRequest);
        List<Question> questions = questionPage.getContent();
        if (questions.isEmpty()) {
            return new PageResult<>(NOT_FOUND, "not found");
        }
        return new PageResult<>(questions);
    }

    @Override
    public Result<Question> getQuestionById(String issuer, String uid, String role, String mod, String id) {
        Optional<Question> optional = questionRepository.findById(id);
        if (!optional.isPresent()) {
            return new Result<>(NOT_FOUND, "not found");
        }
        Question question = optional.get();
        return new Result<>(question);
    }

    @Override
    public Result<?> postQuestion(String issuer, String uid, String role, String mod, String title, String txt, String[] img, String[] tag, int setting, boolean subscribe) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        Question question = Question.builder()
                .status(0)
                .uid(uid)
                .title(title)
                .txt(txt)
                .img(img)
                .tag(tag)
                .like(0)
                .dislike(0)
                .answer(0)
                .favorite(0)
                .subscribe(0)
                .createTime(timestamp)
                .build();
        questionRepository.save(question);
        return new Result<>();
    }

    @Override
    public PageResult<Answer> getAnswerByQid(String issuer, String uid, String role, String mod, String qid, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Answer> answerPage = answerRepository.findByQid(qid, pageRequest);
        List<Answer> answers = answerPage.getContent();
        if (answers.isEmpty()) {
            return new PageResult<>(NOT_FOUND, "not found");
        }
        return new PageResult<>(answers);
    }

    @Override
    public Result<?> postAnswer(String issuer, String uid, String role, String mod, String qid, String txt, String[] img, int setting, boolean subscribe) {
        Query query = new Query(Criteria.where("_id").is(qid));
        Update update = new Update();
        update.inc("answer");
        Question question = mongoTemplate.findAndModify(query, update, Question.class, "question");
        if (null == question) {
            return new Result<>(NOT_FOUND, "not found");
        }
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        Answer answer = Answer.builder()
                .status(0)
                .uid(uid)
                .qid(qid)
                .txt(txt)
                .img(img)
                .like(0)
                .dislike(0)
                .comment(0)
                .favorite(0)
                .createTime(timestamp)
                .build();
        answerRepository.save(answer);
        return new Result<>();
    }

    @Override
    public Result<Answer> getAnswerById(String issuer, String uid, String role, String mod, String id) {
        Optional<Answer> optional = answerRepository.findById(id);
        if (!optional.isPresent()) {
            return new Result<>(NOT_FOUND, "not found");
        }
        Answer answer = optional.get();
        return new Result<>(answer);
    }

    @Override
    public Result<?> postComment(String issuer, String uid, String role, String mod, String aid, String pid, String txt, String[] img, int setting) {
        Query query = new Query(Criteria.where("_id").is(aid));
        Update update = new Update();
        update.inc("comment");
        Answer answer = mongoTemplate.findAndModify(query, update, Answer.class, "answer");
        if (null == answer) {
            return new Result<>(NOT_FOUND, "not found");
        }
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        Comment comment = Comment.builder()
                .status(0)
                .uid(uid)
                .aid(aid)
                .pid(pid)
                .txt(txt)
                .img(img)
                .like(0)
                .dislike(0)
                .createTime(timestamp)
                .build();
        commentRepository.save(comment);
        return new Result<>();
    }

    @Override
    public Result<Comment> getCommentById(String issuer, String uid, String role, String mod, String id) {
        Optional<Comment> optional = commentRepository.findById(id);
        if (!optional.isPresent()) {
            return new Result<>(NOT_FOUND, "not found");
        }
        Comment comment = optional.get();
        return new Result<>(comment);
    }

    @Override
    public PageResult<Report> getReportByPage(String issuer, String uid, String role, String mod, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Report> reportPage = reportRepository.findAll(pageRequest);
        List<Report> reports = reportPage.getContent();
        if (reports.isEmpty()) {
            return new PageResult<>(NOT_FOUND, "not found");
        }
        return new PageResult<>(reports);
    }

    @Override
    public Result<Report> getReportById(String issuer, String uid, String role, String mod, String id) {
        Optional<Report> optional = reportRepository.findById(id);
        if (!optional.isPresent()) {
            return new Result<>(NOT_FOUND, "not found");
        }
        Report report = optional.get();
        return new Result<>(report);
    }

    @Override
    public PageResult<Comment> getCommentByAid(String issuer, String uid, String role, String mod, String aid, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Comment> commentPage = commentRepository.findByAid(aid, pageRequest);
        List<Comment> comments = commentPage.getContent();
        if (comments.isEmpty()) {
            return new PageResult<>(NOT_FOUND, "not found");
        }
        return new PageResult<>(comments);
    }

    @Override
    public Result<?> postReport(String issuer, String uid, String role, String mod, String type, String id, String txt) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("report");
        Class<?> clazz = null;
        switch (type) {
            case "question":
                clazz = Question.class;
                break;
            case "answer":
                clazz = Answer.class;
                break;
            case "comment":
                clazz = Comment.class;
                break;
            default:
                return new Result<>(BAD_REQUEST, "bad request");
        }
        Object o = mongoTemplate.findAndModify(query, update, clazz, type);
        if (null == o) {
            return new Result<>(NOT_FOUND, "not found");
        }
        int timestamp = (int) System.currentTimeMillis();
        Report report = Report.builder()
                .status(0)
                .uid(uid)
                .type(type)
                .txt(txt)
                .createTime(timestamp)
                .build();
        reportRepository.save(report);
        return new Result<>();
    }

    @Override
    public Result<?> putQuestionLike(String issuer, String uid, String role, String mod, String id, int like) {
        String target = String.valueOf(like);
        String key = "q:" + id + ":" + uid;
        BoundValueOperations<String, String> boundValueOps = stringRedisTemplate.boundValueOps(key);
        String value = boundValueOps.get();
        if (target.equals(value) || (target.equals("0") && null == value)) {
            return new Result<>();
        }
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        switch (target) {
            case "0":
                if ("1".equals(value)) {
                    update.inc("like", -1);
                } else {
                    update.inc("dislike", -1);
                }
                stringRedisTemplate.delete(key);
                break;
            case "1":
                if ("2".equals(value)) {
                    update.inc("dislike", -1);
                }
                update.inc("like");
                boundValueOps.set("1");
                break;
            case "2":
                if ("1".equals(value)) {
                    update.inc("like", -1);
                }
                update.inc("dislike");
                boundValueOps.set("2");
                break;
            default:
                return new Result<>(BAD_REQUEST, "bad request");
        }
        mongoTemplate.findAndModify(query, update, Question.class, "question");
        return new Result<>();
    }

    @Override
    public PageResult<Question> getFavoriteQuestionByPage(String issuer, String uid, String role, String mod, int size, int page) {
        return null;
    }

    @Override
    public PageResult<Answer> getFavoriteAnswerByPage(String issuer, String uid, String role, String mod, int size, int page) {
        return null;
    }

    @Override
    public Result<?> postFavorite(String issuer, String uid, String role, String mod, String type, String id) {
        return null;
    }

    @Override
    public Result<?> deleteFavoriteById(String issuer, String uid, String role, String mod, String id) {
        return null;
    }

    @Override
    public PageResult<Question> getSubscriptionQuestionByPage(String issuer, String uid, String role, String mod, int size, int page) {
        return null;
    }

    @Override
    public Result<?> postSubscription(String issuer, String uid, String role, String mod, String qid) {
        return null;
    }

    @Override
    public Result<?> deleteSubscriptionById(String issuer, String uid, String role, String mod, String id) {
        return null;
    }
}
