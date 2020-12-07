package cn.pkucloud.wxmp.dto.wx.custom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MiniProgramPage {
    private String title;
    private String appid;
    private String pagepath;
    private String thumb_media_id;
}
