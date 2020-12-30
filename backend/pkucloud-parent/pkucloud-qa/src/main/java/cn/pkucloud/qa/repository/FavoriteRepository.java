package cn.pkucloud.qa.repository;

import cn.pkucloud.qa.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FavoriteRepository extends PagingAndSortingRepository<Favorite, String> {
    Page<Favorite> findByUidAndType(String uid, String type, Pageable pageable);
}
