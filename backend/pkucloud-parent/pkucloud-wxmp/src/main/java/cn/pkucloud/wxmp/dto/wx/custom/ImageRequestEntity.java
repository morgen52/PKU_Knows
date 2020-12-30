package cn.pkucloud.wxmp.dto.wx.custom;

import cn.pkucloud.wxmp.dto.wx.common.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImageRequestEntity extends BaseRequestEntity {
    private Image image;

    public ImageRequestEntity(String touser, Image image) {
        super(touser, "image");
        this.image = image;
    }
}
