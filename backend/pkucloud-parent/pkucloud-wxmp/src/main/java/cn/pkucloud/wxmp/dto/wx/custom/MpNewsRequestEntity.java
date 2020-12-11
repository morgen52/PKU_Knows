package cn.pkucloud.wxmp.dto.wx.custom;

import cn.pkucloud.wxmp.dto.wx.common.MpNews;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MpNewsRequestEntity extends BaseRequestEntity {
    private MpNews mpNews;

    public MpNewsRequestEntity(String touser, MpNews mpNews) {
        super(touser, "mpnews");
        this.mpNews = mpNews;
    }
}
