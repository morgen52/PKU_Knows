package cn.pkucloud.wxmp.dto.wx.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "xml")
public class XmlEntity {
    @JacksonXmlProperty(localName = "ToUserName")
    private String toUserName;

    @JacksonXmlProperty(localName = "FromUserName")
    private String fromUserName;

    @JacksonXmlProperty(localName = "CreateTime")
    private int createTime;

    @JacksonXmlProperty(localName = "MsgType")
    private String msgType;

    @JacksonXmlProperty(localName = "Content")
    private String content;

    @JacksonXmlProperty(localName = "PicUrl")
    private String picUrl;

    @JacksonXmlProperty(localName = "MediaId")
    private String mediaId;

    @JacksonXmlProperty(localName = "ThumbMediaId")
    private String thumbMediaId;

    @JacksonXmlProperty(localName = "Format")
    private String format;

    @JacksonXmlProperty(localName = "Recognition")
    private String recognition;

    @JacksonXmlProperty(localName = "Location_X")
    private double location_X;

    @JacksonXmlProperty(localName = "Location_Y")
    private double location_Y;

    @JacksonXmlProperty(localName = "Scale")
    private int scale;

    @JacksonXmlProperty(localName = "Label")
    private String label;

    @JacksonXmlProperty(localName = "Title")
    private String title;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @JacksonXmlProperty(localName = "Url")
    private String url;

    @JacksonXmlProperty(localName = "Event")
    private String event;

    @JacksonXmlProperty(localName = "EventKey")
    private String eventKey;

    @JacksonXmlProperty(localName = "Ticket")
    private String ticket;

    @JacksonXmlProperty(localName = "Latitude")
    private double latitude;

    @JacksonXmlProperty(localName = "Longitude")
    private double longitude;

    @JacksonXmlProperty(localName = "Precision")
    private double precision;

    @JacksonXmlProperty(localName = "MsgId")
    private long msgId;
}
