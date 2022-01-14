package com.dayouzc.cronjob.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月12日 16:02:00
 */
public class CronInfo {
    /**
     * 主键
     */
    private String cronId;
    /**
     * 服务器id
     */
    private String serviceId;
    /**
     * 服务器名称
     */
    private String serviceName;
    /**
     * 定时任务名称
     */
    private String cronName;
    /**
     * 正则表达式
     */
    private String cronExpression;
    /**
     * 定时任务执行的逻辑接口uri
     */
    private String cronUri;
    /**
     * 定时任务状态 1：已开启  2：已停止
     */
    private String cronStatus;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 状态 1：正常  0：删除
     */
    private String status;
    /**
     * 备注
     */
    private String memo;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCronId() {
        return cronId;
    }

    public void setCronId(String cronId) {
        this.cronId = cronId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCronName() {
        return cronName;
    }

    public void setCronName(String cronName) {
        this.cronName = cronName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getCronUri() {
        return cronUri;
    }

    public void setCronUri(String cronUri) {
        this.cronUri = cronUri;
    }

    public String getCronStatus() {
        return cronStatus;
    }

    public void setCronStatus(String cronStatus) {
        this.cronStatus = cronStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
