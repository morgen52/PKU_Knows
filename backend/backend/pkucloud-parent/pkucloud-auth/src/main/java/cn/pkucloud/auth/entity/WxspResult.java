package cn.pkucloud.auth.entity;

import lombok.Data;

@Data
public class WxspResult {
    private boolean success;
    private String message;
    private String userid;
}
