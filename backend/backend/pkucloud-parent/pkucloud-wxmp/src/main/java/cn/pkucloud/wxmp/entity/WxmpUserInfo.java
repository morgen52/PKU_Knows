package cn.pkucloud.wxmp.entity;

import cn.pkucloud.wxmp.dto.wx.WxmpUserInfoDto;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_wxmp_user_info")
public class WxmpUserInfo {
    private int subscribe;
    @TableId
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
    private String subscribe_scene;
    private int update;

    public WxmpUserInfo(WxmpUserInfoDto wxmpUserInfoDto) {
        this.subscribe = wxmpUserInfoDto.getSubscribe();
        this.openid = wxmpUserInfoDto.getOpenid();
        this.nickname = wxmpUserInfoDto.getNickname();
        this.sex = wxmpUserInfoDto.getSex();
        this.language = wxmpUserInfoDto.getLanguage();
        this.city = wxmpUserInfoDto.getCity();
        this.province = wxmpUserInfoDto.getProvince();
        this.country = wxmpUserInfoDto.getCountry();
        this.headimgurl = wxmpUserInfoDto.getHeadimgurl();
        this.subscribe_time = wxmpUserInfoDto.getSubscribe_time();
        this.unionid = wxmpUserInfoDto.getUnionid();
        this.subscribe_scene = wxmpUserInfoDto.getSubscribe_scene();
        this.update = (int) (System.currentTimeMillis() / 1000);
    }
}
