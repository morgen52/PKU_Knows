package cn.pkucloud.auth.controller;

import cn.pkucloud.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static cn.pkucloud.common.ResultCode.BAD_REQUEST;

@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<?> handler(Exception e) {
        e.printStackTrace();
        return new Result<>(BAD_REQUEST, "bad request");
    }
}
