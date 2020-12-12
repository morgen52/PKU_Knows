package cn.pkucloud.wxmp.dto.wx.xml;

import cn.pkucloud.wxmp.dto.wx.common.Article;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NewsResponseEntity extends BaseResponseEntity {
    @JacksonXmlProperty(localName = "ArticleCount")
    private int articleCount;

    @JacksonXmlElementWrapper(localName = "Articles")
    @JacksonXmlProperty(localName = "item")
    private Article[] articles;

    public NewsResponseEntity(String toUserName, Article[] articles) {
        super(toUserName, "news");
        this.articleCount = articles.length;
        this.articles = articles;
    }
}
