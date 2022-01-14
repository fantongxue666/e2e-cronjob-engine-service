package com.dayouzc.cronjob.mapper;

import com.dayouzc.cronjob.model.ServiceInfo;
import com.dayouzc.cronjob.model.ServiceInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月12日 16:01:00
 */
@Mapper
@Repository
public interface ServiceInfoMapper {

    /**
     * 查询服务器列表
     */
    @Select("select a.* from service_info a where a.status = #{serviceInfo.status} order by a.create_time desc")
    @Results({
            @Result(id=true,property="serviceId",column="service_id"),
            @Result(property="serviceName",column="service_name"),
            @Result(property="serviceIp",column="service_ip"),
            @Result(property="servicePort",column="service_port"),
            @Result(property="createTime",column="create_time"),
            @Result(property="updateTime",column="update_time"),
            @Result(property="status",column="status")
    })
    List<ServiceInfo> queryServiceInfos(@Param("serviceInfo") ServiceInfo serviceInfo);

    /**
     * 新增服务器
     */
    @Insert("insert into service_info values(#{serviceInfo.serviceId},#{serviceInfo.serviceName},#{serviceInfo.serviceIp},#{serviceInfo.servicePort}," +
            "#{serviceInfo.createTime},#{serviceInfo.updateTime},1)")
    int addServiceInfos(@Param("serviceInfo") ServiceInfo serviceInfo);

    /**
     * 根据id查询服务器
     */
    @Select("select * from service_info where service_id = #{id}")
    @Results({
            @Result(id=true,property="serviceId",column="service_id"),
            @Result(property="serviceName",column="service_name"),
            @Result(property="serviceIp",column="service_ip"),
            @Result(property="servicePort",column="service_port"),
            @Result(property="createTime",column="create_time"),
            @Result(property="updateTime",column="update_time"),
            @Result(property="status",column="status")
    })
    ServiceInfo getById(@Param("id") String id);

    /**
     * 编辑
     */
    @Update("update service_info set " +
            "service_name=#{serviceInfo.serviceName}," +
            "service_ip=#{serviceInfo.serviceIp}," +
            "service_port=#{serviceInfo.servicePort}," +
            "update_time=#{serviceInfo.updateTime} " +
            "where service_id=#{serviceInfo.serviceId}")
    int updateServiceInfo(@Param("serviceInfo") ServiceInfo serviceInfo);

    /**
     * 删除
     */
    @Update("update service_info set status = '0' where service_id=#{id}")
    int delServiceInfo(@Param("id") String id);

}
