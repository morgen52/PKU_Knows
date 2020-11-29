package cn.pkucloud.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@TableName("tb_auth")
public class Auth {
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private int status;

    private int risk;

    private int createTs;

    private int accessTs;

    private String userName;

    @JsonIgnore
    private String password;

    private String pkuId;

    private String phone;

    private String wxUnionId;

    private String wxaOpenId;

    private String mpOpenId;
}
