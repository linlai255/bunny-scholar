package com.linlai.bunnyscholar.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;

    private String msg;

    private T data;

    public static Result success() {
        Result objectResult = new Result<>();
        objectResult.setCode(200);
        objectResult.setMsg("");
        return objectResult;
    }

    public static<T> Result success(T data) {
        Result objectResult = new Result<>();
        objectResult.setCode(200);
        objectResult.setMsg("");
        objectResult.setData(data);
        return objectResult;
    }

    public static Result fail(String msg) {
        Result objectResult = new Result<>();
        objectResult.setCode(500);
        objectResult.setMsg(msg);
        return objectResult;
    }

    public static Result fail() {
        Result objectResult = new Result<>();
        objectResult.setCode(500);
        objectResult.setMsg("");
        return objectResult;
    }
}
