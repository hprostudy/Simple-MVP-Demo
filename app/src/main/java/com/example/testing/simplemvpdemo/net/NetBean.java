package com.example.testing.simplemvpdemo.net;

/**
 * Created by H on 16/8/8.
 */
public class NetBean<T> {

    private int code;
    private T results;

    public T getResults() {
        return results;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setResults(T results) {
        this.results = results;
    }

    private String msg;
    private T result;
    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }



    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
