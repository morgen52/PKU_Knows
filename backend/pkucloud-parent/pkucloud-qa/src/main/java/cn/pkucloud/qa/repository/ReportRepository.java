package cn.pkucloud.qa.repository;

import cn.pkucloud.qa.entity.Report;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReportRepository extends PagingAndSortingRepository<Report, String> {
}
