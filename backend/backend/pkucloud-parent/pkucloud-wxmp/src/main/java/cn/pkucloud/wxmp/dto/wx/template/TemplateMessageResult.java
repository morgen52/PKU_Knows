package cn.pkucloud.wxmp.dto.wx.template;

import cn.pkucloud.wxmp.dto.wx.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateMessageResult extends BaseResult {
    private long msgid;
}
