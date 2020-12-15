package cn.pkucloud.wxmp.dto.wx.custom;

import cn.pkucloud.wxmp.dto.wx.common.Text;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TextRequestEntity extends BaseRequestEntity {
    private Text text;

    public TextRequestEntity(String touser, Text text) {
        super(touser, "text");
        this.text = text;
    }
}
