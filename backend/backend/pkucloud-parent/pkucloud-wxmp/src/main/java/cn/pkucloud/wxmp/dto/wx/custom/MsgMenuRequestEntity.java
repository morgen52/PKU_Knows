package cn.pkucloud.wxmp.dto.wx.custom;

import cn.pkucloud.wxmp.dto.wx.common.MsgMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MsgMenuRequestEntity extends BaseRequestEntity {
    private MsgMenu msgmenu;

    public MsgMenuRequestEntity(String touser, MsgMenu msgmenu) {
        super(touser, "msgmenu");
        this.msgmenu = msgmenu;
    }
}
