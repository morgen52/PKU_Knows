package cn.pkucloud.wxmp.dto.wx.xml;

import cn.pkucloud.wxmp.dto.wx.common.Music;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MusicResponseEntity extends BaseResponseEntity {
    @JacksonXmlProperty(localName = "Music")
    private Music music;

    public MusicResponseEntity(String toUserName, Music music) {
        super(toUserName, "music");
        this.music = music;
    }
}
