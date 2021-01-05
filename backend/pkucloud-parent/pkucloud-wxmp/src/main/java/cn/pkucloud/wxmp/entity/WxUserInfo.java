package cn.pkucloud.wxmp.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_wx_user_info")
public class WxUserInfo {
    private String nickName;
    private Integer gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String wxaOpenId;
    private String mpOpenId;
    private String webOpenId;
    @TableId
    private String unionId;
    private Integer register;
    private Integer access;
}
