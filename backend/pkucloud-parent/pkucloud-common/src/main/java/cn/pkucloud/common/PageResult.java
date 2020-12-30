package cn.pkucloud.common;

import lombok.Data;

import java.util.List;

import static cn.pkucloud.common.ResultCode.OK;

@Data
public class PageResult<T> {
    private int code;
    private String msg;
    private List<T> data;

    public PageResult(List<T> data) {
        this.code = OK;
        this.msg = "ok";
        this.data = data;
    }

    public PageResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
