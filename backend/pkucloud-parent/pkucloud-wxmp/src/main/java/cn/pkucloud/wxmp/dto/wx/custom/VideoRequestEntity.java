package cn.pkucloud.wxmp.dto.wx.custom;

import cn.pkucloud.wxmp.dto.wx.common.Video;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VideoRequestEntity extends BaseRequestEntity {
    private Video video;

    public VideoRequestEntity(String touser, Video video) {
        super(touser, "video");
        this.video = video;
    }
}
