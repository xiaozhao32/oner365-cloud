package com.oner365.common.query;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.google.common.base.Joiner;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.BaseEnum;
import com.oner365.common.query.Criterion.Operator;
import com.oner365.util.DataUtils;

/**
 * 查询工具类
 *
 * @author zhaoyong
 */
public class QueryUtils {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(QueryUtils.class);

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
      Object value = attr.getVal();
      if (DataUtils.isEmpty(key) || DataUtils.isEmpty(value)) {
        return;
      }
      getOperator(criteria, operator, key, value);
    });
    return criteria;
  }

  private static <T> void getOperator(Criteria<T> criteria, Operator operator, String key, Object value) {
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
      if (value.toString().length() > 1) {
        criteria.add(Restrictions.in(key, Arrays.asList(value.toString().split(",")), true));
      }
      break;
    case BE:
      // 格式: 开始条件|结束条件
      if (value.toString().length() > 1) {
        criteria.add(Restrictions.between(key, value));
      }
      break;
    case ENUM:
      criteria.add(Restrictions.eq(key, getEnum(key, String.valueOf(value))));
      break;
    default:
      criteria.add(Restrictions.eq(key, value));
      break;
    }
  }
  
  /**
   * 枚举类型
   * 
   * @param key   键
   * @param value 值
   * @return 枚举
   */
  private static Object getEnum(String key, String value) {
    try {
      Class<?> clazz = Class.forName(PublicConstants.initEnumMap.get(key));
      if (clazz.isEnum()) {
        Field f = clazz.getDeclaredField(value);
        f.setAccessible(true);
        BaseEnum result = (BaseEnum) f.get(value);
        return result;
      }
    } catch (Exception e) {
      // 当前枚举不支持
      LOGGER.error("Enum not support. key:{} value:{}", key, value);
    }
    return null;
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
    if (!DataUtils.isEmpty(order) && !DataUtils.isEmpty(order.getKey())) {
      if (Direction.DESC.name().equalsIgnoreCase(order.getVal().toString())) {
        return Sort.by(Direction.DESC, order.getKey().split(","));
      } else {
        return Sort.by(Direction.ASC, order.getKey().split(","));
      }
    }
    return Sort.unsorted();
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
      Object value = attr.getVal();
      String opt = attr.getOpt();
      if (DataUtils.isEmpty(key) || DataUtils.isEmpty(value)) {
        return;
      }
      getCriterion(sb, key, opt, value);
    });
    return sb.toString();
  }

  private static void getCriterion(StringBuilder sb, String key, String opt, Object value) {
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
      if (value.toString().length() > 1) {
        String valueString = getInCriterion(Arrays.asList(value.toString().split(",")));
        sb.append(PARAM_QUERY_AND).append(key).append(" in (").append(valueString).append(")");
      }
    } else if (Operator.BE.name().equals(opt)) {
      // 格式: 开始条件|结束条件
      if (value.toString().length() > 1) {
        String[] array = StringUtils.split(value.toString(), "|");
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
