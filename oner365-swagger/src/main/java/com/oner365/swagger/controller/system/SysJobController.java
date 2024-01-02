package com.oner365.swagger.controller.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.swagger.client.system.ISystemJobClient;
import com.oner365.swagger.dto.SysJobDto;
import com.oner365.swagger.vo.SysJobVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户职位信息
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "系统管理 - 用户职位")
@RequestMapping("/system/job")
public class SysJobController {

  @Resource
  private ISystemJobClient client;

  /**
   * 用户职位列表
   *
   * @param data 查询参数
   * @return ResponseData<Page<SysJobDto>>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<PageInfo<SysJobDto>> list(@RequestBody QueryCriteriaBean data) {
    return client.list(data);
  }

  /**
   * 获取用户职位
   *
   * @param id 编号
   * @return ResponseData<SysJobDto>
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ResponseData<SysJobDto> get(@PathVariable String id) {
    return client.getById(id);
  }

  /**
   * 修改职位状态
   *
   * @param id     主键
   * @param status 状态
   * @return ResponseData<Boolean>
   */
  @ApiOperation("3.修改状态")
  @ApiOperationSupport(order = 3)
  @PostMapping("/status/{id}")
  public ResponseData<Boolean> editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return client.editStatus(id, status);
  }

  /**
   * 用户职位保存
   *
   * @param sysJobVo 职位对象
   * @return ResponseData<ResponseResult<SysJobDto>>
   */
  @ApiOperation("4.保存")
  @ApiOperationSupport(order = 4)
  @PutMapping("/save")
  public ResponseData<ResponseResult<SysJobDto>> save(@RequestBody SysJobVo sysJobVo) {
    return client.save(sysJobVo);
  }

  /**
   * 删除用户职位
   *
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @ApiOperation("5.删除")
  @ApiOperationSupport(order = 5)
  @DeleteMapping("/delete")
  public ResponseData<List<Boolean>> delete(@RequestBody String... ids) {
    return client.deleteById(ids);
  }

  /**
   * 导出Excel
   *
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("6.导出")
  @ApiOperationSupport(order = 6)
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data, HttpServletResponse response) {
    return client.export(data);
  }
}
