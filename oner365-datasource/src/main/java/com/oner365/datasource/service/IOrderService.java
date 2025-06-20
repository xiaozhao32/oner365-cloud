package com.oner365.datasource.service;

import java.util.List;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.service.BaseService;
import com.oner365.datasource.dto.OrderDto;
import com.oner365.datasource.vo.OrderVo;

/**
 * 订单接口
 *
 * @author zhaoyong
 *
 */
public interface IOrderService extends BaseService {

    /**
     * 查询职位列表
     * @param data 查询参数
     * @return PageInfo
     */
    PageInfo<OrderDto> pageList(QueryCriteriaBean data);

    /**
     * 查询列表
     * @param data 查询参数
     * @return List
     */
    List<OrderDto> findList(QueryCriteriaBean data);

    /**
     * 查询职位详情
     * @param id 编号
     * @return OrderDto
     */
    OrderDto getById(String id);

    /**
     * 保存职位
     * @param vo 职位对象
     * @return OrderDto
     */
    OrderDto save(OrderVo vo);

    /**
     * 删除职位
     * @param id 编号
     * @return Boolean
     */
    Boolean deleteById(String id);

    /**
     * 更新状态
     * @param id 编号
     * @param status 状态
     * @return Boolean
     */
    Boolean editStatus(String id, StatusEnum status);

}
