package cn.pkucloud.auth.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Data
@RedisHash("smscode")
public class SmsCode {
    @Id
    private String phone;
    private String code;
    private String pkuId;
    private String ip;
    private String ua;
    @TimeToLive
    private int ttl;
}
