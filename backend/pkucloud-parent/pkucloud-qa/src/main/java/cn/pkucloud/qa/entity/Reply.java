package cn.pkucloud.qa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_reply")
public class Reply {
    private String id;
    private int status;
    private int createTs;
}
