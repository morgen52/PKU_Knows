package cn.pkucloud.wxmp.dto.wx.custom;

import cn.pkucloud.wxmp.dto.wx.common.Music;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MusicRequestEntity extends BaseRequestEntity {
    private Music music;

    public MusicRequestEntity(String touser, Music music) {
        super(touser, "music");
        this.music = music;
    }
}
