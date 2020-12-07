package cn.pkucloud.wxmp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auth {
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
