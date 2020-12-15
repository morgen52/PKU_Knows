package cn.pkucloud.qa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Question {
    @Id
    private String _id;
    private int status;
    @JsonIgnore
    private long uid;
    private User user;
    private String title;
    private String txt;
    private String[] img;
    private int like;
    private int dislike;
    private int answer;
    private int favorite;
    private int create;
}
