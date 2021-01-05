package cn.pkucloud.qa.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("favorite")
public class Favorite {
    @Id
    private String _id;
    private String type;
    private String oid;
    private String uid;
    private int createTime;
}
