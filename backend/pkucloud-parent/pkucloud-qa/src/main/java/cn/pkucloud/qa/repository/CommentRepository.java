package cn.pkucloud.qa.repository;

import cn.pkucloud.qa.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, String> {
    Page<Comment> findByAid(String aid, Pageable pageable);
}
