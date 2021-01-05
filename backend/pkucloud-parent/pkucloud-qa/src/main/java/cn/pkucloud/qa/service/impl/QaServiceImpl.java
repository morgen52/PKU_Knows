package cn.pkucloud.qa.service.impl;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.*;
import cn.pkucloud.qa.feign.AuthClient;
import cn.pkucloud.qa.feign.WxmpClient;
import cn.pkucloud.qa.repository.*;
import cn.pkucloud.qa.service.QaService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static cn.pkucloud.common.ResultCode.*;

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

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private WxmpClient wxmpClient;

    @Override
    public PageResult<Question> getQuestionByPage(String issuer, String uid, String role, String mod, int size, int page) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "_id");
        Sort sort = Sort.by(order);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Question> questionPage = questionRepository.findAll(pageRequest);
        List<Question> questions = questionPage.getContent();
//        if (questions.isEmpty()) {
//            return new PageResult<>(NOT_FOUND, "not found");
//        }
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
    public Result<?> postQuestion(String issuer, String uid, String role, String mod, String title, String txt, String[] img, String topic, String tag, int setting, boolean subscribe) {
        String[] tagArray = tag.split(" ");
        User user = null;
        if (setting != 0) {
            Result<User> result = authClient.getUserInfo(uid);
            if (0 != result.getCode()) {
                return new Result<>(NOT_FOUND, "not found");
            }
            user = result.getData();
            user = getUser(user, setting);
        }

        int timestamp = (int) (System.currentTimeMillis() / 1000);
        Question question = Question.builder()
                .status(0)
                .uid(uid)
                .user(user)
                .title(title)
                .txt(txt)
                .img(img)
                .topic(topic)
                .tag(tagArray)
                .like(0)
                .dislike(0)
                .answer(0)
                .favorite(0)
                .subscribe(subscribe ? 1 : 0)
                .createTime(timestamp)
                .build();
        Question newQuestion = questionRepository.save(question);
        String qid = newQuestion.get_id();
        if (subscribe) {
            Subscription subscription = Subscription.builder()
                    .qid(qid)
                    .uid(uid)
                    .createTime(timestamp)
                    .build();
            subscriptionRepository.save(subscription);
        }
        return new Result<>(qid);
}

    @Override
    public PageResult<Answer> getAnswerByQid(String issuer, String uid, String role, String mod, String qid, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Answer> answerPage = answerRepository.findByQid(qid, pageRequest);
        List<Answer> answers = answerPage.getContent();
//        if (answers.isEmpty()) {
//            return new PageResult<>(NOT_FOUND, "not found");
//        }
        return new PageResult<>(answers);
    }

    @Override
    public Result<?> postAnswer(String issuer, String uid, String role, String mod, String qid, String txt, String[] img, int setting, boolean subscribe) {
        User user = null;
        if (setting != 0) {
            Result<User> result = authClient.getUserInfo(uid);
            if (0 != result.getCode()) {
                return new Result<>(NOT_FOUND, "not found");
            }
            user = result.getData();
            user = getUser(user, setting);
        }
        Query query = new Query(Criteria.where("_id").is(qid));
        Update update = new Update();
        update.inc("answer");
        Question question = mongoTemplate.findAndModify(query, update, Question.class, "question");
        if (null == question) {
            return new Result<>(NOT_FOUND, "not found");
        }
        long l = System.currentTimeMillis();
        int timestamp = (int) (l / 1000);
        Answer answer = Answer.builder()
                .status(0)
                .uid(uid)
                .user(user)
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
        List<Subscription> subscriptionList = subscriptionRepository.findByQid(qid);
        ArrayList<String> idList = new ArrayList<>();
        for (Subscription subscription : subscriptionList) {
            idList.add(subscription.getUid());
        }
        if (idList.size() > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            wxmpClient.sendAnswerMsg(idList, "你好，你关注的问题有了新回答。", question.getTitle(), "匿名用户", simpleDateFormat.format(new Date(l)), "点击查看详情。");
        }
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
        User user = null;
        if (setting != 0) {
            Result<User> result = authClient.getUserInfo(uid);
            if (0 != result.getCode()) {
                return new Result<>(NOT_FOUND, "not found");
            }
            user = result.getData();
            user = getUser(user, setting);
        }
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
                .user(user)
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
//        if (reports.isEmpty()) {
//            return new PageResult<>(NOT_FOUND, "not found");
//        }
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
//        if (comments.isEmpty()) {
//            return new PageResult<>(NOT_FOUND, "not found");
//        }
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
        String key = "q:" + uid + ":" + id;
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
    public PageResult<?> getFavoriteByPage(String issuer, String uid, String role, String mod, String type, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Favorite> favoritePage = favoriteRepository.findByUidAndType(uid, type, pageRequest);
        List<String> fids = new ArrayList<>();
        List<String> oids = new ArrayList<>();
        for (Favorite favorite : favoritePage) {
            fids.add(favorite.get_id());
            oids.add(favorite.getOid());
        }
        switch (type) {
            case "question":
                Iterable<Question> questionIterable = questionRepository.findAllById(oids);
//                List<Question> questions = new ArrayList<>();
                List<Question> questionList = IterableUtils.toList(questionIterable);
                List<FavoriteQuestion> favoriteQuestions = new ArrayList<>();
                for (int i = 0; i < questionList.size(); ++i) {
                    favoriteQuestions.add(new FavoriteQuestion(fids.get(i), questionList.get(i)));
                }
//                for (Question question : questionIterable) {
//                    questions.add(question);
//                }
                return new PageResult<>(favoriteQuestions);
            case "answer":
                Iterable<Answer> answerIterable = answerRepository.findAllById(oids);
                List<Answer> answerList = IterableUtils.toList(answerIterable);
                ArrayList<FavoriteAnswer> favoriteAnswers = new ArrayList<>();
//                List<Answer> answers = new ArrayList<>();
                for (int i = 0; i < answerList.size(); ++i) {
                    favoriteAnswers.add(new FavoriteAnswer(fids.get(i), answerList.get(i)));
                }
//                for (Answer answer : answerIterable) {
//                    answers.add(answer);
//                }
                return new PageResult<>(favoriteAnswers);
            default:
                return new PageResult<>(BAD_REQUEST, "bad request");
        }
    }

    @Override
    public Result<?> postFavorite(String issuer, String uid, String role, String mod, String type, String oid) {
        Favorite oldFavorite = favoriteRepository.findByUidAndTypeAndOid(uid, type, oid);
        if (null != oldFavorite) {
            return new Result<>();
        }

        Query query = new Query(Criteria.where("_id").is(oid));
        Update update = new Update();
        update.inc("favorite");

        switch (type) {
            case "question":
                Question question = mongoTemplate.findAndModify(query, update, Question.class, "question");
//                Optional<Question> questionOptional = questionRepository.findById(id);
                if (null == question) {
                    return new Result<>(NOT_FOUND, "not found");
                }
                break;
            case "answer":
                Answer answer = mongoTemplate.findAndModify(query, update, Answer.class, "answer");
//                Optional<Answer> answerOptional = answerRepository.findById(id);
                if (null == answer) {
                    return new Result<>(NOT_FOUND, "not found");
                }
                break;
            default:
                return new Result<>(BAD_REQUEST, "bad request");
        }
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        Favorite favorite = Favorite.builder()
                .type(type)
                .oid(oid)
                .uid(uid)
                .createTime(timestamp)
                .build();
        favoriteRepository.save(favorite);
        return new Result<>();
    }

    @Override
    public Result<?> deleteFavoriteById(String issuer, String uid, String role, String mod, String id) {
        Optional<Favorite> optional = favoriteRepository.findById(id);
        if (optional.isPresent()) {
            Favorite favorite = optional.get();
            if (favorite.getUid().equals(uid)) {
                String type = favorite.getType();
                String oid = favorite.getOid();
                favoriteRepository.deleteById(id);
                Query query = new Query(Criteria.where("_id").is(oid));
                Update update = new Update();
                update.inc("favorite", -1);

                switch (type) {
                    case "question":
                        Question question = mongoTemplate.findAndModify(query, update, Question.class, "question");
//                Optional<Question> questionOptional = questionRepository.findById(id);
                        if (null == question) {
                            return new Result<>(NOT_FOUND, "not found");
                        }
                        break;
                    case "answer":
                        Answer answer = mongoTemplate.findAndModify(query, update, Answer.class, "answer");
//                Optional<Answer> answerOptional = answerRepository.findById(id);
                        if (null == answer) {
                            return new Result<>(NOT_FOUND, "not found");
                        }
                        break;
                    default:
                        return new Result<>(BAD_REQUEST, "bad request");
                }
                return new Result<>();
            }
            return new Result<>(AUTHORIZATION_REQUIRED, "authorization required");
        }
        return new Result<>(NOT_FOUND, "not found");
    }

    @Override
    public PageResult<SubscriptionQuestion> getSubscriptionQuestionByPage(String issuer, String uid, String role, String mod, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Subscription> subscriptionPage = subscriptionRepository.findByUid(uid, pageRequest);
        List<String> sids = new ArrayList<>();
        List<String> qids = new ArrayList<>();
        for (Subscription subscription : subscriptionPage) {
            sids.add(subscription.get_id());
            qids.add(subscription.getQid());
        }
        Iterable<Question> questionIterable = questionRepository.findAllById(qids);
        List<Question> questionList = IterableUtils.toList(questionIterable);
        List<SubscriptionQuestion> subscriptionQuestions = new ArrayList<>();
//        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < questionList.size(); ++i) {
            subscriptionQuestions.add(new SubscriptionQuestion(sids.get(i), questionList.get(i)));
        }
//        for (Question question : questionIterable) {
//            questions.add(question);
//        }
        return new PageResult<>(subscriptionQuestions);
    }

    @Override
    public Result<?> postSubscription(String issuer, String uid, String role, String mod, String qid) {
        Subscription oldSubscription = subscriptionRepository.findByUidAndQid(uid, qid);
        if (null != oldSubscription) {
            return new Result<>();
        }
        Query query = new Query(Criteria.where("_id").is(qid));
        Update update = new Update();
        update.inc("subscribe");
        Question question = mongoTemplate.findAndModify(query, update, Question.class, "question");

//        Optional<Question> optional = questionRepository.findById(qid);
        if (null != question) {
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            Subscription subscription = Subscription.builder()
                    .qid(qid)
                    .uid(uid)
                    .createTime(timestamp)
                    .build();
            subscriptionRepository.save(subscription);
            return new Result<>();
        }
        return new Result<>(NOT_FOUND, "not found");
    }

    @Override
    public Result<?> deleteSubscriptionById(String issuer, String uid, String role, String mod, String id) {
        Optional<Subscription> optional = subscriptionRepository.findById(id);
        if (optional.isPresent()) {
            Subscription subscription = optional.get();
            if (subscription.getUid().equals(uid)) {
                String qid = subscription.getQid();
                subscriptionRepository.deleteById(id);
                Query query = new Query(Criteria.where("_id").is(qid));
                Update update = new Update();
                update.inc("subscribe", -1);
                Question question = mongoTemplate.findAndModify(query, update, Question.class, "question");
                if (null == question) {
                    return new Result<>(NOT_FOUND, "not found");
                }
                return new Result<>();
            }
            return new Result<>(AUTHORIZATION_REQUIRED, "authorization required");
        }
        return new Result<>(NOT_FOUND, "not found");
    }

    @Override
    public Result<?> putAnswerLike(String issuer, String uid, String role, String mod, String id, int like) {
        String target = String.valueOf(like);
        String key = "a:" + uid + ":" + id;
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
        mongoTemplate.findAndModify(query, update, Answer.class, "answer");
        return new Result<>();
    }

    @Override
    public Result<Map<String, String>> getQuestionLike(String uid) {
        Set<String> keys = stringRedisTemplate.keys("q:" + uid + ":*");
        if (null != keys) {
            List<String> keyList = new ArrayList<>(keys);
            List<String> valueList = stringRedisTemplate.opsForValue().multiGet(keys);
            Map<String, String> map = new HashMap<>();
            int size = keyList.size();
            for (int i = 0; i < size; ++i) {
                String key = keyList.get(i);
                String qid = key.split(":")[2];
                map.put(qid, valueList.get(i));
            }
            return new Result<>(map);
        }
        return new Result<>(NOT_FOUND, "not found");
    }

    @Override
    public Result<Map<String, String>> getAnswerLike(String uid) {
        Set<String> keys = stringRedisTemplate.keys("a:" + uid + ":*");
        if (null != keys) {
            List<String> keyList = new ArrayList<>(keys);
            List<String> valueList = stringRedisTemplate.opsForValue().multiGet(keys);
            Map<String, String> map = new HashMap<>();
            int size = keyList.size();
            for (int i = 0; i < size; ++i) {
                String key = keyList.get(i);
                String aid = key.split(":")[2];
                map.put(aid, valueList.get(i));
            }
            return new Result<>(map);
        }
        return new Result<>(NOT_FOUND, "not found");
    }

    @Override
    public Result<List<String>> getFavoriteIds(String uid, String type) {
        List<Favorite> favoriteList = favoriteRepository.findByUidAndType(uid, type);
        List<String> ids = new ArrayList<>();
        for (Favorite favorite : favoriteList) {
            ids.add(favorite.getOid());
        }
        return new Result<>(ids);
    }

    @Override
    public Result<List<String>> getSubscriptionIds(String uid) {
        List<Subscription> subscriptionList = subscriptionRepository.findByUid(uid);
        List<String> ids = new ArrayList<>();
        for (Subscription subscription : subscriptionList) {
            ids.add(subscription.getQid());
        }
        return new Result<>(ids);
    }

    @Override
    public Result<?> deleteFavorite(String issuer, String uid, String role, String mod, String type, String oid) {
        Favorite favorite = favoriteRepository.findByUidAndTypeAndOid(uid, type, oid);
        if (null != favorite) {
            if (favorite.getUid().equals(uid)) {
                String id = favorite.get_id();
                favoriteRepository.deleteById(id);
                Query query = new Query(Criteria.where("_id").is(oid));
                Update update = new Update();
                update.inc("favorite", -1);

                switch (type) {
                    case "question":
                        Question question = mongoTemplate.findAndModify(query, update, Question.class, "question");
//                Optional<Question> questionOptional = questionRepository.findById(id);
                        if (null == question) {
                            return new Result<>(NOT_FOUND, "not found");
                        }
                        break;
                    case "answer":
                        Answer answer = mongoTemplate.findAndModify(query, update, Answer.class, "answer");
//                Optional<Answer> answerOptional = answerRepository.findById(id);
                        if (null == answer) {
                            return new Result<>(NOT_FOUND, "not found");
                        }
                        break;
                    default:
                        return new Result<>(BAD_REQUEST, "bad request");
                }
                return new Result<>();
            }
            return new Result<>(AUTHORIZATION_REQUIRED, "authorization required");
        }
        return new Result<>(NOT_FOUND, "not found");
    }

    @Override
    public Result<?> deleteSubscription(String issuer, String uid, String role, String mod, String qid) {
        Subscription subscription = subscriptionRepository.findByUidAndQid(uid, qid);
        if (null != subscription) {
            if (subscription.getUid().equals(uid)) {
                String id = subscription.get_id();
                subscriptionRepository.deleteById(id);
                Query query = new Query(Criteria.where("_id").is(qid));
                Update update = new Update();
                update.inc("subscribe", -1);
                Question question = mongoTemplate.findAndModify(query, update, Question.class, "question");
                if (null == question) {
                    return new Result<>(NOT_FOUND, "not found");
                }
                return new Result<>();
            }
            return new Result<>(AUTHORIZATION_REQUIRED, "authorization required");
        }
        return new Result<>(NOT_FOUND, "not found");
    }

    @Override
    public PageResult<Question> getQuestionByTopic(String issuer, String uid, String role, String mod, String topic, int size, int page) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "_id");
        Sort sort = Sort.by(order);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Question> questionPage = questionRepository.findByTopic(topic, pageRequest);
        List<Question> questionList = questionPage.getContent();
        return new PageResult<>(questionList);
    }

    @Override
    public PageResult<Question> getQuestionByTag(String issuer, String uid, String role, String mod, String[] tag, int size, int page) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "_id");
        Sort sort = Sort.by(order);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Question> questionPage = questionRepository.findByTag(tag, pageRequest);
        List<Question> questionList = questionPage.getContent();
        return new PageResult<>(questionList);
    }

    @Override
    public PageResult<Question> getQuestionByRegex(String issuer, String uid, String role, String mod, String regex, int size, int page) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "_id");
        Sort sort = Sort.by(order);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        System.out.println("keyword = " + regex);
        Page<Question> questionPage = questionRepository.findByTitleRegexOrTxtRegex(regex, regex, pageRequest);
        List<Question> questionList = questionPage.getContent();
        return new PageResult<>(questionList);
    }

    @Override
    public Result<?> putCommentLike(String issuer, String uid, String role, String mod, String id, int like) {
        return null;
    }

    private User getUser(User user, int setting) {
        User newUser = new User();
        if (1 == (setting & 1)) {
            newUser.setUserName(user.getUserName());
        }
        if (2 == (setting & 2)) {
            newUser.setMotto(user.getMotto());
        }
        if (4 == (setting & 4)) {
            newUser.setAvatar(user.getAvatar());
        }
        if (8 == (setting & 8)) {
            newUser.setGender(user.getGender());
        }
        if (16 == (setting & 16)) {
            newUser.setUsrT(user.getUsrT());
        }
        if (32 == (setting & 32)) {
            newUser.setStuT(user.getStuT());
        }
        if (64 == (setting & 64)) {
            newUser.setEnroll(user.getEnroll());
        }
        if (128 == (setting & 128)) {
            newUser.setDept(user.getDept());
        }
        if (256 == (setting & 256)) {
            newUser.setMajor(user.getMajor());
        }
        if (512 == (setting & 512)) {
            newUser.setName(user.getName());
        }
        return newUser;
    }
}
