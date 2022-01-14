package com.dayouzc.cronjob.utils;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月13日 14:57:00
 */
public class HttpUtil {

    /**
     * 发送get请求
     * @param url
     * @param params
     */
    public static void sendGet(String url,String... params){
        System.out.println("====== 发送请求 BEGIN ======");
        System.out.println("请求地址："+url);
        System.out.println("====== 发送请求 END ======");

//        Map<String,String> map=new HashMap<String, String>();
//        RestTemplate restTemplate=new RestTemplate();
//        restTemplate.getForObject(url, String.class, map);
    }
}
