package cn.pkucloud.wxmp.dto.wx.custom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Article {
    private String title;
    private String description;
    private String url;
    private String picurl;
}
