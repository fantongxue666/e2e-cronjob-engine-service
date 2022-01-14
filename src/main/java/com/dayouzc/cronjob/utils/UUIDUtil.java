package com.dayouzc.cronjob.utils;

import java.util.UUID;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月13日 13:10:00
 */
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
