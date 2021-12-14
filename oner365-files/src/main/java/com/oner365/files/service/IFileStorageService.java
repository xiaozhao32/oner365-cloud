package com.oner365.files.service;

import java.util.List;

import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.files.dto.SysFileStorageDto;
import com.oner365.files.vo.SysFileStorageVo;

/**
 * 文件接口
 * 
 * @author zhaoyong
 */
public interface IFileStorageService extends BaseService {

  /**
   * 查询文件列表
   *
   * @param data 参数
   * @return PageInfo
   */
  PageInfo<SysFileStorageDto> pageList(QueryCriteriaBean data);

  /**
   * 查询列表
   * 
   * @param data 参数
   * @return List
   */
  List<SysFileStorageDto> findList(QueryCriteriaBean data);

  /**
   * 查询文件详情
   *
   * @param id 编号
   * @return SysFileStorage
   */
  SysFileStorageDto getById(String id);

  /**
   * 保存文件
   *
   * @param entity 文件对象
   * @return SysFileStorage
   */
  SysFileStorageDto save(SysFileStorageVo entity);

  /**
   * 删除文件
   *
   * @param id 编号
   * @return int
   */
  int deleteById(String id);

}
