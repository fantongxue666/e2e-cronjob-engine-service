package com.dayouzc.cronjob.constant;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 应用服务接口响应数据。
 *
 * Created by fish on 2017/4/17.
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ResponseData<T> implements Serializable {

    private String status;
    private String msg;
    T result;

    public ResponseData() {
        this.setStatus("10000");
        this.setMsg("success!");
    }

    public ResponseData(String errorCode, String msg) {
        this.setStatus(errorCode);
        this.setMsg(msg);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
