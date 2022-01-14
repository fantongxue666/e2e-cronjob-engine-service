package com.dayouzc.cronjob.service;

import com.dayouzc.cronjob.model.CronInfo;

import java.util.List;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月12日 16:19:00
 */
public interface CronInfoService {

    /**
     * 查询定时任务列表
     */
    List<CronInfo> queryCronInfos(CronInfo cronInfo);
    /**
     * 新增定时任务
     */
    int addCronInfo(CronInfo cronInfo);
    /**
     * 根据id查询
     */
    CronInfo getById(String id);
    /**
     * 编辑
     */
    int updateCronInfo(CronInfo cronInfo);
    /**
     * 删除
     */
    int delCronInfo(String id);
}
