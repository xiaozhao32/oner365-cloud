package com.oner365.monitor.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.dao.ISysTaskDao;
import com.oner365.monitor.entity.SysTask;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.util.CronUtils;
import com.oner365.monitor.util.ScheduleUtils;
import com.google.common.base.Strings;

/**
 * 定时任务调度信息 服务层
 *
 * @author liutao
 */
@Service
public class SysTaskServiceImpl implements ISysTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysTaskServiceImpl.class);

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ISysTaskDao dao;


    /**
     * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.clear();
        List<SysTask> taskList = dao.findAll();
        taskList.forEach(task -> {
            try {
                ScheduleUtils.createScheduleJob(scheduler, task);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        });
    }

    /**
     * 获取quartz调度器的计划任务列表
     *
     * @param paramJson 调度信息
     * @return Page
     */
    @Override
    public Page<SysTask> pageList(JSONObject paramJson) {
        try {
            QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
            Pageable pageable = QueryUtils.buildPageRequest(data);
            Criteria<SysTask> criteria = new Criteria<>();
            criteria.add(Restrictions.like("taskName", paramJson.getString("taskName")));
            criteria.add(Restrictions.eq("taskGroup", paramJson.getString("taskGroup")));
            criteria.add(Restrictions.eq("status", paramJson.getString("status")));
            return dao.findAll(criteria, pageable);
        } catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    /**
     * 通过调度任务ID查询调度信息
     *
     * @param id 调度任务ID
     * @return 调度任务对象信息
     */
    @Override
    public SysTask selectTaskById(String id) {
        Optional<SysTask> optional = dao.findById(id);
        return optional.orElse(null);
    }

    /**
     * 暂停任务
     *
     * @param task 调度信息
     * @throws TaskException 异常
     */
    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public int pauseTask(SysTask task) throws SchedulerException, TaskException {
        String taskId = task.getId();
        String taskGroup = task.getTaskGroup();
        task.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        save(task);
        scheduler.pauseJob(ScheduleUtils.getJobKey(taskId, taskGroup));
        return 1;
    }

    /**
     * 恢复任务
     *
     * @param task 调度信息
     */
    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public int resumeTask(SysTask task) throws SchedulerException,TaskException {
        String taskId = task.getId();
        String taskGroup = task.getTaskGroup();
        task.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        save(task);
        scheduler.resumeJob(ScheduleUtils.getJobKey(taskId, taskGroup));
        return 1;
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param task 调度信息
     */
    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public int deleteTask(SysTask task) throws SchedulerException {
        String id = task.getId();
        String taskGroup = task.getTaskGroup();
        dao.deleteById(id);
        scheduler.deleteJob(ScheduleUtils.getJobKey(id, taskGroup));
        return 1;
    }

    /**
     * 批量删除调度信息
     *
     * @param ids 需要删除的任务ID
     */
    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public void deleteTaskByIds(String[] ids) throws SchedulerException {
        for (String id : ids) {
            SysTask task = selectTaskById(id);
            deleteTask(task);
        }
    }

    /**
     * 任务调度状态修改
     *
     * @param task 调度信息
     * @return int
     * @throws SchedulerException 异常
     * @throws TaskException 异常
     */
    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public int changeStatus(SysTask task) throws SchedulerException, TaskException {
        int rows = 0;
        String status = task.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            rows = resumeTask(task);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            rows = pauseTask(task);
        }
        return rows;
    }

    /**
     * 立即运行任务
     *
     * @param task 调度信息
     */
    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public void run(SysTask task) throws SchedulerException {
        String id = task.getId();
        String taskGroup = task.getTaskGroup();
        SysTask properties = selectTaskById(task.getId());
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        scheduler.triggerJob(ScheduleUtils.getJobKey(id, taskGroup), dataMap);
    }

    /**
     * 保存任务
     *
     * @param task 调度信息 调度信息
     */
    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public int save(SysTask task) throws SchedulerException, TaskException {
        boolean isAdd = Strings.isNullOrEmpty(task.getId());
        if(isAdd && Strings.isNullOrEmpty(task.getStatus())){
            task.setStatus(ScheduleConstants.Status.PAUSE.getValue());
            task.setCreateTime(new Date());
        }
        task = dao.save(task);
        if (isAdd) {
            ScheduleUtils.createScheduleJob(scheduler, task);
        }
        return 1;
    }

    /**
     * 更新任务的时间表达式
     *
     * @param task 调度信息
     */
    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public int updateTask(SysTask task) throws SchedulerException, TaskException {
        SysTask properties = selectTaskById(task.getId());
        save(task);
        updateSchedulerTask(task, properties.getTaskGroup());
        return 1;
    }

    /**
     * 更新任务
     *
     * @param task      任务对象
     * @param taskGroup 任务组名
     */
    public void updateSchedulerTask(SysTask task, String taskGroup) throws SchedulerException, TaskException {
        String taskId = task.getId();
        // 判断是否存在
        JobKey taskKey = ScheduleUtils.getJobKey(taskId, taskGroup);
        if (scheduler.checkExists(taskKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(taskKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, task);
    }

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return CronUtils.isValid(cronExpression);
    }
}
