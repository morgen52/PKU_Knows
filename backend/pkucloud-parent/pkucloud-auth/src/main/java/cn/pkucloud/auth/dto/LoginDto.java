package cn.pkucloud.auth.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String userName;
    private String password;
    private String phone;
    private String smsCode;
}
