package cn.pkucloud.qa.repository;

import cn.pkucloud.qa.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, String> {
    Page<Subscription> findByUid(String uid, Pageable pageable);
}
