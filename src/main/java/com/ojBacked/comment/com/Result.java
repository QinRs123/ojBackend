package com.ojBacked.comment.com;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    public Integer code;
    public String msg;

    public T data;

    Result(ResultEnum e){
        this.code=e.getCode();
        this.msg=e.getMsg();
        this.data=null;
    }


    public static Result<String> ok(){
        return new Result<>(ResultEnum.SUCCESS);
    }

    public static <T> Result<T> bulid(ResultEnum e, T data){
        Result result = new Result(e);
        result.setData(data);;
        return result;
    }
    public static <T> Result<T> bulid(Integer code,String msg,T data){
        Result result = new Result(code,msg,data);
        return result;
    }

    public static <T> Result<T> ok(T data){
        return bulid(ResultEnum.SUCCESS,data);
    }

    public static Result fail(){
        return Result.fail(null);
    }
    public static Result fail(Integer code, String msg){
        return new Result(code,msg,null);
    }

    public static<T> Result<T> fail(T data){
        return bulid(ResultEnum.ERROR,data);
    }
    public static<T> Result<T> fail(String msg){
        return bulid(444,msg,null);
    }

}
