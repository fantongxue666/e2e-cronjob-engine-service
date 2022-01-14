package com.dayouzc.cronjob.service.impl;

import com.dayouzc.cronjob.constant.Constants;
import com.dayouzc.cronjob.mapper.CronInfoMapper;
import com.dayouzc.cronjob.mapper.ServiceInfoMapper;
import com.dayouzc.cronjob.model.CronInfo;
import com.dayouzc.cronjob.model.ServiceInfo;
import com.dayouzc.cronjob.service.CronInfoService;
import com.dayouzc.cronjob.service.ServiceInfoService;
import com.dayouzc.cronjob.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月12日 16:21:00
 */
@Service
public class ServiceInfoServiceImpl implements ServiceInfoService {

    @Autowired
    private ServiceInfoMapper serviceInfoMapper;


    @Override
    public List<ServiceInfo> queryServiceInfos(ServiceInfo cronInfo) {
        return serviceInfoMapper.queryServiceInfos(cronInfo);
    }

    @Override
    public int addServiceInfo(ServiceInfo cronInfo) {
        cronInfo.setServiceId(UUIDUtil.getUUID());
        cronInfo.setCreateTime(new Date());
        cronInfo.setUpdateTime(null);
        return serviceInfoMapper.addServiceInfos(cronInfo);
    }

    @Override
    public ServiceInfo getById(String id) {
        return serviceInfoMapper.getById(id);
    }

    @Override
    public int updateServiceInfo(ServiceInfo cronInfo) {
        cronInfo.setUpdateTime(new Date());
        return serviceInfoMapper.updateServiceInfo(cronInfo);
    }

    @Override
    public int delServiceInfo(String id) {
        return serviceInfoMapper.delServiceInfo(id);
    }
}
