package com.oner365.monitor.controller.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.api.rabbitmq.dto.SysTaskLogDto;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.monitor.service.ISysTaskLogService;

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
   * @param data 查询参数
   * @return PageInfo<SysTaskLogDto>
   */
  @PostMapping("/list")
  public PageInfo<SysTaskLogDto> pageList(@RequestBody QueryCriteriaBean data) {
    return taskLogService.pageList(data);
  }

  /**
   * 根据调度编号获取详细信息
   *
   * @param id 主键
   * @return SysTaskLogDto
   */
  @GetMapping("/{id}")
  public SysTaskLogDto getInfo(@PathVariable String id) {
    return taskLogService.selectTaskLogById(id);
  }

  /**
   * 清空定时任务调度日志
   *
   * @return Boolean
   */
  @DeleteMapping("/clean")
  public Boolean clean() {
    return taskLogService.cleanTaskLog();
  }

  /**
   * 删除定时任务调度日志
   *
   * @param ids 主键
   * @return List<Boolean>
   */
  @DeleteMapping("/delete")
  public List<Boolean> remove(@RequestBody String[] ids) {
    return taskLogService.deleteTaskLogByIds(ids);
  }

  /**
   * 导出定时任务调度日志列表
   *
   * @param data 查询参数
   * @return String
   */
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    List<SysTaskLogDto> list = taskLogService.findList(data);

    String[] titleKeys = new String[] { "编号", "任务id", "任务名称", "任务组名", "目标字符串", "任务信息", "状态", "异常信息", "开始时间", "结束时间",
            "执行ip", "执行服务器名称", "备注", "创建人", "创建时间", "更新时间" };
    String[] columnNames = { "id", "taskId", "taskName", "taskGroup", "invokeTarget", "taskMessage", "status",
            "exceptionInfo", "startTime", "stopTime", "executeIp", "executeServerName", "remark", "createUser",
            "createTime", "updateTime" };

    String fileName = SysTaskLogDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }
}
