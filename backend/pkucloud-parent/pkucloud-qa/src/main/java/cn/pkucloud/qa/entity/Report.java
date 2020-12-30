package cn.pkucloud.qa.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("report")
public class Report {
    @Id
    private String _id;
    private int status;
    private String uid;
    private String type;
    private String id;
    private String txt;
    private int createTime;
}
