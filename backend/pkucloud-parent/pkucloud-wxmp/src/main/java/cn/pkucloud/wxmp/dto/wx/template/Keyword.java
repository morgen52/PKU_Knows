package cn.pkucloud.wxmp.dto.wx.template;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Keyword {
    private String value;
    private String color;

    public Keyword(String value) {
        this.value = value;
    }
}
