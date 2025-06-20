package com.oner365.generator.service;

import java.util.List;

import com.oner365.generator.entity.GenTableColumn;

/**
 * 业务字段 服务层
 *
 * @author zhaoyong
 */
public interface IGenTableColumnService {

    /**
     * 查询业务字段列表
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

    /**
     * 新增业务字段
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    Boolean insertGenTableColumn(GenTableColumn genTableColumn);

    /**
     * 修改业务字段
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    Boolean updateGenTableColumn(GenTableColumn genTableColumn);

    /**
     * 删除业务字段信息
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    Boolean deleteGenTableColumnByIds(String ids);

}
