package com.dayouzc.cronjob.service;

import com.dayouzc.cronjob.model.ServiceInfo;

import java.util.List;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月12日 16:19:00
 */
public interface ServiceInfoService {

    /**
     * 查询服务器列表
     */
    List<ServiceInfo> queryServiceInfos(ServiceInfo cronInfo);
    /**
     * 新增服务器
     */
    int addServiceInfo(ServiceInfo cronInfo);
    /**
     * 根据id查询
     */
    ServiceInfo getById(String id);
    /**
     * 编辑
     */
    int updateServiceInfo(ServiceInfo cronInfo);
    /**
     * 删除
     */
    int delServiceInfo(String id);
}
