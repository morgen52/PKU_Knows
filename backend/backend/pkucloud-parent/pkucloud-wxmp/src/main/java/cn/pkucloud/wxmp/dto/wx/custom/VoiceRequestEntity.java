package cn.pkucloud.wxmp.dto.wx.custom;

import cn.pkucloud.wxmp.dto.wx.common.Voice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VoiceRequestEntity extends BaseRequestEntity {
    private Voice voice;

    public VoiceRequestEntity(String touser, Voice voice) {
        super(touser, "voice");
        this.voice = voice;
    }
}
