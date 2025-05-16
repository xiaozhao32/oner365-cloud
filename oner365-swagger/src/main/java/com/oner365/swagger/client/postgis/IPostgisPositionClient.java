package com.oner365.swagger.client.postgis;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.PositionDto;
import com.oner365.swagger.vo.PositionVo;

/**
 * Postgis服务 - 消费
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_POSTGIS, contextId = PathConstants.CONTEXT_POSTGIS_POSITION_ID)
public interface IPostgisPositionClient {

  /**
   * 获取列表
   * 
   * @return ResponseData
   */
  @GetMapping(PathConstants.REQUEST_POSTGIS_POSITION_LIST)
  ResponseData<List<PositionDto>> findList();

  /**
   * 按主键查询
   * 
   * @param id 主键
   * @return ResponseData
   */
  @GetMapping(PathConstants.REQUEST_POSTGIS_POSITION_GET_ID)
  ResponseData<PositionDto> get(@PathVariable String id);

  /**
   * 保存
   * 
   * @param vo 保存对象
   * @return ResponseData
   */
  @PutMapping(PathConstants.REQUEST_POSTGIS_POSITION_SAVE)
  ResponseData<PositionDto> save(@RequestBody PositionVo vo);

  /**
   * 删除
   * 
   * @param ids 主键集合
   * @return ResponseData
   */
  @DeleteMapping(PathConstants.REQUEST_POSTGIS_POSITION_DELETE)
  ResponseData<List<Boolean>> delete(@RequestBody String... ids);
}
