package com.oner365.data.jpa.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.oner365.data.commons.util.DateUtil;

/**
 * 简单条件表达式
 *
 * @author zhaoyong
 */
public class SimpleExpression implements Criterion {

    private static final String POINT = ".";

    private static final String COLLECTION = "List";

    /**
     * 属性名
     */
    private final String fieldName;

    /**
     * 对应值
     */
    private final Object value;

    /**
     * 计算符
     */
    private final Operator operator;

    protected SimpleExpression(String fieldName, Object value, Operator operator) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getValue() {
        return value;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path<Object> expression;
        if (fieldName.contains(POINT)) {
            String[] names = StringUtils.split(fieldName, POINT);
            if (names[0].contains(COLLECTION)) {
                expression = root.join(names[0], JoinType.LEFT);
            }
            else {
                expression = root.get(names[0]);
            }
            for (int i = 1; i < names.length; i++) {
                expression = expression.get(names[i]);
            }
        }
        else {
            expression = root.get(fieldName);
        }
        return getOperator(builder, expression);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Predicate getOperator(CriteriaBuilder builder, Path expression) {
        switch (operator) {
            case EQ:
                return builder.equal(expression, value);
            case NE:
                return builder.notEqual(expression, value);
            case LIKE:
                return builder.like((Expression<String>) expression, "%" + value + "%");
            case LT:
                if (DateUtil.isLocalDateTime(String.valueOf(value))) {
                    return builder.lessThan(expression, DateUtil.toLocalDateTime(String.valueOf(value)));
                }
                return builder.lessThan(expression, (Comparable) value);
            case GT:
                if (DateUtil.isLocalDateTime(String.valueOf(value))) {
                    return builder.greaterThan(expression, DateUtil.toLocalDateTime(String.valueOf(value)));
                }
                return builder.greaterThan(expression, (Comparable) value);
            case LTE:
                if (DateUtil.isLocalDateTime(String.valueOf(value))) {
                    return builder.lessThanOrEqualTo(expression, DateUtil.toLocalDateTime(String.valueOf(value)));
                }
                return builder.lessThanOrEqualTo(expression, (Comparable) value);
            case GTE:
                if (DateUtil.isLocalDateTime(String.valueOf(value))) {
                    return builder.greaterThanOrEqualTo(expression, DateUtil.toLocalDateTime(String.valueOf(value)));
                }
                return builder.greaterThanOrEqualTo(expression, (Comparable) value);
            case BE:
                String param = String.valueOf(value);
                String[] array = StringUtils.split(param, "|");
                if (DateUtil.isLocalDateTime(array[0]) && DateUtil.isLocalDateTime(array[1])) {
                    return builder.between(expression, DateUtil.toLocalDateTime(array[0]),
                            DateUtil.toLocalDateTime(array[1]));
                }
                return builder.between(expression, (Comparable) array[0], (Comparable) array[1]);
            default:
                return null;
        }
    }

}
