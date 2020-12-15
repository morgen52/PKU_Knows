package cn.pkucloud.auth.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class WxaUserInfoDto {
    private String nickName;
    private int gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String openId;
    private String unionId;
}
