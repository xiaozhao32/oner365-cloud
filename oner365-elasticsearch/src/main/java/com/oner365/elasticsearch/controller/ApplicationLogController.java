package com.oner365.elasticsearch.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.elasticsearch.dto.ApplicationLogDto;
import com.oner365.elasticsearch.service.IApplicationLogElasticsearchService;

/**
 * Elasticsearch Controller
 *
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/application/log")
public class ApplicationLogController extends BaseController {

  @Autowired
  private IApplicationLogElasticsearchService service;

  /**
   * 列表
   *
   * @param data 查询条件参数
   * @return PageInfo<ApplicationLogDto>
   */
  @PostMapping("/list")
  public PageInfo<ApplicationLogDto> pageList(@RequestBody QueryCriteriaBean data) {
    return this.service.pageList(data);
  }

  /**
   * id查询
   *
   * @param id 编号
   * @return ApplicationLogDto
   */
  @GetMapping("/get/{id}")
  public ApplicationLogDto get(@PathVariable("id") String id) {
    return service.findById(id);
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
