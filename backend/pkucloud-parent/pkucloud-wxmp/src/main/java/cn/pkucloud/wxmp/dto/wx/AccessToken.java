package cn.pkucloud.wxmp.dto.wx;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccessToken extends BaseResult {
    private String access_token;
    private int expires_in;
}
