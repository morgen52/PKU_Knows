package cn.pkucloud.wxmp.entity.wx;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Ticket extends BaseResult {
    private String ticket;
    private int expires_in;
}
