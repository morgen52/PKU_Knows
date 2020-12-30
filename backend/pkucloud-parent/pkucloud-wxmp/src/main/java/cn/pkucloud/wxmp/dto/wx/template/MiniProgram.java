package cn.pkucloud.wxmp.dto.wx.template;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MiniProgram {
    private String appid;
    private String pagepath;

    public MiniProgram(String appid) {
        this.appid = appid;
    }
}
