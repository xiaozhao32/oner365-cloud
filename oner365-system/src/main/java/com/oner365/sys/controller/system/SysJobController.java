package com.oner365.sys.controller.system;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.enums.ErrorInfoEnum;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
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

  @Resource
  private ISysJobService sysJobService;

  /**
   * 用户职位列表
   *
   * @param data 查询参数
   * @return PageInfo<SysJobDto>
   */
  @PostMapping("/list")
  public PageInfo<SysJobDto> pageList(@RequestBody QueryCriteriaBean data) {
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
   * @return Boolean
   */
  @PostMapping("/status/{id}")
  public Boolean editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return sysJobService.editStatus(id, status);
  }

  /**
   * 用户职位保存
   *
   * @param sysJobVo 职位对象
   * @return ResponseResult<SysJobDto>
   */
  @PutMapping("/save")
  public ResponseResult<SysJobDto> save(@Validated @RequestBody SysJobVo sysJobVo) {
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
   * @return List<Boolean>
   */
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> sysJobService.deleteById(id)).collect(Collectors.toList());
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
