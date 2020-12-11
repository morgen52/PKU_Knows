package cn.pkucloud.qa.dao;

import cn.pkucloud.qa.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionDao extends MongoRepository<Question, String> {
}
