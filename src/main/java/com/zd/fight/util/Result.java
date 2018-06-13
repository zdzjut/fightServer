package com.zd.fight.util;

public class Result {

    private int result;

    private String message;

    private Object data;

    public Result() {
    }

    public Result(int result) {
        this.result = result;
    }

    public Result(int result, String message, Object data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public Result(int result, String message) {
        this.result = result;
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
