package com.oner365.generator.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oner365.data.commons.util.ConvertString;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.generator.entity.GenTableColumn;
import com.oner365.generator.mapper.GenTableColumnMapper;
import com.oner365.generator.service.IGenTableColumnService;

/**
 * 业务字段 服务层实现
 * 
 * @author zhaoyong
 */
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService {
    @Resource
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * 查询业务字段列表
     * 
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
        return genTableColumnMapper.selectGenTableColumnListByTableId(tableId);
    }

    /**
     * 新增业务字段
     * 
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Override
    public Boolean insertGenTableColumn(GenTableColumn genTableColumn) {
        genTableColumn.setCreateTime(DateUtil.getDate());
        int result = genTableColumnMapper.insertGenTableColumn(genTableColumn);
        if (result > 0) {
          return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 修改业务字段
     * 
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Override
    public Boolean updateGenTableColumn(GenTableColumn genTableColumn) {
        genTableColumn.setUpdateTime(DateUtil.getDate());
        int result = genTableColumnMapper.updateGenTableColumn(genTableColumn);
        if (result > 0) {
          return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 删除业务字段对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public Boolean deleteGenTableColumnByIds(String ids) {
        int result = genTableColumnMapper.deleteGenTableColumnByIds(ConvertString.toLongArray(ids));
        if (result > 0) {
          return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
