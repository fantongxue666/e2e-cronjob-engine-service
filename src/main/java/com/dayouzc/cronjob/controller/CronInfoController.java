package com.dayouzc.cronjob.controller;

import com.dayouzc.cronjob.config.DynamicTask;
import com.dayouzc.cronjob.constant.Constants;
import com.dayouzc.cronjob.constant.ResponseData;
import com.dayouzc.cronjob.model.CronInfo;
import com.dayouzc.cronjob.service.CronInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月12日 16:22:00
 *
 * /all         查询定时任务列表
 */
@RestController
@RequestMapping("/cronInfo")
public class CronInfoController {

    @Autowired
    private CronInfoService cronInfoService;
    @Autowired
    private DynamicTask dynamicTask;

    /**
     * 查询定时任务列表
     * @param cronInfo
     * @return
     */
    @GetMapping("/all")
    public ResponseData<List<CronInfo>> queryCrons(CronInfo cronInfo){
        //1，校验
        //2，业务处理
        cronInfo.setStatus(Constants.StatusConstant.NORMAL);
        List<CronInfo> cronInfos = cronInfoService.queryCronInfos(cronInfo);
        //3，响应
        ResponseData<List<CronInfo>> listResponseData = new ResponseData<>();
        listResponseData.setResult(cronInfos);
        return listResponseData;
    }

    /**
     * 新增定时任务
     * @param cronInfo
     * @return
     */
    @PostMapping("/add")
    public ResponseData addCron(CronInfo cronInfo){
        //1，校验
        //2，业务处理
        cronInfoService.addCronInfo(cronInfo);
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
        CronInfo cronInfo = cronInfoService.getById(id);
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
    public ResponseData edit(CronInfo cronInfo){
        //1，校验
        //2，业务处理
        CronInfo cronInfo1 = cronInfoService.getById(cronInfo.getCronId());
        cronInfo.setCronStatus(cronInfo1.getCronStatus());
        cronInfoService.updateCronInfo(cronInfo);
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
        CronInfo cronInfo = cronInfoService.getById(id);
        if(Constants.CronConstant.STARTED.equals(cronInfo.getCronStatus())){
            return new ResponseData("500","此任务正在运行，需要先终止该任务后方可删除");
        }
        //2，业务处理
        cronInfoService.delCronInfo(id);
        //3，响应
        ResponseData responseData = new ResponseData<>();
        return responseData;
    }
    /**
     * 开启定时任务
     * @param id
     * @return
     */
    @GetMapping("/start")
    public ResponseData start(String id){

        //1，校验
        //2，业务处理
        CronInfo cronInfo = cronInfoService.getById(id);
        cronInfo.setCronStatus(Constants.CronConstant.STARTED);
        cronInfoService.updateCronInfo(cronInfo);
        //动态开启定时任务
        dynamicTask.startTask(cronInfo.getCronId(),cronInfo.getCronExpression());
        //3，响应
        ResponseData responseData = new ResponseData<>();
        return responseData;
    }
    /**
     * 停止定时任务
     * @param id
     * @return
     */
    @GetMapping("/stop")
    public ResponseData stop(String id){
        //1，校验
        //2，业务处理
        CronInfo cronInfo = cronInfoService.getById(id);
        cronInfo.setCronStatus(Constants.CronConstant.STOPED);
        cronInfoService.updateCronInfo(cronInfo);
        //动态停止定时任务
        dynamicTask.stopTaskByTaskId(cronInfo.getCronId());
        //3，响应
        ResponseData responseData = new ResponseData<>();
        return responseData;
    }
}
