package cn.pkucloud.wxmp.dto.wx.template;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Keywords {
    private Keyword first;
    private Keyword keyword1;
    private Keyword keyword2;
    private Keyword keyword3;
    private Keyword keyword4;
    private Keyword keyword5;
    private Keyword remark;
}
