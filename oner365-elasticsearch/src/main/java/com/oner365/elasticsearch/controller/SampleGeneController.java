package com.oner365.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.elasticsearch.dto.SampleGeneDto;
import com.oner365.elasticsearch.service.ISampleGeneElasticsearchService;
import com.oner365.elasticsearch.vo.SampleGeneVo;
import com.oner365.util.DataUtils;
import com.oner365.util.GeneTransFormUtils;

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

  @Autowired
  private ISampleGeneElasticsearchService service;

  /**
   * 列表
   *
   * @param data 查询条件参数
   * @return Page<SampleGeneDto>
   */
  @PostMapping("/list")
  public PageInfo<SampleGeneDto> list(@RequestBody QueryCriteriaBean data) {
    return service.findList(data);
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
   * @return ResponseResult<SampleGeneDto>
   */
  @PutMapping("/save")
  public ResponseResult<SampleGeneDto> save(@RequestBody SampleGeneVo sampleGeneVo) {
    if (sampleGeneVo == null) {
      return ResponseResult.error("基因对象为空!");
    }
    if (!sampleGeneVo.getGeneList().isEmpty()) {
      // 基因型格式转换
      String jsonArray = JSON.toJSONString(sampleGeneVo.getGeneList());
      sampleGeneVo.setGeneInfo(GeneTransFormUtils.geneFormatString(jsonArray));
      String s = GeneTransFormUtils.geneTrimString(sampleGeneVo.getGeneInfo().toJSONString());
      sampleGeneVo.setMatchJson(JSON.parseObject(s));
    }
    SampleGeneDto entity = service.save(sampleGeneVo);
    return ResponseResult.success(entity);
  }

  /**
   * 删除
   *
   * @param ids 编号
   * @return Integer
   */
  @DeleteMapping("/delete")
  public Integer delete(@RequestBody String... ids) {
    Integer result = ResultEnum.SUCCESS.getCode();
    for (String id : ids) {
      service.deleteById(id);
    }
    return result;
  }

}
