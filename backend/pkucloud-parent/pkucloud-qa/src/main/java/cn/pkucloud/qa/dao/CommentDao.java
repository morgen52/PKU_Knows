package cn.pkucloud.qa.dao;

import cn.pkucloud.qa.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentDao extends MongoRepository<Comment, String> {
}
