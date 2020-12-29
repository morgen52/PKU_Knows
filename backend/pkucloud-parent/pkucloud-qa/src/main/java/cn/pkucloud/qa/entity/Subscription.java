package cn.pkucloud.qa.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("subscription")
public class Subscription {
    @Id
    private String _id;
    private String id;
    private String uid;
    private int createTime;
}
