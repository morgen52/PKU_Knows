package cn.pkucloud.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodeInfo {
    private String ip;
    private String ua;
    private String code;
    private String pkuId;
}
