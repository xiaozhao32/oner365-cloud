package com.oner365.swagger.controller.postgis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.client.postgis.IPostgisPositionClient;
import com.oner365.swagger.dto.PositionDto;
import com.oner365.swagger.vo.PositionVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Postgis - 位置服务
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "Postgis - Position")
@RequestMapping("/postgis/position")
public class PostgisPositionController {

  @Resource
  private IPostgisPositionClient client;

  /**
   * 集合列表
   * 
   * @return 集合
   */
  @ApiOperation("1.集合列表")
  @ApiOperationSupport(order = 1)
  @GetMapping("/list")
  public ResponseData<List<PositionDto>> findList() {
    return client.findList();
  }

  /**
   * 保存
   * 
   * @return ResponseData
   */
  @ApiOperation("2.保存")
  @ApiOperationSupport(order = 2)
  @PutMapping("/save")
  public ResponseData<PositionDto> save(@RequestBody PositionVo vo) {
    return client.save(vo);
  }

  /**
   * 按主键查询
   * 
   * @param id 主键
   * @return ResponseData
   */
  @ApiOperation("3.主键查询")
  @ApiOperationSupport(order = 3)
  @GetMapping("/get/{id}")
  public ResponseData<PositionDto> get(@PathVariable String id) {
    return client.get(id);
  }

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @ApiOperation("4.删除")
  @ApiOperationSupport(order = 4)
  @DeleteMapping("/delete")
  public ResponseData<List<Boolean>> delete(@RequestBody String... ids) {
    return client.delete(ids);
  }
}
