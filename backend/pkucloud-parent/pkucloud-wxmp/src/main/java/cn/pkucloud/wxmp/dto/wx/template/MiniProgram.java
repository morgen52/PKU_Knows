package cn.pkucloud.wxmp.dto.wx.template;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MiniProgram {
    private String appid;
    private String pagepath;
}
