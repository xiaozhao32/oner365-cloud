package com.oner365.common.query;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.oner365.util.DataUtils;


/**
 * 查询工具类
 * @author zhaoyong
 */
public class QueryUtils {

    public static final String PARAM_ORDER_ASC = "ASC";
    public static final String PARAM_ORDER_DESC = "DESC";

    /***
     * EQ, NE, LIKE, GT, LT, GTE, LTE
     */
    public static final String PARAM_QUERY_EQ = "EQ";
    public static final String PARAM_QUERY_LIKE = "LIKE";
    public static final String PARAM_QUERY_NE = "NE";
    public static final String PARAM_QUERY_GT = "GT";
    public static final String PARAM_QUERY_LT = "LT";
    public static final String PARAM_QUERY_GTE = "GTE";
    public static final String PARAM_QUERY_LTE = "LTE";
    public static final String PARAM_QUERY_IN = "IN";
    public static final String PARAM_QUERY_BE = "BE";
    public static final String PARAM_QUERY_AND = " and ";

    private QueryUtils() {

    }

    /***
     * 构造查询条件
     *
     * @param data 数据
     * @return Criteria
     */
    public static <T> Criteria<T> buildCriteria(QueryCriteriaBean data) {
        Criteria<T> criteria = new Criteria<>();
        if (data!=null && data.getWhereList() != null && !data.getWhereList().isEmpty()) {
            List<AttributeBean> whereList = data.getWhereList();
            for (AttributeBean attr : whereList) {
                String key = attr.getKey();
                String opt = !DataUtils.isEmpty(attr.getOpt()) ? attr.getOpt().toUpperCase() : PARAM_QUERY_EQ;
                String value = attr.getVal();
                if (DataUtils.isEmpty(key) || DataUtils.isEmpty(value)) {
                    continue;
                }
                switch (opt) {
                    case PARAM_QUERY_LIKE:
                        criteria.add(Restrictions.like(key, value));
                        break;
                    case PARAM_QUERY_NE:
                        criteria.add(Restrictions.ne(key, value));
                        break;
                    case PARAM_QUERY_GT:
                        criteria.add(Restrictions.gt(key, value));
                        break;
                    case PARAM_QUERY_LT:
                        criteria.add(Restrictions.lt(key, value));
                        break;
                    case PARAM_QUERY_LTE:
                        criteria.add(Restrictions.lte(key, value));
                        break;
                    case PARAM_QUERY_GTE:
                        criteria.add(Restrictions.gte(key, value));
                        break;
                    case PARAM_QUERY_IN:
                        criteria.add(Restrictions.in(key, Arrays.asList(value.split(",")), true));
                        break;
                    case PARAM_QUERY_BE:
                        criteria.add(Restrictions.between(key, value));
                        break;
                    default:
                        criteria.add(Restrictions.eq(key, value));
                        break;
                }
            }
        }
        return criteria;
    }

    /**
     * 构造分页与排序内容
     * @param data 数据
     * @return PageRequest
     */
    public static PageRequest buildPageRequest(QueryCriteriaBean data) {
        Sort sort = null;
        Integer pageNum = 1;
        Integer pageSize = Integer.MAX_VALUE;
        if(data!=null){
            AttributeBean order = data.getOrder();
            if (!DataUtils.isEmpty(order) && !DataUtils.isEmpty(order.getKey())) {
                if(PARAM_ORDER_DESC.equalsIgnoreCase(order.getVal())){
                    sort = Sort.by(Direction.DESC, order.getKey().split(","));
                }else{
                    sort = Sort.by(Direction.ASC, order.getKey().split(","));
                }
            }
            pageNum = data.getPageIndex()==null?pageNum:data.getPageIndex();
            pageSize = data.getPageSize()==null?pageSize:data.getPageSize();
        }
        if(sort == null){
            return PageRequest.of(pageNum - 1, pageSize);
        }else{
            return PageRequest.of(pageNum - 1, pageSize, sort);
        }
    }


    /***
     * 构造查询条件字符串
     * @param whereList 条件
     * @return String
     */
    public static String buildWhereCondition(List<AttributeBean> whereList) {
        StringBuilder sb = new StringBuilder();
        for (AttributeBean attr : whereList) {
            String key = attr.getKey();
            String value = attr.getVal();
            String opt = attr.getOpt();
            if (DataUtils.isEmpty(key) || DataUtils.isEmpty(value)) {
                continue;
            }
            if (PARAM_QUERY_LIKE.equals(opt)) {
                sb.append(PARAM_QUERY_AND).append(key).append(" like '%").append(value).append("%'");
            } else if (PARAM_QUERY_NE.equals(opt)) {
                sb.append(PARAM_QUERY_AND).append(key).append(" != '").append(value).append("'");
            } else if (PARAM_QUERY_GT.equals(opt)) {
                sb.append(PARAM_QUERY_AND).append(key).append(" > '").append(value).append("'");
            } else if (PARAM_QUERY_LT.equals(opt)) {
                sb.append(PARAM_QUERY_AND).append(key).append(" < '").append(value).append("'");
            } else if (PARAM_QUERY_LTE.equals(opt)) {
                sb.append(PARAM_QUERY_AND).append(key).append(" <= '").append(value).append("'");
            } else if (PARAM_QUERY_GTE.equals(opt)) {
                sb.append(PARAM_QUERY_AND).append(key).append(" >= '").append(value).append("'");
            } else if (PARAM_QUERY_IN.equals(opt)) {
                String inValStr = getInCondition(Arrays.asList(value.split(",")));
                sb.append(PARAM_QUERY_AND).append(key).append(" in (").append(inValStr).append(")");
            } else {
                sb.append(PARAM_QUERY_AND).append(key).append(" = '").append(value).append("'");
            }
        }
        return sb.toString();
    }

    /***
     * 把list<String>转换成in查询str
     *
     * @param list ['val1','val2','val3']
     * @return  'val1','val2','val3'
     */
    public static String getInCondition(List<String> list) {
        String result = null;
        if (!DataUtils.isEmpty(list)) {
            StringBuilder sb = new StringBuilder();
            for (String str : list) {
                sb.append("'").append(str).append("',");
            }
            result = sb.toString();
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}
