package cn.pkucloud.wxmp.dto.wx.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequestEntity {
    private String touser;
    private String msgtype;
}
