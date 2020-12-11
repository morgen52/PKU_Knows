package cn.pkucloud.wxmp.dto.wx.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MsgMenu {
    private String head_content;
    private MenuItem[] list;
    private String tail_content;
}
