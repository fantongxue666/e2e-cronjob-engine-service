package com.dayouzc.cronjob.constant;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;


@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class RequestData<T> implements Serializable {

    /**
     * 业务数据信息，结构化数据
     */
    private T appData;

    public T getAppData() {
        return appData;
    }

    public void setAppData(T appData) {
        this.appData = appData;
    }

}
