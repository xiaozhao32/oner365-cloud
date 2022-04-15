package com.oner365.neo4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseData;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
import com.oner365.neo4j.entity.ParentNode;
import com.oner365.neo4j.entity.SonNode;
import com.oner365.neo4j.service.ParentService;
import com.oner365.neo4j.service.SonService;

@RestController
@RequestMapping("/neo4j/test")
public class TestController extends BaseController {

  @Autowired
  private ParentService parentService;
  
  @Autowired
  private SonService sonService;

  /**
   * 保存父节点
   * 
   * @return ParentNode
   */
  @PostMapping("/parent/save")
  public ParentNode save(@RequestBody ParentNode parentNode) {
    return parentService.save(parentNode);
  }
  
  /**
   * 删除父节点
   * 
   * @param id 父节点
   */
  @DeleteMapping("/parent/delete/{id}")
  public ResponseData<ResultEnum> deleteParent(@PathVariable Long id) {
    boolean result = parentService.delete(id);
    if (result) {
      return ResponseData.success(ResultEnum.SUCCESS);
    }
    return ResponseData.error(ResultEnum.ERROR.name());
  }
  
  /**
   * 保存子节点
   * 
   * @return SonNode
   */
  @PostMapping("/son/save")
  public SonNode save(@RequestBody SonNode sonNode) {
    return sonService.save(sonNode);
  }

  /**
   * 删除子节点
   * 
   * @param id 子节点
   */
  @DeleteMapping("/son/delete/{id}")
  public ResponseData<ResultEnum> deleteSon(@PathVariable Long id) {
    boolean result = sonService.delete(id);
    if (result) {
      return ResponseData.success(ResultEnum.SUCCESS);
    }
    return ResponseData.error(ResultEnum.ERROR.name());
  }

}
