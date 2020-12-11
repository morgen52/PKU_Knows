package cn.pkucloud.qa.dao;

import cn.pkucloud.qa.entity.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportDao extends MongoRepository<Report, String> {
}
