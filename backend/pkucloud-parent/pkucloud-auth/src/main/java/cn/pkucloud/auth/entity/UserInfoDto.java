package cn.pkucloud.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private String userName;
    private String motto;
    private String avatar;
    private String gender;
    private String usrT;
    private String stuT;
    private String enroll;
    private String dept;
    private String major;
    private String name;
}
