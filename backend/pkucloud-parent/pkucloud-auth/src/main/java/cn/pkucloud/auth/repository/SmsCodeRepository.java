package cn.pkucloud.auth.repository;

import cn.pkucloud.auth.entity.SmsCode;
import org.springframework.data.repository.CrudRepository;

public interface SmsCodeRepository extends CrudRepository<SmsCode, String> {
}
