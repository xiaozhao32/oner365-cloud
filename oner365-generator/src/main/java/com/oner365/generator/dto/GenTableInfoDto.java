package com.oner365.generator.dto;

import java.io.Serializable;
import java.util.List;

import com.oner365.generator.entity.GenTable;
import com.oner365.generator.entity.GenTableColumn;

/**
 * 生成实体信息
 *
 * @author zhaoyong
 *
 */
public class GenTableInfoDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 表对象
     */
    private GenTable table;

    /**
     * 列对象集合
     */
    private List<GenTableColumn> columnList;

    /**
     * 构造方法
     */
    public GenTableInfoDto() {
        super();
    }

    /**
     * 构造方法
     */
    public GenTableInfoDto(GenTable table, List<GenTableColumn> columnList) {
        this.table = table;
        this.columnList = columnList;
    }

    /**
     * @return the table
     */
    public GenTable getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(GenTable table) {
        this.table = table;
    }

    /**
     * @return the columnList
     */
    public List<GenTableColumn> getColumnList() {
        return columnList;
    }

    /**
     * @param columnList the columnList to set
     */
    public void setColumnList(List<GenTableColumn> columnList) {
        this.columnList = columnList;
    }

}
