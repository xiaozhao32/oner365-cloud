package com.oner365.neo4j.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.web.controller.BaseController;
import com.oner365.neo4j.entity.ParentNode;
import com.oner365.neo4j.service.ParentService;

/**
 * neo4j test
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

  @Resource
  private ParentService parentService;
  
  /**
   * 保存人员节点
   * 
   * @return ParentNode
   */
  @PostMapping("/parent/save")
  public ParentNode save(@RequestBody ParentNode parentNode) {
    return parentService.save(parentNode);
  }
  
  /**
   * 删除当前人员节点
   * 
   * @param id 人员节点
   */
  @DeleteMapping("/parent/delete/{id}")
  public ResponseData<ResultEnum> deleteParent(@PathVariable Long id) {
    boolean result = parentService.delete(id);
    if (result) {
      return ResponseData.success(ResultEnum.SUCCESS);
    }
    return ResponseData.error(ResultEnum.ERROR.name());
  }
  
}
