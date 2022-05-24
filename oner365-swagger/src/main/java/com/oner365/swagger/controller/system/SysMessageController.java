package com.oner365.swagger.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.system.ISystemMessageClient;
import com.oner365.swagger.dto.SysMessageDto;
import com.oner365.swagger.enums.MessageTypeEnum;
import com.oner365.swagger.vo.SysMessageVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 消息通知
 * 
 * @author zhaoyong
 */
@RestController
@Api(tags = "系统管理 - 消息")
@RequestMapping("/system/message")
public class SysMessageController extends BaseController {
  
  @Autowired
  private ISystemMessageClient client;
  
  /**
   * 查询结果 有返回 true 并且删除
   * 
   * @param messageType 消息类型
   * @return ResponseData<ResponseResult<Boolean>>
   */
  @ApiOperation("1.刷新结果")
  @ApiOperationSupport(order = 1)
  @GetMapping("/refresh")
  public ResponseData<ResponseResult<Boolean>> refresh(@RequestParam("messageType") MessageTypeEnum messageType) {
    return client.refresh(messageType);
  }
  
  /**
   * 列表
   *
   * @param data 参数
   * @return ResponseData<PageInfo<SysMessageDto>>
   */
  @ApiOperation("2.获取分页列表")
  @ApiOperationSupport(order = 2)
  @PostMapping("/list")
  public ResponseData<PageInfo<SysMessageDto>> list(@RequestBody QueryCriteriaBean data) {
    return client.list(data);
  }
  
  /**
   * 获取信息
   *
   * @param id 编号
   * @return ResponseData<SysMessageDto>
   */
  @ApiOperation("3.按id查询")
  @ApiOperationSupport(order = 3)
  @GetMapping("/get/{id}")
  public ResponseData<SysMessageDto> get(@PathVariable String id) {
    return client.getById(id);
  }
  
  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return ResponseData<Boolean>
   */
  @ApiOperation("4.修改状态")
  @ApiOperationSupport(order = 4)
  @PostMapping("/status/{id}")
  public ResponseData<Boolean> editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return client.editStatus(id, status);
  }
  
  /**
   * 保存
   *
   * @param sysMessageVo 消息对象
   * @return ResponseData<ResponseResult<SysMessageDto>>
   */
  @ApiOperation("5.保存")
  @ApiOperationSupport(order = 5)
  @PutMapping("/save")
  public ResponseData<ResponseResult<SysMessageDto>> save(@RequestBody SysMessageVo sysMessageVo) {
    return client.save(sysMessageVo);
  }
  
  /**
   * 删除
   *
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @ApiOperation("6.删除")
  @ApiOperationSupport(order = 6)
  @DeleteMapping("/delete")
  public ResponseData<List<Boolean>> delete(@RequestBody String... ids) {
    return client.delete(ids);
  }
  
  /**
   * 导出Excel
   *
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("7.导出")
  @ApiOperationSupport(order = 7)
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data, HttpServletResponse response) {
    return client.export(data);
  }

}
