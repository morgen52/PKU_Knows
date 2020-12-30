package cn.pkucloud.auth.dto;

import lombok.Data;

@Data
public class WxaLoginDto {
    private String scene;
    private PkuUserInfoDto pkuUserInfo;
    private WxaUserInfoDto wxaUserInfo;
}
