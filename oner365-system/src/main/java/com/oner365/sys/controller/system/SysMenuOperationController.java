package com.oner365.sys.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.dto.SysMenuOperationDto;
import com.oner365.sys.service.ISysMenuOperationService;
import com.oner365.sys.vo.SysMenuOperationVo;
import com.oner365.sys.vo.check.CheckCodeVo;

/**
 * 菜单操作权限
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/menuOperation")
public class SysMenuOperationController extends BaseController {

  @Autowired
  private ISysMenuOperationService menuOperationService;

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return PageInfo<SysMenuOperationDto>
   */
  @PostMapping("/list")
  public PageInfo<SysMenuOperationDto> findList(@RequestBody QueryCriteriaBean data) {
    return menuOperationService.pageList(data);
  }

  /**
   * 获取信息
   * 
   * @param id 编号
   * @return SysMenuOperationDto
   */
  @GetMapping("/get/{id}")
  public SysMenuOperationDto getById(@PathVariable String id) {
    return menuOperationService.getById(id);
  }

  /**
   * 判断是否存在
   * 
   * @param checkCodeVo 查询参数
   * @return Long
   */
  @PostMapping("/checkCode")
  public Long checkCode(@RequestBody CheckCodeVo checkCodeVo) {
    if (checkCodeVo != null) {
      return menuOperationService.checkCode(checkCodeVo.getId(), checkCodeVo.getCode());
    }
    return Long.valueOf(ResultEnum.ERROR.getCode());
  }

  /**
   * 保存
   * 
   * @param sysMenuOperationVo 操作对象
   * @return ResponseResult<SysMenuOperationDto>
   */
  @PutMapping("/save")
  public ResponseResult<SysMenuOperationDto> save(@RequestBody SysMenuOperationVo sysMenuOperationVo) {
    if (sysMenuOperationVo != null) {
      SysMenuOperationDto entity = menuOperationService.save(sysMenuOperationVo);
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
      code = menuOperationService.deleteById(id);
    }
    return code;
  }

}
