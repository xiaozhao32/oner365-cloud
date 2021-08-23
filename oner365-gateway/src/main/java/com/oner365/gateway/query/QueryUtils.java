package com.oner365.gateway.query;

import org.springframework.data.domain.PageRequest;

/**
 * 查询工具类
 * @author zhaoyong
 */
public class QueryUtils {

    private QueryUtils() {

    }

    /**
     * 构造分页与排序内容
     * @param data 数据
     * @return PageRequest
     */
    public static PageRequest buildPageRequest(QueryCriteriaBean data) {
        Integer pageNum = 1;
        Integer pageSize = Integer.MAX_VALUE;
        if(data!=null){
            pageNum = data.getPageIndex()==null?pageNum:data.getPageIndex();
            pageSize = data.getPageSize()==null?pageSize:data.getPageSize();
        }
        return PageRequest.of(pageNum - 1, pageSize);
    }

}
