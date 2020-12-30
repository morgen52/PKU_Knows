package cn.pkucloud.obs.dto;

import lombok.Data;

@Data
public class Signature {
    private String ak;
    private String policy;
    private String signature;
}
