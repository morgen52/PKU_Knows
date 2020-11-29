package cn.pkucloud.wxmp.entity.wx;

import lombok.Data;

@Data
public class BaseResult {
    private int errcode;
    private String errmsg;
}
