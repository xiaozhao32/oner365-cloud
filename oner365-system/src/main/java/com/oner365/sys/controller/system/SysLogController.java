package com.oner365.sys.controller.system;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.sys.dto.SysLogDto;
import com.oner365.sys.service.ISysLogService;
import com.oner365.sys.vo.SysLogVo;

/**
 * 系统日志控制器
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/log")
public class SysLogController extends BaseController {

  @Resource
  private ISysLogService logService;

  /**
   * 列表
   *
   * @param data 查询参数
   * @return PageInfo<SysLog>
   */
  @PostMapping("/list")
  public PageInfo<SysLogDto> pageList(@RequestBody QueryCriteriaBean data) {
    return logService.pageList(data);
  }

  /**
   * 获取信息
   *
   * @param id 编号
   * @return SysLog
   */
  @GetMapping("/get/{id}")
  public SysLogDto get(@PathVariable String id) {
    return logService.getById(id);
  }

  /**
   * 保存
   *
   * @param sysLogVo 菜单类型对象
   * @return Boolean
   */
  @PutMapping("/save")
  public Boolean save(@RequestBody SysLogVo sysLogVo) {
    if (sysLogVo != null) {
      logService.save(sysLogVo);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  /**
   * 删除
   *
   * @param ids 编号
   * @return List<Boolean>
   */
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> logService.deleteById(id)).collect(Collectors.toList());
  }

  /**
   * 按日期删除日志
   *
   * @param days 天数
   * @return Boolean
   */
  @DeleteMapping("/days/delete")
  public Boolean deleteLog(@RequestParam Integer days) {
    Date date = DateUtil.getDateAgo(days);
    return logService.deleteLog(DateUtil.dateToLocalDateTime(date));
  }

  /**
   * 导出日志
   *
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @PostMapping("/export")
  public ResponseEntity<byte[]> exportItem(@RequestBody QueryCriteriaBean data) {
    List<SysLogDto> list = logService.findList(data);

    String[] titleKeys = new String[] { "编号", "请求IP", "请求方法", "服务名称", "请求地址", "请求内容", "创建时间" };
    String[] columnNames = { "id", "operationIp", "methodName", "operationName", "operationPath", "operationContext",
        "createTime" };

    String fileName = SysLogDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }

}
