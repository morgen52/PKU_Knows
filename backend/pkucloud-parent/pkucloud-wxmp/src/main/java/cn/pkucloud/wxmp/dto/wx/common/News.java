package cn.pkucloud.wxmp.dto.wx.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class News {
    private Article[] articles;
}
