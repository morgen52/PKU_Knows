package cn.pkucloud.wxmp.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_pku_user_info")
public class PkuUserInfo {
    @TableId
    private String pkuId;
    private String name;
    private String usrT;
    private String abbr;
    private String gender;
    private String dept;
    private String stuT;
    private String birth;
    private String ethnic;
    private String natP;
    private String oriP;
    private String politics;
    private String major;
    private String addr;
    private Integer register;
    private Integer access;
}
