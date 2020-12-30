package cn.pkucloud.wxmp.dto.wx.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TextResponseEntity extends BaseResponseEntity {
    @JacksonXmlProperty(localName = "Content")
    private String content;

    public TextResponseEntity(String toUserName, String content) {
        super(toUserName, "text");
        this.content = content;
    }
}
