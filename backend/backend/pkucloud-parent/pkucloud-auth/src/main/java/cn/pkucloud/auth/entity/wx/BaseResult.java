package cn.pkucloud.auth.entity.wx;

import lombok.Data;

@Data
public class BaseResult {
    private int errcode;
    private String errmsg;
}
