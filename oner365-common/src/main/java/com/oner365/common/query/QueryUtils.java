package com.oner365.common.query;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.google.common.base.Joiner;
import com.oner365.common.query.Criterion.Operator;
import com.oner365.util.DataUtils;

/**
 * 查询工具类
 *
 * @author zhaoyong
 */
public class QueryUtils {

  /** 升序 */
  public static final String PARAM_ORDER_ASC = "ASC";
  /** 降序 */
  public static final String PARAM_ORDER_DESC = "DESC";
  /** 和 */
  public static final String PARAM_QUERY_AND = " and ";

  private QueryUtils() {

  }

  /**
   * 构造查询条件
   *
   * @param data 数据
   * @return Criteria
   */
  public static <T> Criteria<T> buildCriteria(QueryCriteriaBean data) {
    Criteria<T> criteria = new Criteria<>();
    if (data == null || DataUtils.isEmpty(data.getWhereList())) {
      return criteria;
    }
    data.getWhereList().forEach(attr -> {
      String key = attr.getKey();
      String opt = !DataUtils.isEmpty(attr.getOpt()) ? attr.getOpt().toUpperCase() : Operator.EQ.name();
      Operator operator = Operator.valueOf(opt);
      String value = attr.getVal();
      if (DataUtils.isEmpty(key) || DataUtils.isEmpty(value)) {
        return;
      }
      getOperator(criteria, operator, key, value);
    });
    return criteria;
  }

  private static <T> void getOperator(Criteria<T> criteria, Operator operator, String key, String value) {
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

  /**
   * 构造分页内容
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

      Sort sort = buildSortRequest(data.getOrder());
      if (sort != null) {
        return PageRequest.of(pageNum - 1, pageSize, sort);
      }
    }
    return PageRequest.of(pageNum - 1, pageSize);
  }

  /**
   * 构造排序内容
   *
   * @param order 数据
   * @return Sort
   */
  public static Sort buildSortRequest(AttributeBean order) {
    Sort sort = null;
    if (!DataUtils.isEmpty(order) && !DataUtils.isEmpty(order.getKey())) {
      if (PARAM_ORDER_DESC.equalsIgnoreCase(order.getVal())) {
        sort = Sort.by(Direction.DESC, order.getKey().split(","));
      } else {
        sort = Sort.by(Direction.ASC, order.getKey().split(","));
      }
    }
    return sort;
  }

  /**
   * 构造查询条件字符串
   *
   * @param whereList 条件
   * @return String
   */
  public static String buildWhereCriterion(List<AttributeBean> whereList) {
    StringBuilder sb = new StringBuilder();
    whereList.forEach(attr -> {
      String key = attr.getKey();
      String value = attr.getVal();
      String opt = attr.getOpt();
      if (DataUtils.isEmpty(key) || DataUtils.isEmpty(value)) {
        return;
      }
      getCriterion(sb, key, opt, value);
    });
    return sb.toString();
  }

  private static void getCriterion(StringBuilder sb, String key, String opt, String value) {
    if (Operator.LIKE.name().equals(opt)) {
      sb.append(PARAM_QUERY_AND).append(key).append(" like '%").append(value).append("%'");
    } else if (Operator.NE.name().equals(opt)) {
      sb.append(PARAM_QUERY_AND).append(key).append(" != '").append(value).append("'");
    } else if (Operator.GT.name().equals(opt)) {
      sb.append(PARAM_QUERY_AND).append(key).append(" > '").append(value).append("'");
    } else if (Operator.LT.name().equals(opt)) {
      sb.append(PARAM_QUERY_AND).append(key).append(" < '").append(value).append("'");
    } else if (Operator.LTE.name().equals(opt)) {
      sb.append(PARAM_QUERY_AND).append(key).append(" <= '").append(value).append("'");
    } else if (Operator.GTE.name().equals(opt)) {
      sb.append(PARAM_QUERY_AND).append(key).append(" >= '").append(value).append("'");
    } else if (Operator.IN.name().equals(opt)) {
      // 格式: 条件1,条件2
      if (value.length() > 1) {
        String valueString = getInCriterion(Arrays.asList(value.split(",")));
        sb.append(PARAM_QUERY_AND).append(key).append(" in (").append(valueString).append(")");
      }
    } else if (Operator.BE.name().equals(opt)) {
      // 格式: 开始条件|结束条件
      if (value.length() > 1) {
        String[] array = StringUtils.split(value, "|");
        sb.append(PARAM_QUERY_AND).append(key).append(" between '").append(array[0]).append("' and '").append(array[1])
            .append("'");
      }
    } else {
      sb.append(PARAM_QUERY_AND).append(key).append(" = '").append(value).append("'");
    }
  }

  /**
   * 把list<String>转换成in查询str
   *
   * @param list ['val1','val2','val3']
   * @return 'val1','val2','val3'
   */
  public static String getInCriterion(List<String> list) {
    String result = null;
    if (list != null && !list.isEmpty()) {
      result = "'" + Joiner.on("','").join(list) + "'";
    }
    return result;
  }
}
