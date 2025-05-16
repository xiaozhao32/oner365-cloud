package com.oner365.elasticsearch.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.elasticsearch.dto.SampleLocationDto;
import com.oner365.elasticsearch.service.ISampleLocationElasticsearchService;
import com.oner365.elasticsearch.vo.SampleLocationVo;

/**
 * SampleLocation Controller
 *
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/sample/location")
public class SampleLocationController extends BaseController {

  @Resource
  private ISampleLocationElasticsearchService service;

  /**
   * 列表
   *
   * @param data 查询条件参数
   * @return PageInfo<SampleLocationDto>
   */
  @PostMapping("/list")
  public PageInfo<SampleLocationDto> pageList(@RequestBody QueryCriteriaBean data) {
    return this.service.pageList(data);
  }

  /**
   * id查询
   *
   * @param id 编号
   * @return SampleLocationDto
   */
  @GetMapping("/get/{id}")
  public SampleLocationDto get(@PathVariable String id) {
    return service.findById(id);
  }

  /**
   * 保存
   *
   * @param sampleLocationVo 坐标对象
   * @return SampleLocationDto
   */
  @PutMapping("/save")
  public SampleLocationDto save(@RequestBody SampleLocationVo sampleLocationVo) {
    return service.save(sampleLocationVo);
  }

  /**
   * 删除
   *
   * @param ids 编号
   * @return Integer
   */
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> service.deleteById(id)).collect(Collectors.toList());
  }

}
