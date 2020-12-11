package cn.pkucloud.wxmp.dto.wx.xml;

import cn.pkucloud.wxmp.dto.wx.common.Voice;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VoiceResponseEntity extends BaseResponseEntity {
    @JacksonXmlProperty(localName = "Voice")
    private Voice voice;

    public VoiceResponseEntity(String toUserName, Voice voice) {
        super(toUserName, "voice");
        this.voice = voice;
    }
}
