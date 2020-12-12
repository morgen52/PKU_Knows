package cn.pkucloud.wxmp.dto.wx.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TemplateMessage {
    private String touser;
    private String template_id;
    private String url;
    private MiniProgram miniprogram;
    private Keywords data;
}
