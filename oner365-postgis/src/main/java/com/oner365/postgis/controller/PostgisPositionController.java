package com.oner365.postgis.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.web.controller.BaseController;
import com.oner365.postgis.dto.PositionDto;
import com.oner365.postgis.service.IPositionService;
import com.oner365.postgis.vo.PositionVo;

/**
 * Position Controller
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/position")
public class PostgisPositionController extends BaseController {

  @Resource
  private IPositionService service;

  /**
   * 集合列表
   *
   * @return 集合
   */
  @GetMapping("/list")
  public List<PositionDto> findList() {
    return service.findList();
  }

  /**
   * 保存
   *
   * @return ResponseData
   */
  @PutMapping("/save")
  public ResponseData<PositionDto> save(@RequestBody PositionVo vo) {
    PositionDto result = service.save(vo);
    return ResponseData.success(result);
  }

  /**
   * 按主键查询
   *
   * @param id 主键
   * @return ResponseData
   */
  @GetMapping("/get/{id}")
  public ResponseData<PositionDto> get(@PathVariable String id) {
    PositionDto result = service.getById(id);
    return ResponseData.success(result);
  }

  /**
   * 删除
   *
   * @param ids 编号
   * @return List<Boolean>
   */
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> service.deleteById(id)).collect(Collectors.toList());
  }

}
