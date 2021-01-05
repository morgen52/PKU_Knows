package cn.pkucloud.qa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("answer")
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    private String _id;
    private Integer status;
    @JsonIgnore
    private String uid;
    private String qid;
    private User user;
    private String txt;
    private String[] img;
    private Integer like;
    private Integer dislike;
    private Integer comment;
    private Integer favorite;
    @JsonIgnore
    private Integer report;
    private Integer createTime;
}
