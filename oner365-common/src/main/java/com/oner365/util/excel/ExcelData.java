package com.oner365.util.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel数据对象
 * @author zhaoyong
 */
public class ExcelData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * Sheet名称
     */
    private String sheetName;

    /***
     * 表格列名
     */
    private String[] columnNames = new String[0];

    /***
     * 列表数据
     */
    private List<T> dataList = new ArrayList<>();

    /**
     * 构造方法
     */
    public ExcelData(){
        super();
    }

    /**
     * 构造方法
     * @param sheetName 名称
     */
    public ExcelData(String sheetName){
        this.sheetName = sheetName;
    }

    /**
     * @return the sheetName
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * @param sheetName the sheetName to set
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * @return the columnNames
     */
    public String[] getColumnNames() {
        return columnNames;
    }

    /**
     * @param columnNames the columnNames to set
     */
    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    /**
     * @return the dataList
     */
    public List<T> getDataList() {
        return dataList;
    }

    /**
     * @param dataList the dataList to set
     */
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

}
