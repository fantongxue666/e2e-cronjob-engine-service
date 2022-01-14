package com.dayouzc.cronjob.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月12日 16:50:00
 */
@Controller
@RequestMapping("")
public class ViewController {

    /**
     * 跳转登录页
     */
    @RequestMapping("/")
    public String login1(){
        return "page-login";
    }
    @RequestMapping("/view/login")
    public String login(){
        return "page-login";
    }

    /**
     * 跳转首页
     * @return
     */
    @RequestMapping("/view/index")
    public String index(){
        return "index";
    }

    /**
     * 跳转定时任务管理页
     * @return
     */
    @RequestMapping("/view/cronList")
    public String cronList(){
        return "cron-list";
    }

    /**
     * 跳转服务器管理页
     * @return
     */
    @RequestMapping("/view/serviceList")
    public String serviceList(){
        return "service-list";
    }

}
