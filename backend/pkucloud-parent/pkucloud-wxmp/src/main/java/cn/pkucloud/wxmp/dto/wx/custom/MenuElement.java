package cn.pkucloud.wxmp.dto.wx.custom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuElement {
    private String id;
    private String content;
}
