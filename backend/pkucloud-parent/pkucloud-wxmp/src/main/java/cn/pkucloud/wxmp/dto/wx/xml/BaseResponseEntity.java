package cn.pkucloud.wxmp.dto.wx.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JacksonXmlRootElement(localName = "xml")
public class BaseResponseEntity {
    @JacksonXmlProperty(localName = "ToUserName")
    private String toUserName;

    @JacksonXmlProperty(localName = "FromUserName")
    private String fromUserName;

    @JacksonXmlProperty(localName = "CreateTime")
    private int createTime;

    @JacksonXmlProperty(localName = "MsgType")
    private String msgType;

    public BaseResponseEntity(String toUserName, String msgType) {
        this.toUserName = toUserName;
        this.fromUserName = "gh_f07f86698721";
        this.createTime = (int) (System.currentTimeMillis() / 1000);
        this.msgType = msgType;
    }
}
