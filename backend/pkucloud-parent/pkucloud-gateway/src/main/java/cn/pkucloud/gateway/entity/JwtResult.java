package cn.pkucloud.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResult {
    private boolean valid;
    private String msg;
    private String issuer;
    private String subject;
    private String role;
    private int mod;

    public JwtResult(boolean valid, String msg) {
        this.valid = valid;
        this.msg = msg;
    }
}
