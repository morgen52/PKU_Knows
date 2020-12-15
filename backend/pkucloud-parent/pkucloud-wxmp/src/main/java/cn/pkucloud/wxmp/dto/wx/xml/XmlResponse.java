package cn.pkucloud.wxmp.dto.wx.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JacksonXmlRootElement(localName = "xml")
public class XmlResponse {
    @JacksonXmlProperty(localName = "Encrypt")
    private String encryptedStr;

    @JacksonXmlProperty(localName = "MsgSignature")
    private String msgSignature;

    @JacksonXmlProperty(localName = "TimeStamp")
    private int timestamp;

    @JacksonXmlProperty(localName = "Nonce")
    private String nonce;
}
