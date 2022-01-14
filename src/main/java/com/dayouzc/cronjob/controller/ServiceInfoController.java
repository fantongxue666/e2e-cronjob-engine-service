package com.dayouzc.cronjob.controller;

import com.dayouzc.cronjob.config.DynamicTask;
import com.dayouzc.cronjob.constant.Constants;
import com.dayouzc.cronjob.constant.ResponseData;
import com.dayouzc.cronjob.model.ServiceInfo;
import com.dayouzc.cronjob.service.ServiceInfoService;
import com.dayouzc.cronjob.service.ServiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月12日 16:22:00
 *
 * /all         查询服务器列表
 */
@RestController
@RequestMapping("/serviceInfo")
public class ServiceInfoController {

    @Autowired
    private ServiceInfoService serviceInfoService;

    /**
     * 查询服务器列表
     * @param cronInfo
     * @return
     */
    @GetMapping("/all")
    public ResponseData<List<ServiceInfo>> queryCrons(ServiceInfo cronInfo){
        //1，校验
        //2，业务处理
        cronInfo.setStatus(Constants.StatusConstant.NORMAL);
        List<ServiceInfo> cronInfos = serviceInfoService.queryServiceInfos(cronInfo);
        //3，响应
        ResponseData<List<ServiceInfo>> listResponseData = new ResponseData<>();
        listResponseData.setResult(cronInfos);
        return listResponseData;
    }

    /**
     * 新增服务器
     * @param cronInfo
     * @return
     */
    @PostMapping("/add")
    public ResponseData addCron(ServiceInfo cronInfo){
        //1，校验
        //2，业务处理
        serviceInfoService.addServiceInfo(cronInfo);
        //3，响应
        ResponseData responseData = new ResponseData<>();
        return responseData;
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public ResponseData getById(String id){
        //1，校验
        //2，业务处理
        ServiceInfo cronInfo = serviceInfoService.getById(id);
        //3，响应
        ResponseData responseData = new ResponseData<>();
        responseData.setResult(cronInfo);
        return responseData;
    }

    /**
     * 编辑
     * @param cronInfo
     * @return
     */
    @PostMapping("/update")
    public ResponseData edit(ServiceInfo cronInfo){
        //1，校验
        //2，业务处理
        serviceInfoService.updateServiceInfo(cronInfo);
        //3，响应
        ResponseData responseData = new ResponseData<>();
        return responseData;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/del")
    public ResponseData del(String id){
        //1，校验
        //2，业务处理
        serviceInfoService.delServiceInfo(id);
        //3，响应
        ResponseData responseData = new ResponseData<>();
        return responseData;
    }

}
