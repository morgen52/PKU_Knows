package cn.pkucloud.wxmp.dto.wx.common;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Music")
public class Music {
    @JacksonXmlProperty(localName = "Title")
    private String title;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @JacksonXmlProperty(localName = "MusicUrl")
    private String musicurl;

    @JacksonXmlProperty(localName = "HQMusicUrl")
    private String hqmusicurl;

    @JacksonXmlProperty(localName = "ThumbMediaId")
    private String thumb_media_id;
}
