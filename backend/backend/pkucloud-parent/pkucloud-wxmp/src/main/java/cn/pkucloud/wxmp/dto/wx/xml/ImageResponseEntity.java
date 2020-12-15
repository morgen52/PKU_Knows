package cn.pkucloud.wxmp.dto.wx.xml;

import cn.pkucloud.wxmp.dto.wx.common.Image;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImageResponseEntity extends BaseResponseEntity {
    @JacksonXmlProperty(localName = "Image")
    private Image image;

    public ImageResponseEntity(String toUserName, Image image) {
        super(toUserName, "image");
        this.image = image;
    }
}
