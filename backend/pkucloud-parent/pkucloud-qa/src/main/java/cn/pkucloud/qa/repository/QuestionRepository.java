package cn.pkucloud.qa.repository;

import cn.pkucloud.qa.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestionRepository extends PagingAndSortingRepository<Question, String> {
    Page<Question> findByTopic(String topic, Pageable pageable);

    @Query("{'tag': { '$all': ?0 } }")
    Page<Question> findByTag(String[] tag, Pageable pageable);

    Page<Question> findByTitleRegexOrTxtRegex(String regex1, String regex2, Pageable pageable);
}
