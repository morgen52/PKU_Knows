package cn.pkucloud.wxmp.dto.wx.custom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomMessage {
    private String touser;
    private String msgtype;
    private Text text;
    private Image image;
    private Voice voice;
    private Video video;
    private Music music;
    private News news;
    private MpNews mpnews;
    private MsgMenu msgmenu;
    private WxCard wxcard;
    private MiniProgramPage miniprogrampage;
}
