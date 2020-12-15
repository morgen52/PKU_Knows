package cn.pkucloud.wxmp.dto.wx.custom;

import cn.pkucloud.wxmp.dto.wx.common.MiniProgramPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MiniProgramPageRequestEntity extends BaseRequestEntity {
    private MiniProgramPage miniprogrampage;

    public MiniProgramPageRequestEntity(String touser, MiniProgramPage miniprogrampage) {
        super(touser, "miniprogrampage");
        this.miniprogrampage = miniprogrampage;
    }
}
