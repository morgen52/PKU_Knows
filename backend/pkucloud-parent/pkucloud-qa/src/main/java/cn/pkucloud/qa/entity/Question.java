package cn.pkucloud.qa.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_question")
public class Question {
    @TableId
    private String id;
    private int status;
    private String uid;
    private String title;
    private String desc;
    private String[] img;
    private int createTs;
}
