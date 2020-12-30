package cn.pkucloud.qa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("comment")
public class Comment {
    @Id
    private String _id;
    private Integer status;
    @JsonIgnore
    private String uid;
    private String aid;
    private String pid;
    private User user;
    private String txt;
    private String[] img;
    private Integer like;
    private Integer dislike;
    @JsonIgnore
    private Integer report;
    private Integer createTime;
}
