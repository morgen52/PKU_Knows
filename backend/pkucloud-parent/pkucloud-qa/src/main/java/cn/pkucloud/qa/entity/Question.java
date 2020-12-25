package cn.pkucloud.qa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("question")
public class Question {
    @Id
    private String _id;
    private Integer status;
    @JsonIgnore
    private String uid;
    private User user;
    private String title;
    private String txt;
    private String[] img;
    private String[] tag;
    private Integer like;
    private Integer dislike;
    private Integer answer;
    private Integer favorite;
    private Integer subscribe;
    @JsonIgnore
    private Integer report;
    private Integer createTime;
}
