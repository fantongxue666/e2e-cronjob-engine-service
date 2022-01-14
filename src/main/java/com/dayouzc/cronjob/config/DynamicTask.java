package com.dayouzc.cronjob.config;

import com.dayouzc.cronjob.model.CronInfo;
import com.dayouzc.cronjob.model.ServiceInfo;
import com.dayouzc.cronjob.service.CronInfoService;
import com.dayouzc.cronjob.service.ServiceInfoService;
import com.dayouzc.cronjob.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.expression.Lists;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author FanJiangFeng
 * @createTime 2022年01月13日 14:42:00
 *
 * 动态操作定时任务的全靠SchedulingConfigurer类
 * 关于SchedulingConfigurer类的讲解（推荐）：https://cloud.tencent.com/developer/article/1362794
 */
@Configuration
@EnableScheduling
public class DynamicTask implements SchedulingConfigurer{
    @Autowired
    private CronInfoService cronInfoService;
    @Autowired
    private ServiceInfoService serviceInfoService;

    //定时任务注册器
    private volatile ScheduledTaskRegistrar registrar;

    //ScheduledFuture 真正的定时任务执行者
    //这里key = taskId（任务id，自定义，唯一标识） value = 定时任务实例
    private final ConcurrentHashMap<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();

    //CronTask 真正的定时任务
    //这里key = taskId（任务id，自定义，唯一标识） value = 定时任务
    private final ConcurrentHashMap<String, CronTask> cronTasks = new ConcurrentHashMap<>();

    //任务对象信息集合
    private volatile List<TaskConstant> taskConstants = new ArrayList<>();

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        this.registrar = registrar;
        /**
         * 给定时任务注册器中添加一个Trigger（PeriodicTrigger），五秒已执行，根据目前的taskConstants自定义任务对象集合更新系统的定时任务
         * Trigger接口
         *      CronTrigger实现类
         *      PeriodicTrigger实现类
         * 具体CronTrigger和PeriodicTrigger的区别见文章：https://blog.csdn.net/qq_39720208/article/details/120970665
         */
        this.registrar.addTriggerTask(() -> {
                    //定时轮询自定义任务对象，如果自定义任务对象集合不为空，并把自定义任务对象信息装载到TimingTask中
                    if (!CollectionUtils.isEmpty(taskConstants)) {
                        System.out.println("检测动态定时任务列表...");
                        List<TimingTask> tts = new ArrayList<>();
                        taskConstants
                                .forEach(taskConstant -> {
                                    TimingTask tt = new TimingTask();
                                    tt.setExpression(taskConstant.getCron());
                                    tt.setTaskId(taskConstant.getTaskId());
                                    tts.add(tt);
                                });
                        //刷新任务状态
                        this.refreshTasks(tts);
                    }
                }
                , triggerContext -> new PeriodicTrigger(5L, TimeUnit.SECONDS).nextExecutionTime(triggerContext));
    }


    public List<TaskConstant> getTaskConstants() {
        return taskConstants;
    }

    /**
     * 根据taskId停止任务
     * @param taskId 任务id
     */
    public void stopTaskByTaskId(String taskId){
        ScheduledFuture<?> scheduledFuture = scheduledFutures.get(taskId);
        if(scheduledFuture != null){
            System.out.println("------------------- 删除任务成功！！ ----------------");
            scheduledFuture.cancel(true);
        }else{
            System.out.println("------------------- 删除任务失败！！ ----------------");
        }
    }

    /**
     * 根据taskId和cron开启一个新任务
     * @param taskId 任务id
     * @param cron 正则表达式
     */
    public void startTask(String taskId,String cron){
        List<DynamicTask.TaskConstant> taskConstants = getTaskConstants();
        ScheduledFuture<?> scheduledFuture = scheduledFutures.remove(taskId);
        if(scheduledFuture != null){
            scheduledFuture.cancel(true);
        }
        //添加新任务
        DynamicTask.TaskConstant taskConstant = new DynamicTask.TaskConstant();
        taskConstant.setCron(cron);
        taskConstant.setTaskId(taskId);
        taskConstants.add(taskConstant);
        System.out.println("------------------- 添加新任务成功！！ ----------------");
    }


    /**
     * 刷新任务状态
     * @param tasks List<TimingTask>
     */
    private void refreshTasks(List<TimingTask> tasks) {
        //遍历taskId列表，如果taskId对应的任务已经不存在了，则取消系统中该任务的执行
        Set<String> taskIds = scheduledFutures.keySet();
        for (String taskId : taskIds) {
            if (!exists(tasks, taskId)) {
                scheduledFutures.get(taskId).cancel(false);
            }
        }
        //对所有定时任务信息进行遍历
        for (TimingTask tt : tasks) {
            //校验正则表达式是否合法
            String expression = tt.getExpression();
            if (StringUtils.isBlank(expression) || !CronSequenceGenerator.isValidExpression(expression)) {
                System.out.println("定时任务DynamicTask cron表达式不合法: " + expression);
                continue;
            }
            //如果配置一致，则不需要重新创建定时任务
            if (scheduledFutures.containsKey(tt.getTaskId())
                    && cronTasks.get(tt.getTaskId()).getExpression().equals(expression)) {
                continue;
            }
            //如果策略执行时间发生了变化，则取消当前策略的任务
            if (scheduledFutures.containsKey(tt.getTaskId())) {
                scheduledFutures.remove(tt.getTaskId()).cancel(false);
                cronTasks.remove(tt.getTaskId());
            }
            //创建真实的定时任务
            CronTask task = new CronTask(tt, expression);
            /**
             * TaskScheduler  = registrar.getScheduler() 得到任务调度器
             * ScheduledFuture<?> future = TaskScheduler.schedule(Runnable task, Date startTime) 安排给定的Runnable，在指定的执行时间调用它，也就是执行TimingTask的run方法内容
             *
             * task：真实的定时任务 future：真实的定时任务执行者
             * 【task到future还需要触发】
             */
            ScheduledFuture<?> future = registrar.getScheduler().schedule(task.getRunnable(), task.getTrigger());
            //分别存放到集合中去
            cronTasks.put(tt.getTaskId(), task);
            scheduledFutures.put(tt.getTaskId(), future);
        }
    }

    /**
     * 判断定时任务是否存在
     * @param tasks 任务
     * @param taskId 任务id
     * @return
     */
    private boolean exists(List<TimingTask> tasks, String taskId) {
        for (TimingTask task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 销毁当前系统中正在运行的所有定时任务
     */
    @PreDestroy
    public void destroy() {
        this.registrar.destroy();
    }

    /**
     * 自定义任务对象，存储taskId和cron表达式，由业务层调用传入
     */
    public static class TaskConstant {
        private String cron;
        private String taskId;

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }
    }

    /**
     * 也算是一个定时任务吧（汗。。强行解释）
     * 是更新后的定时任务，实质上和上面一样，只不过作用的时间段有区别，前者在检测动态定时任务列表之前，后者在检测动态定时任务列表之后
     *
     * 定时任务就是一个异步线程对象（Runnable） And 定时Trigger
     * 实现Runable，为创建真实的定时任务CronTask做准备
     */
    private class TimingTask implements Runnable {
        private String expression;

        private String taskId;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            //############# 定时任务的业务逻辑 BEGIN ###############
            String taskId = this.getTaskId();
            CronInfo cronInfo = cronInfoService.getById(taskId);
            if(cronInfo == null){
                throw new RuntimeException("任务不存在");
            }
            ServiceInfo serviceInfo = serviceInfoService.getById(cronInfo.getServiceId());
            String url = serviceInfo.getServiceIp() + cronInfo.getCronUri();
            HttpUtil.sendGet(url,null);

            //############# 定时任务的业务逻辑 END ###############
        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this
                    , ToStringStyle.JSON_STYLE
                    , false
                    , false
                    , TimingTask.class);
        }

    }

    /**
     * 队列消费线程工厂类
     */
    private static class DynamicTaskConsumeThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DynamicTaskConsumeThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                    poolNumber.getAndIncrement() +
                    "-dynamic-task-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

}
