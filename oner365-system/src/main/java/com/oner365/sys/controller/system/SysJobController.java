package com.oner365.sys.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.dto.SysJobDto;
import com.oner365.sys.service.ISysJobService;
import com.oner365.sys.vo.SysJobVo;

/**
 * 用户职位信息
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/job")
public class SysJobController extends BaseController {

  @Autowired
  private ISysJobService sysJobService;

  /**
   * 用户职位列表
   *
   * @param data 查询参数
   * @return PageInfo<SysJobDto>
   */
  @PostMapping("/list")
  public PageInfo<SysJobDto> list(@RequestBody QueryCriteriaBean data) {
    return sysJobService.pageList(data);
  }

  /**
   * 获取用户职位
   *
   * @param id 编号
   * @return SysJobDto
   */
  @GetMapping("/get/{id}")
  public SysJobDto get(@PathVariable String id) {
    return sysJobService.getById(id);
  }

  /**
   * 修改职位状态
   *
   * @param id     主键
   * @param status 状态
   * @return Integer
   */
  @PostMapping("/editStatus/{id}")
  public Integer editStatus(@PathVariable String id, @RequestParam("status") String status) {
    return sysJobService.editStatus(id, status);
  }

  /**
   * 用户职位保存
   *
   * @param sysJobVo 职位对象
   * @return ResponseResult<SysJobDto>
   */
  @PutMapping("/save")
  public ResponseResult<SysJobDto> save(@RequestBody SysJobVo sysJobVo) {
    if (sysJobVo != null) {
      SysJobDto entity = sysJobService.save(sysJobVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除用户职位
   *
   * @param ids 编号
   * @return Integer
   */
  @DeleteMapping("/delete")
  public Integer delete(@RequestBody String... ids) {
    int code = 0;
    for (String id : ids) {
      code = sysJobService.deleteById(id);
    }
    return code;
  }

  /**
   * 导出Excel
   *
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    List<SysJobDto> list = sysJobService.findList(data);

    String[] titleKeys = new String[] { "编号", "职位名称", "职位描述", "排序", "状态", "创建时间", "更新时间" };
    String[] columnNames = { "id", "jobName", "jobInfo", "jobOrder", "status", "createTime", "updateTime" };

    String fileName = SysJobDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }

}
