package com.oner365.monitor.controller.task;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseResult;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.constants.ErrorInfo;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.monitor.entity.SysTask;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.util.CronUtils;
import com.oner365.monitor.vo.SysTaskVo;

/**
 * 调度任务信息操作处理
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/task")
public class SysTaskController extends BaseController {

    @Autowired
    private ISysTaskService taskService;

    /**
     * 查询定时任务列表
     *
     * @param data 查询参数
     * @return Page<SysTask>
     */
    @PostMapping("/list")
    public Page<SysTask> list(@RequestBody QueryCriteriaBean data) {
        return taskService.pageList(data);
    }

    /**
     * 导出定时任务列表
     *
     * @param data 查询参数
     * @return String
     */
    @GetMapping("/export")
    public String export(@RequestBody QueryCriteriaBean data) {
        return PublicConstants.SUCCESS;
    }

    /**
     * 获取定时任务详细信息
     *
     * @param id 主键
     * @return SysTask
     */
    @GetMapping("/{id}")
    public SysTask getInfo(@PathVariable String id) {
        return taskService.selectTaskById(id);
    }

    /**
     * 新增定时任务
     *
     * @param paramJson 参数
     * @param authUser 登录对象
     * @return ResponseResult<Integer>
     * @throws SchedulerException, TaskException 异常
     */
    @PostMapping
    public ResponseResult<Integer> add(@RequestBody SysTaskVo sysTaskVo, @CurrentUser AuthUser authUser)
            throws SchedulerException, TaskException {
        SysTask sysTask = sysTaskVo.toObject();

        if (sysTask == null || !CronUtils.isValid(sysTask.getCronExpression())) {
            return ResponseResult.error("cron表达式不正确");
        }
        sysTask.setCreateUser(authUser.getUserName());

        int code = taskService.save(sysTask);
        return ResponseResult.success(code);
    }

    /**
     * 修改定时任务
     *
     * @param sysTaskVo 参数
     * @param authUser 登录对象
     * @return ResponseResult<Integer>
     * @throws SchedulerException, TaskException 异常
     */
    @PutMapping
    public ResponseResult<Integer> edit(@RequestBody SysTaskVo sysTaskVo, @CurrentUser AuthUser authUser)
            throws SchedulerException, TaskException {
        SysTask sysTask = sysTaskVo.toObject();

        if (sysTask == null || !CronUtils.isValid(sysTask.getCronExpression())) {
            return ResponseResult.error("cron表达式不正确");
        }
        int code = taskService.updateTask(sysTask);
        return ResponseResult.success(code);
    }

    /**
     * 定时任务状态修改
     *
     * @param sysTaskVo 参数
     * @return ResponseResult<Integer>
     * @throws SchedulerException, TaskException 异常
     */
    @PutMapping("/changeStatus")
    public ResponseResult<Integer> changeStatus(@RequestBody SysTaskVo sysTaskVo) throws SchedulerException, TaskException {
        if (sysTaskVo != null) {
            SysTask sysTask = sysTaskVo.toObject();
            if (sysTask != null) {
                SysTask newJob = taskService.selectTaskById(sysTask.getId());
                newJob.setStatus(sysTask.getStatus());
                int code = taskService.changeStatus(newJob);
                return ResponseResult.success(code);
            }
        }
        return ResponseResult.error(ErrorInfo.ERR_SAVE_ERROR);
    }

    /**
     * 定时任务立即执行一次
     *
     * @param sysTaskVo 参数
     * @return ResponseResult<String>
     * @throws SchedulerException 异常
     */
    @PutMapping("/run")
    public ResponseResult<String> run(@RequestBody SysTaskVo sysTaskVo) throws SchedulerException {
        if (sysTaskVo != null) {
            taskService.run(sysTaskVo.toObject());
            return ResponseResult.success(PublicConstants.SUCCESS);
        }
        return ResponseResult.error("执行失败");
    }

    /**
     * 删除定时任务
     *
     * @param ids 主键
     * @return ResponseResult<String>
     * @throws SchedulerException 异常
     */
    @DeleteMapping("/{ids}")
    public ResponseResult<String> remove(@PathVariable String[] ids) throws SchedulerException {
        taskService.deleteTaskByIds(ids);
        return ResponseResult.success(PublicConstants.SUCCESS);
    }
}
