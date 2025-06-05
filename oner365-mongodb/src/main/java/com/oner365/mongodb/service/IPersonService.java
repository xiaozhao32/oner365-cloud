package com.oner365.mongodb.service;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.service.BaseService;
import com.oner365.mongodb.dto.PersonDto;
import com.oner365.mongodb.vo.PersonVo;

/**
 * Person Service
 *
 * @author zhaoyong
 */
public interface IPersonService extends BaseService {

    /**
     * Save
     * @param vo 实体对象
     * @return Person
     */
    PersonDto save(PersonVo vo);

    /**
     * Get
     * @param id 主键
     * @return Person
     */
    PersonDto getById(String id);

    /**
     * Page List
     * @param data 查询参数
     * @return PageInfo<PersonDto>
     */
    PageInfo<PersonDto> page(QueryCriteriaBean data);

    /**
     * Delete
     * @param id 主键
     * @return 是否成功
     */
    Boolean delete(String id);

}
