package com.oner365.sys.service;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.service.BaseService;
import com.oner365.sys.dto.DataSourceConfigDto;
import com.oner365.sys.vo.DataSourceConfigVo;

/**
 * 数据源信息
 *
 * @author zhaoyong
 */
public interface IDataSourceConfigService extends BaseService {

    /**
     * 查询分页列表
     * @param data 查询参数
     * @return PageInfo
     */
    PageInfo<DataSourceConfigDto> pageList(QueryCriteriaBean data);

    /**
     * 查询详情
     * @param id 编号
     * @return DataSourceConfigDto
     */
    DataSourceConfigDto getById(String id);

    /**
     * 获取连接名称
     * @param connectName 连接名称
     * @return DataSourceConfigDto
     */
    DataSourceConfigDto getConnectName(String connectName);

    /**
     * 保存
     * @param entity 对象
     * @return DataSourceConfigDto
     */
    DataSourceConfigDto save(DataSourceConfigVo entity);

    /**
     * 删除
     * @param id 编号
     * @return Boolean
     */
    Boolean deleteById(String id);

}
