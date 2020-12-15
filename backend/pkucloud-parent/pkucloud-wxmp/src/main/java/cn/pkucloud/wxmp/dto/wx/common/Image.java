package cn.pkucloud.wxmp.dto.wx.common;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Image")
public class Image {
    @JacksonXmlProperty(localName = "MediaId")
    private String media_id;
}
