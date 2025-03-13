package com.oner365.elasticsearch.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.commons.util.GeneTransFormUtils;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.elasticsearch.dto.SampleGeneDto;
import com.oner365.elasticsearch.service.ISampleGeneElasticsearchService;
import com.oner365.elasticsearch.vo.SampleGeneVo;

/**
 * Elasticsearch Controller
 *
 * @author zhaoyong
 *
 */
@RefreshScope
@RestController
@RequestMapping("/sample/gene")
public class SampleGeneController extends BaseController {

  @Resource
  private ISampleGeneElasticsearchService service;

  /**
   * 列表
   *
   * @param data 查询条件参数
   * @return Page<SampleGeneDto>
   */
  @PostMapping("/list")
  public PageInfo<SampleGeneDto> pageList(@RequestBody QueryCriteriaBean data) {
    return service.pageList(data);
  }

  /**
   * id查询
   *
   * @param id 编号
   * @return SampleGeneDto
   */
  @GetMapping("/get/{id}")
  public SampleGeneDto get(@PathVariable("id") String id) {
    SampleGeneDto sampleGene = service.findById(id);
    if (sampleGene != null && !DataUtils.isEmpty(sampleGene.getGeneInfo())) {
      // 基因型格式转换
      sampleGene.setGeneList(GeneTransFormUtils.geneFormatList(sampleGene.getGeneInfo().toJSONString()));
    }
    return sampleGene;
  }

  /**
   * 保存
   *
   * @param sampleGeneVo 基因对象
   * @return SampleGeneDto
   */
  @PutMapping("/save")
  public SampleGeneDto save(@RequestBody SampleGeneVo sampleGeneVo) {
    if (!sampleGeneVo.getGeneList().isEmpty()) {
      // 基因型格式转换
      String jsonArray = JSON.toJSONString(sampleGeneVo.getGeneList());
      sampleGeneVo.setGeneInfo(GeneTransFormUtils.geneFormatString(jsonArray));
      String s = GeneTransFormUtils.geneTrimString(sampleGeneVo.getGeneInfo().toJSONString());
      sampleGeneVo.setMatchJson(JSON.parseObject(s));
    }
    return service.save(sampleGeneVo);
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
