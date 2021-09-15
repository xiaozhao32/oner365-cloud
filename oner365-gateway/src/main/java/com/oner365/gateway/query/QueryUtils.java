package com.oner365.gateway.query;

import java.util.Arrays;

import org.springframework.data.domain.PageRequest;

import com.oner365.gateway.query.Criterion.Operator;
import com.oner365.gateway.util.DataUtils;

/**
 * 查询工具类
 * 
 * @author zhaoyong
 */
public class QueryUtils {

    private QueryUtils() {

    }

    /**
     * 构造分页与排序内容
     * 
     * @param data 数据
     * @return PageRequest
     */
    public static PageRequest buildPageRequest(QueryCriteriaBean data) {
        Integer pageNum = 1;
        Integer pageSize = Integer.MAX_VALUE;
        if (data != null) {
            pageNum = data.getPageIndex() == null ? pageNum : data.getPageIndex();
            pageSize = data.getPageSize() == null ? pageSize : data.getPageSize();
        }
        return PageRequest.of(pageNum - 1, pageSize);
    }

    /**
     * 构造查询条件
     *
     * @param data 数据
     * @return Criteria
     */
    public static <T> Criteria<T> buildCriteria(QueryCriteriaBean data) {
        Criteria<T> criteria = new Criteria<>();
        if (data != null && data.getWhereList() != null && !data.getWhereList().isEmpty()) {
            for (AttributeBean attr : data.getWhereList()) {
                String key = attr.getKey();
                String opt = !DataUtils.isEmpty(attr.getOpt()) ? attr.getOpt().toUpperCase() : Operator.EQ.name();
                Operator operator = Criterion.Operator.valueOf(opt);
                String value = attr.getVal();
                if (DataUtils.isEmpty(key) || DataUtils.isEmpty(value)) {
                    continue;
                }
                switch (operator) {
                case LIKE:
                    criteria.add(Restrictions.like(key, value));
                    break;
                case NE:
                    criteria.add(Restrictions.ne(key, value));
                    break;
                case GT:
                    criteria.add(Restrictions.gt(key, value));
                    break;
                case LT:
                    criteria.add(Restrictions.lt(key, value));
                    break;
                case LTE:
                    criteria.add(Restrictions.lte(key, value));
                    break;
                case GTE:
                    criteria.add(Restrictions.gte(key, value));
                    break;
                case IN:
                    // 格式: 条件1,条件2
                    if (value.length() > 1) {
                        criteria.add(Restrictions.in(key, Arrays.asList(value.split(",")), true));
                    }
                    break;
                case BE:
                    // 格式: 开始条件|结束条件
                    if (value.length() > 1) {
                        criteria.add(Restrictions.between(key, value));
                    }
                    break;
                default:
                    criteria.add(Restrictions.eq(key, value));
                    break;
                }
            }
        }
        return criteria;
    }

}
