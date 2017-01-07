package com.hss01248.springdemo.bean;

/**
 * Created by Administrator on 2017/1/7 0007.
 */
public class BaseNetBean {

    private int code = 0;
    private String msg = "请求成功";
    private Object data;

    public BaseNetBean(int code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseNetBean(Object data){
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
