package cn.pkucloud.qa.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Report {
    @Id
    private String _id;
    private int status;
    private long uid;
    private int type;
    private String id;
    private String txt;
    private int create;
}
