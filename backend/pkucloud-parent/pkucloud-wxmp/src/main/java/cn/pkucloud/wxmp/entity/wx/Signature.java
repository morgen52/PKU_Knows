package cn.pkucloud.wxmp.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Signature {
    private String nonceStr;
    private String timestamp;
    private String signature;
}
