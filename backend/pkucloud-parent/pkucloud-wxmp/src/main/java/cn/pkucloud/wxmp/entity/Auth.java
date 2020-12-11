package cn.pkucloud.wxmp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth {
    private Long id;

    private int status;

    private int risk;

    private int createTime;

    private int accessTime;

    private String userName;

    private String pkuId;

    private String phone;

    private String wxUnionId;
}
