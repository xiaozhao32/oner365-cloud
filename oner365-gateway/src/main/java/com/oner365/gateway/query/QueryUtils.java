
package com.oner365.gateway.query;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;

import com.oner365.gateway.constants.GatewayConstants;
import com.oner365.gateway.enums.StatusEnum;
import com.oner365.gateway.query.Criterion.Operator;
import com.oner365.gateway.util.DataUtils;

/**
 * 查询工具类
 * 
 * @author zhaoyong
 */
public class QueryUtils {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(QueryUtils.class);

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
    if (data == null || DataUtils.isEmpty(data.getWhereList())) {
      return criteria;
    }
    for (AttributeBean attr : data.getWhereList()) {
      String key = attr.getKey();
      String opt = !DataUtils.isEmpty(attr.getOpt()) ? attr.getOpt().toUpperCase() : Operator.EQ.name();
      Operator operator = Criterion.Operator.valueOf(opt);
      Object value = attr.getVal();
      if (DataUtils.isEmpty(key) || DataUtils.isEmpty(value)) {
        continue;
      }
      getOperator(criteria, operator, key, value);
    }
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
    // 状态枚举
    if (GatewayConstants.STATUS.equals(key)) {
      return StatusEnum.valueOf(value);
    } else {
      // 当前枚举不支持
      LOGGER.error("Enum not support. key:{} value:{}", key, value);
    }
    return null;
  }

}
