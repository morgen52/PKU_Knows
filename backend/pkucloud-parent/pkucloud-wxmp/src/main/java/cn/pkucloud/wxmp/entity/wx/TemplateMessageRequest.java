package cn.pkucloud.wxmp.entity.wx;

import lombok.Data;

@Data
public class TemplateMessageRequest {
    private String touser;
    private String template_id;
    private String url;
}
