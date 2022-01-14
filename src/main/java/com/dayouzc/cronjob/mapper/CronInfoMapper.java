package com.dayouzc.cronjob.mapper;

import com.dayouzc.cronjob.model.CronInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月12日 16:01:00
 */
@Mapper
@Repository
public interface CronInfoMapper {

    /**
     * 查询定时任务列表
     */
    @Select("select a.*,b.service_name as serviceName from cron_info a,service_info b where a.service_id=b.service_id and a.status = #{cronInfo.status} order by a.create_time desc")
    @Results({
            @Result(id=true,property="cronId",column="cron_id"),
            @Result(property="serviceId",column="service_id"),
            @Result(property="cronName",column="cron_name"),
            @Result(property="cronExpression",column="cron_expression"),
            @Result(property="cronUri",column="cron_uri"),
            @Result(property="cronStatus",column="cron_status"),
            @Result(property="createTime",column="create_time"),
            @Result(property="updateTime",column="update_time"),
            @Result(property="status",column="status"),
            @Result(property="memo",column="memo")
    })
    List<CronInfo> queryCronInfos(@Param("cronInfo") CronInfo cronInfo);

    /**
     * 新增定时任务
     */
    @Insert("insert into cron_info values(#{cronInfo.cronId},#{cronInfo.serviceId},#{cronInfo.cronName},#{cronInfo.cronExpression}," +
            "#{cronInfo.cronUri},#{cronInfo.cronStatus},#{cronInfo.createTime},#{cronInfo.updateTime},1,#{cronInfo.memo})")
    int addCronInfos(@Param("cronInfo") CronInfo cronInfo);

    /**
     * 根据id查询任务
     */
    @Select("select * from cron_info where cron_id = #{id}")
    @Results({
            @Result(id=true,property="cronId",column="cron_id"),
            @Result(property="serviceId",column="service_id"),
            @Result(property="cronName",column="cron_name"),
            @Result(property="cronExpression",column="cron_expression"),
            @Result(property="cronUri",column="cron_uri"),
            @Result(property="cronStatus",column="cron_status"),
            @Result(property="createTime",column="create_time"),
            @Result(property="updateTime",column="update_time"),
            @Result(property="status",column="status"),
            @Result(property="memo",column="memo")
    })
    CronInfo getById(@Param("id") String id);

    /**
     * 编辑
     */
    @Update("update cron_info set service_id=#{cronInfo.serviceId},cron_name=#{cronInfo.cronName},cron_expression=#{cronInfo.cronExpression}," +
            "cron_uri=#{cronInfo.cronUri},cron_status=#{cronInfo.cronStatus},update_time=#{cronInfo.updateTime},memo=#{cronInfo.memo} " +
            "where cron_id=#{cronInfo.cronId}")
    int updateCronInfo(@Param("cronInfo") CronInfo cronInfo);

    /**
     * 删除
     */
    @Update("update cron_info set status = '0' where cron_id=#{id}")
    int delCronInfo(@Param("id") String id);

}
