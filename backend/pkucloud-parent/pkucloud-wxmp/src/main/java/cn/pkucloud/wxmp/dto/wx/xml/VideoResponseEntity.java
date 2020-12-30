package cn.pkucloud.wxmp.dto.wx.xml;

import cn.pkucloud.wxmp.dto.wx.common.Video;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VideoResponseEntity extends BaseResponseEntity {
    @JacksonXmlProperty(localName = "Video")
    private Video video;

    public VideoResponseEntity(String toUserName, Video video) {
        super(toUserName, "video");
        this.video = video;
    }
}
