package com.ojBacked.comment.com;

public enum ResultEnum {


    SUCCESS(200,"操作成功"),
    ERROR(401, "操作失败");





    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;

    private final String msg;

}
