package com.oner365.monitor.controller.task;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.api.rabbitmq.dto.SysTaskDto;
import com.oner365.data.commons.auth.AuthUser;
import com.oner365.data.commons.auth.annotation.CurrentUser;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
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

    @Resource
    private ISysTaskService taskService;

    /**
     * 查询定时任务列表
     * @param data 查询参数
     * @return PageInfo<SysTaskDto>
     */
    @PostMapping("/list")
    public PageInfo<SysTaskDto> pageList(@RequestBody QueryCriteriaBean data) {
        return taskService.pageList(data);
    }

    /**
     * 获取定时任务详细信息
     * @param id 主键
     * @return SysTaskDto
     */
    @GetMapping("/{id}")
    public SysTaskDto getInfo(@PathVariable String id) {
        return taskService.selectTaskById(id);
    }

    /**
     * 新增定时任务
     * @param sysTaskVo 参数
     * @param authUser 登录对象
     * @return String
     * @throws SchedulerException, TaskException 异常
     */
    @PostMapping
    public String add(@Validated @RequestBody SysTaskVo sysTaskVo, @CurrentUser AuthUser authUser)
            throws SchedulerException, TaskException {
        if (sysTaskVo == null || !CronUtils.isValid(sysTaskVo.getCronExpression())) {
            return "cron表达式不正确";
        }
        sysTaskVo.setCreateUser(authUser.getUserName());

        Boolean result = taskService.save(sysTaskVo);
        return String.valueOf(result);
    }

    /**
     * 修改定时任务
     * @param sysTaskVo 参数
     * @return String
     * @throws SchedulerException, TaskException 异常
     */
    @PutMapping
    public String edit(@RequestBody SysTaskVo sysTaskVo) throws SchedulerException, TaskException {
        if (sysTaskVo == null || !CronUtils.isValid(sysTaskVo.getCronExpression())) {
            return "cron表达式不正确";
        }
        Boolean result = taskService.updateTask(sysTaskVo);
        return String.valueOf(result);
    }

    /**
     * 定时任务状态修改
     * @param sysTaskVo 参数
     * @return Boolean
     * @throws SchedulerException, TaskException 异常
     */
    @PutMapping("/status")
    public Boolean changeStatus(@RequestBody SysTaskVo sysTaskVo) throws SchedulerException {
        return taskService.changeStatus(sysTaskVo);
    }

    /**
     * 定时任务立即执行一次
     * @param sysTaskVo 参数
     * @return Boolean
     * @throws SchedulerException 异常
     */
    @PutMapping("/run")
    public Boolean run(@RequestBody SysTaskVo sysTaskVo) throws SchedulerException {
        return taskService.run(sysTaskVo);
    }

    /**
     * 删除定时任务
     * @param ids 主键
     * @return List<Boolean>
     */
    @DeleteMapping("/delete")
    public List<Boolean> remove(@RequestBody String[] ids) {
        return taskService.deleteTaskByIds(ids);
    }

    /**
     * 导出定时任务列表
     * @param data 查询参数
     * @return String
     */
    @PostMapping("/export")
    public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
        List<SysTaskDto> list = taskService.findList(data);

        String[] titleKeys = new String[] { "编号", "任务名称", "任务组", "调用目标", "目标参数", "执行表达式", "计划策略", "是否并发", "状态", "执行状态",
                "备注", "创建人", "创建时间", "更新时间" };
        String[] columnNames = { "id", "taskName", "taskGroup", "invokeTarget", "invokeParamDto", "cronExpression",
                "misfirePolicy", "concurrent", "status", "executeStatus", "remark", "createUser", "createTime",
                "updateTime" };

        String fileName = SysTaskDto.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

}
