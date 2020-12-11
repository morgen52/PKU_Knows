package cn.pkucloud.qa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Answer {
    @Id
    private String _id;
    private int status;
    @JsonIgnore
    private long uid;
    private User user;
    private String qid;
    private String txt;
    private String[] img;
    private int like;
    private int dislike;
    private int comment;
    private int favorite;
    private int create;
}
