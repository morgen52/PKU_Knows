package cn.pkucloud.wxmp.dto.wx;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WxmpUserInfoDto extends BaseResult {
    private int subscribe;
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private int subscribe_time;
    private String unionid;
    private String remark;
    private int groupid;
    private int[] tagid_list;
    private String subscribe_scene;
    private int qr_scene;
    private String qr_scene_str;
}
