package com.dayouzc.cronjob.service.impl;

import com.dayouzc.cronjob.constant.Constants;
import com.dayouzc.cronjob.mapper.CronInfoMapper;
import com.dayouzc.cronjob.model.CronInfo;
import com.dayouzc.cronjob.service.CronInfoService;
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
public class CronInfoServiceImpl implements CronInfoService {

    @Autowired
    private CronInfoMapper cronInfoMapper;

    @Override
    public List<CronInfo> queryCronInfos(CronInfo cronInfo) {
        return cronInfoMapper.queryCronInfos(cronInfo);
    }

    @Override
    public int addCronInfo(CronInfo cronInfo) {
        cronInfo.setCronId(UUIDUtil.getUUID());
        cronInfo.setCronStatus(Constants.CronConstant.STOPED);
        cronInfo.setCreateTime(new Date());
        cronInfo.setUpdateTime(null);
        return cronInfoMapper.addCronInfos(cronInfo);
    }

    @Override
    public CronInfo getById(String id) {
        return cronInfoMapper.getById(id);
    }

    @Override
    public int updateCronInfo(CronInfo cronInfo) {
        cronInfo.setUpdateTime(new Date());
        return cronInfoMapper.updateCronInfo(cronInfo);
    }

    @Override
    public int delCronInfo(String id) {
        return cronInfoMapper.delCronInfo(id);
    }
}
