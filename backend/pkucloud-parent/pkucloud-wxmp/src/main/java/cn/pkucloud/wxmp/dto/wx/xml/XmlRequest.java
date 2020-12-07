package cn.pkucloud.wxmp.dto.wx.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class XmlRequest {
    @JacksonXmlProperty(localName = "ToUserName")
    private String toUserName;

    @JacksonXmlProperty(localName = "Encrypt")
    private String encrypt;
}
