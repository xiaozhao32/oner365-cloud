package com.oner365.generator.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oner365.generator.entity.GenTable;

/**
 * 业务 数据层
 *
 * @author zhaoyong
 */
@Repository
public interface GenTableMapper {

    /**
     * 查询业务列表
     * @param genTable 业务信息
     * @return 业务集合
     */
    List<GenTable> selectGenTableList(GenTable genTable);

    /**
     * 查询据库列表
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    List<GenTable> selectDbTableList(GenTable genTable);

    /**
     * 查询据库列表
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * 查询表ID业务信息
     * @param id 业务ID
     * @return 业务信息
     */
    GenTable selectGenTableById(Long id);

    /**
     * 查询表名称业务信息
     * @param tableName 表名称
     * @return 业务信息
     */
    GenTable selectGenTableByName(String tableName);

    /**
     * 新增业务
     * @param genTable 业务信息
     * @return 结果
     */
    int insertGenTable(GenTable genTable);

    /**
     * 修改业务
     * @param genTable 业务信息
     * @return 结果
     */
    int updateGenTable(GenTable genTable);

    /**
     * 批量删除业务
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteGenTableByIds(Long[] ids);

}
