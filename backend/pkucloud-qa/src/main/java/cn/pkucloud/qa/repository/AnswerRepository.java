package cn.pkucloud.qa.repository;

import cn.pkucloud.qa.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnswerRepository extends PagingAndSortingRepository<Answer, String> {
    Page<Answer> findByQid(String qid, Pageable pageable);
}
