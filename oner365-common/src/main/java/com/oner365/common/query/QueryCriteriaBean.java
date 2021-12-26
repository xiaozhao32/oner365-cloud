package com.oner365.common.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.oner365.common.constants.PublicConstants;

/**
 * 查询条件设置
 * 
 * @author zhaoyong
 */
public class QueryCriteriaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 页码
     */
    private Integer pageIndex = 1;

    /***
     * 页大小
     */
    private Integer pageSize = PublicConstants.PAGE_SIZE;

    /***
     * 查询条件
     */
    private List<AttributeBean> whereList = new ArrayList<>();

    /***
     * 排序条件
     */
    private AttributeBean order;

    public QueryCriteriaBean() {
        super();
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<AttributeBean> getWhereList() {
        return whereList;
    }

    public void setWhereList(List<AttributeBean> whereList) {
        this.whereList = whereList;
    }

    public AttributeBean getOrder() {
        return order;
    }

    public void setOrder(AttributeBean order) {
        this.order = order;
    }
}
