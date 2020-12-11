package cn.pkucloud.qa.service;

import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Question;

public interface QaService {
    Result<Question> getQuestionById(String id);
}
