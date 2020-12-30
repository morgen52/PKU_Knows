package cn.pkucloud.wxmp.dto.wx.custom;

import cn.pkucloud.wxmp.dto.wx.common.News;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NewsRequestEntity extends BaseRequestEntity {
    private News news;

    public NewsRequestEntity(String touser, News news) {
        super(touser, "news");
        this.news = news;
    }
}
