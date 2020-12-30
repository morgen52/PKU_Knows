package cn.pkucloud.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Wxh5Signature {
    private String nonceStr;
    private int timestamp;
    private String signature;
}
