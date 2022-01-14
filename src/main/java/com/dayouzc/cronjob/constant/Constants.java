package com.dayouzc.cronjob.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月12日 16:32:00
 */
public class Constants {

    /** 状态 */
    public static final class StatusConstant {
        /** 正常 */
        public static final String NORMAL = "1";
        /** 已删除 */
        public static final String DELETED = "0";

        public static Map<String, String> map = new LinkedHashMap<String, String>() {
            {
                put("1", "正常");
                put("0", "已删除");
            }
        };
    }

    /** 定时任务状态 */
    public static final class CronConstant {
        /** 已开启 */
        public static final String STARTED = "1";
        /** 已停止 */
        public static final String STOPED = "2";

        public static Map<String, String> map = new LinkedHashMap<String, String>() {
            {
                put("1", "已开启");
                put("2", "已停止");
            }
        };
    }

}
