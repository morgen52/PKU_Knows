package cn.pkucloud.qa.dao;

import cn.pkucloud.qa.entity.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerDao extends MongoRepository<Answer, String> {
}
