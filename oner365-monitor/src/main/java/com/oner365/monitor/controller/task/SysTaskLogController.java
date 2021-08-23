package com.oner365.monitor.controller.task;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.monitor.entity.SysTaskLog;
import com.oner365.monitor.service.ISysTaskLogService;
import com.google.common.collect.Maps;

/**
 * 调度日志操作处理
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/taskLog")
public class SysTaskLogController extends BaseController {
    
    @Autowired
    private ISysTaskLogService taskLogService;

    /**
     * 查询定时任务调度日志列表
     * 
     * @param paramJson 查询参数
     * @return Page<SysTaskLog>
     */
    @PostMapping("/list")
    public Page<SysTaskLog> list(@RequestBody JSONObject paramJson) {
        return taskLogService.pageList(paramJson);
    }

    /**
     * 导出定时任务调度日志列表
     * 
     * @param paramJson 查询参数
     * @return String
     */
    @GetMapping("/export")
    public String export(@RequestBody JSONObject paramJson) {
        return PublicConstants.SUCCESS;
    }

    /**
     * 根据调度编号获取详细信息
     * 
     * @param id 主键
     * @return SysTaskLog
     */
    @GetMapping("/{id}")
    public SysTaskLog getInfo(@PathVariable String id) {
        return taskLogService.selectTaskLogById(id);
    }

    /**
     * 删除定时任务调度日志
     * 
     * @param ids 主键
     * @return Map<String, Object>
     */
    @DeleteMapping("/{ids}")
    public Map<String, Object> remove(@PathVariable String[] ids) {
        int code = taskLogService.deleteTaskLogByIds(ids);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 清空定时任务调度日志
     * 
     * @return String
     */
    @DeleteMapping("/clean")
    public String clean() {
        taskLogService.cleanTaskLog();
        return PublicConstants.SUCCESS;
    }
}
