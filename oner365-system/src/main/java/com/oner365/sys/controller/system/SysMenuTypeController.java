package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.AttributeBean;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dto.SysMenuTypeDto;
import com.oner365.sys.service.ISysMenuTypeService;
import com.oner365.sys.vo.SysMenuTypeVo;
import com.oner365.sys.vo.check.CheckCodeVo;

/**
 * 菜单类型管理
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/menu/type")
public class SysMenuTypeController extends BaseController {

  @Autowired
  private ISysMenuTypeService menuTypeService;

  /**
   * 列表
   * 
   * @param data 参数
   * @return PageInfo<SysMenuTypeDto>
   */
  @PostMapping("/list")
  public PageInfo<SysMenuTypeDto> pageList(@RequestBody QueryCriteriaBean data) {
    return menuTypeService.pageList(data);
  }

  /**
   * 列表
   * 
   * @return List<SysMenuTypeDto>
   */
  @GetMapping("/all")
  public List<SysMenuTypeDto> findAll() {
    QueryCriteriaBean data = new QueryCriteriaBean();
    List<AttributeBean> whereList = new ArrayList<>();
    AttributeBean attribute = new AttributeBean(SysConstants.STATUS, StatusEnum.YES.getCode());
    whereList.add(attribute);
    data.setWhereList(whereList);
    return menuTypeService.findList(data);
  }

  /**
   * 获取信息
   * 
   * @param id 编号
   * @return SysMenuTypeDto
   */
  @GetMapping("/get/{id}")
  public SysMenuTypeDto get(@PathVariable String id) {
    return menuTypeService.getById(id);
  }

  /**
   * 修改状态
   * 
   * @param id     主键
   * @param status 状态
   * @return Integer
   */
  @PostMapping("/status/{id}")
  public Integer editStatusById(@PathVariable String id, @RequestParam("status") String status) {
    return menuTypeService.editStatusById(id, status);
  }

  /**
   * 判断是否存在
   * 
   * @param checkCodeVo 查询参数
   * @return Long
   */
  @PostMapping("/check")
  public Long checkCode(@RequestBody CheckCodeVo checkCodeVo) {
    if (checkCodeVo != null) {
      return menuTypeService.checkCode(checkCodeVo.getId(), checkCodeVo.getCode());
    }
    return Long.valueOf(ResultEnum.ERROR.getCode());
  }

  /**
   * 保存
   * 
   * @param sysMenuTypeVo 菜单类型对象
   * @return ResponseResult<SysMenuTypeDto>
   */
  @PutMapping("/save")
  public ResponseResult<SysMenuTypeDto> save(@RequestBody SysMenuTypeVo sysMenuTypeVo) {
    if (sysMenuTypeVo != null) {
      SysMenuTypeDto entity = menuTypeService.save(sysMenuTypeVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除
   * 
   * @param ids 编号
   * @return Integer
   */
  @DeleteMapping("/delete")
  public Integer delete(@RequestBody String... ids) {
    int code = 0;
    for (String id : ids) {
      code = menuTypeService.deleteById(id);
    }
    return code;
  }
}
