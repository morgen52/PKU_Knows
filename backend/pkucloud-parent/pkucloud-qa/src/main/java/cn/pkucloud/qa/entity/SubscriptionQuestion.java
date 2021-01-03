package cn.pkucloud.qa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubscriptionQuestion extends Question {
    private String sid;

    public SubscriptionQuestion(String sid, Question question) {
        super(
                question.get_id(),
                question.getStatus(),
                question.getUid(),
                question.getUser(),
                question.getTitle(),
                question.getTxt(),
                question.getImg(),
                question.getTopic(),
                question.getTag(),
                question.getLike(),
                question.getDislike(),
                question.getAnswer(),
                question.getFavorite(),
                question.getSubscribe(),
                question.getReport(),
                question.getCreateTime());
        this.sid = sid;
    }
}
