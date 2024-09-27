package com.ojBacked.comment.com;

public class MyThreadLocal {

    private static final ThreadLocal userId =new ThreadLocal<>();

    public static void set(Object value){
        userId.set(value);
    }

    public static <T> T get(){
        return (T)userId.get();
    }

    public static void remove(){
        userId.remove();
    }

}
