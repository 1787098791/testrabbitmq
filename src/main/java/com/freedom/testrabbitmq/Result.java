package com.freedom.testrabbitmq;

/**
 * @author ：wujie
 * @date ：Created in 2020/2/13 18:51
 * @description：
 * @version:
 */
public class Result {
   private Object object;
   private String code;
   private String message;

    public Result(Object object) {
        this.object = object;
    }

    public Result(Object object, String code, String message) {
        this.object = object;
        this.code = code;
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
