package cn.pkucloud.wxa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SceneInfo {
    private String ip;
    private String ua;
    private int state;
}
