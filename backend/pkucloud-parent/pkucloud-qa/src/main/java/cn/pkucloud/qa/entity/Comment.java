package cn.pkucloud.qa.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_comment")
public class Comment {
    @TableId
    private String id;
    private int status;
    private String uid;
    private String aid;
    private String txt;
    private String[] img;
    private int createTs;
}
