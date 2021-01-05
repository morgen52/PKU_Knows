package cn.pkucloud.qa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("question")
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    private String _id;
    private Integer status;
    @JsonIgnore
    private String uid;
    private User user;
    @TextIndexed
    private String title;
    @TextIndexed
    private String txt;
    private String[] img;
    private String topic;
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
