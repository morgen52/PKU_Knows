package cn.pkucloud.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_auth")
public class Auth {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    private int status;

    private int risk;

    private int createTime;

    private int accessTime;

    private String userName;

    @JsonIgnore
    private String password;

    private String pkuId;

    private String phone;

    private String wxUnionId;
}
