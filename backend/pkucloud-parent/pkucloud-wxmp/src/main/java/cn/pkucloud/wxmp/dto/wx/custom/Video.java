package cn.pkucloud.wxmp.dto.wx.custom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Video {
    private String media_id;
    private String thumb_media_id;
    private String title;
    private String description;
}
