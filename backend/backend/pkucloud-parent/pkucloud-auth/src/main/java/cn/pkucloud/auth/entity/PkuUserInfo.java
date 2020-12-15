package cn.pkucloud.auth.entity;

import cn.pkucloud.auth.dto.PkuUserInfoDto;
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
    private int createTime;
    private int accessTime;

    public PkuUserInfo(PkuUserInfoDto pkuUserInfoDto, int create, int access) {
        this.pkuId = pkuUserInfoDto.getPkuId();
        this.name = pkuUserInfoDto.getName();
        this.usrT = pkuUserInfoDto.getUsrT();
        this.abbr = pkuUserInfoDto.getAbbr();
        this.gender = pkuUserInfoDto.getGender();
        this.dept = pkuUserInfoDto.getDept();
        this.stuT = pkuUserInfoDto.getStuT();
        this.birth = pkuUserInfoDto.getBirth();
        this.ethnic = pkuUserInfoDto.getEthnic();
        this.natP = pkuUserInfoDto.getNatP();
        this.oriP = pkuUserInfoDto.getOriP();
        this.politics = pkuUserInfoDto.getPolitics();
        this.major = pkuUserInfoDto.getMajor();
        this.addr = pkuUserInfoDto.getAddr();
        if (0 != create) {
            this.createTime = create;
        }
        this.accessTime = access;
    }
}
