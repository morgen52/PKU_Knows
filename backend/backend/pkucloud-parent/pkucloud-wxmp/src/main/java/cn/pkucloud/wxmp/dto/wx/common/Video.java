package cn.pkucloud.wxmp.dto.wx.common;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Video")
public class Video {
    @JacksonXmlProperty(localName = "MediaId")
    private String media_id;

    @JacksonXmlProperty(localName = "ThumbMediaId")
    private String thumb_media_id;

    @JacksonXmlProperty(localName = "Title")
    private String title;

    @JacksonXmlProperty(localName = "Description")
    private String description;
}
