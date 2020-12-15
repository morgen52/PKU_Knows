package cn.pkucloud.wxmp.dto.wx.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MiniProgramPage {
    private String title;
    private String appid;
    private String pagepath;
    private String thumb_media_id;
}
