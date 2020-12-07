package cn.pkucloud.wxmp.dto.wx.template;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Keyword {
    private String value;
    private String color;
}
