package cn.pkucloud.wxmp.dto.wx.custom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Music {
    private String title;
    private String description;
    private String musicurl;
    private String hqmusicurl;
    private String thumb_media_id;
}
