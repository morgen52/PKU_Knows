package cn.pkucloud.qa.repository;

import cn.pkucloud.qa.entity.Question;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestionRepository extends PagingAndSortingRepository<Question, String> {
}
