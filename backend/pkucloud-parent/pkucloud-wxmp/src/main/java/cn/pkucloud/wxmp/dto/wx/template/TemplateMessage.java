package cn.pkucloud.wxmp.dto.wx.template;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateMessage {
    private String touser;
    private String template_id;
    private String url;
    private MiniProgram miniprogram;
    private Keywords data;
}
