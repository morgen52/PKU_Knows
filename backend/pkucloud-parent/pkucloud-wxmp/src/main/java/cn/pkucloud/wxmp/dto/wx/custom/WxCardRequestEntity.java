package cn.pkucloud.wxmp.dto.wx.custom;

import cn.pkucloud.wxmp.dto.wx.common.WxCard;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WxCardRequestEntity extends BaseRequestEntity {
    private WxCard wxcard;

    public WxCardRequestEntity(String touser, WxCard wxcard) {
        super(touser, "wxcard");
        this.wxcard = wxcard;
    }
}
