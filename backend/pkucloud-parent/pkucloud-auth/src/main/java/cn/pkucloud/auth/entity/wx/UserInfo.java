package cn.pkucloud.auth.entity.wx;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfo extends BaseResult {
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String[] privilege;
    private String unionid;
}
