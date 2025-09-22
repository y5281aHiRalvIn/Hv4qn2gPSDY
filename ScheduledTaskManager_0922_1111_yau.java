// 代码生成时间: 2025-09-22 11:11:51
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.TriggerBuilder;
import org.quartz.Trigger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Scheduler;

import java.util.Properties;
import java.util.Date;

public class ScheduledTaskManager {
    private Scheduler scheduler;

    // 初始化定时任务调度器
    public void initScheduler() throws Exception {
        // 创建调度器
        scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 定义定时任务
        JobDetail job = JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob", "group1").build();

        // 定义触发器，设置定时任务的执行周期
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.dailyAtHourAndMinute(10, 0);
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .withSchedule(scheduleBuilder)
                .build();

        // 将定时任务和触发器添加到调度器
        scheduler.scheduleJob(job, trigger);
    }

    // 开始调度任务
    public void start() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 停止调度任务
    public void stop() {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 定义定时任务要执行的工作
    public static class MyJob implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            System.out.println("定时任务执行...");
        }
    }

    public static void main(String[] args) {
        try {
            ScheduledTaskManager manager = new ScheduledTaskManager();
            manager.initScheduler();
            manager.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}