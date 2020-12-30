package cn.pkucloud.auth.wxa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WxaScene {
    private int state;
    private String ip;
    private String ua;
}
