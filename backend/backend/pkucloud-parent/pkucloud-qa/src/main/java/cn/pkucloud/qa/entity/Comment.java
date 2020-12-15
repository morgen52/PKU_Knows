package cn.pkucloud.qa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Comment {
    @Id
    private String _id;
    private int status;
    @JsonIgnore
    private long uid;
    private User user;
    private String aid;
    private String pid;
    private String txt;
    private String[] img;
    private int like;
    private int dislike;
    private int create;
}
