package com.oner365.swagger.client.mongodb;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.PersonDto;
import com.oner365.swagger.vo.PersonVo;

/**
 * Mongodb服务 - 用户
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_MONGODB, contextId = PathConstants.CONTEXT_MONGODB_PERSON_ID)
public interface IMongodbPersonClient {

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return ResponseData<PageInfo<PersonDto>>
   */
  @PostMapping(PathConstants.REQUEST_MONGODB_PERSON_PAGE)
  ResponseData<PageInfo<PersonDto>> page(@RequestBody QueryCriteriaBean data);

  /**
   * 按id获取查询
   * 
   * @param id 编号
   * @return ResponseData<PersonDto>
   */
  @GetMapping(PathConstants.REQUEST_MONGODB_PERSON_GET_ID)
  ResponseData<PersonDto> getById(@PathVariable(value = "id") String id);

  /**
   * 保存
   * 
   * @param personVo 保存对象
   * @return ResponseData<PersonDto>
   */
  @PutMapping(PathConstants.REQUEST_MONGODB_PERSON_SAVE)
  ResponseData<PersonDto> save(@RequestBody PersonVo personVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @DeleteMapping(PathConstants.REQUEST_MONGODB_PERSON_DELETE)
  ResponseData<List<Boolean>> deleteById(@RequestBody String... ids);
}
