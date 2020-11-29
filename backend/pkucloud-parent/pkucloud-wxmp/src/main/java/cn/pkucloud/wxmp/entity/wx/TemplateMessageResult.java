package cn.pkucloud.wxmp.entity.wx;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateMessageResult extends BaseResult {
    private int msgid;
}
