package cn.pkucloud.auth.sms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsCode {
    private String pkuId;
    private String code;
    private String ip;
    private String ua;
}
