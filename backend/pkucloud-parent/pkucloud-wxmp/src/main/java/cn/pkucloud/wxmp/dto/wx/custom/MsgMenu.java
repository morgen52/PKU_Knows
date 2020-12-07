package cn.pkucloud.wxmp.dto.wx.custom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MsgMenu {
    private String head_content;
    private MenuElement[] list;
    private String tail_content;
}
