package cn.pkucloud.common;

import lombok.Data;

import static cn.pkucloud.common.ResultCode.OK;

@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result() {
        this.code = OK;
        this.msg = "ok";
    }

    public Result(T data) {
        this.code = OK;
        this.msg = "ok";
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
