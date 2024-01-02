package com.oner365.data.jpa.query;

import java.util.Collection;

import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.jpa.query.Criterion.Operator;

/**
 * 条件构造器-用于创建条件表达式
 * @author zhaoyong
 */
public class Restrictions {

    private Restrictions() {

    }

    /**
     * 等于
     *
     * @param fieldName 字段
     * @param value 值
     * @return SimpleExpression
     */
    public static SimpleExpression eq(String fieldName, Object value) {
        if (DataUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Operator.EQ);
    }

    /**
     * 不等于
     *
     * @param fieldName 字段
     * @param value 值
     * @return SimpleExpression
     */
    public static SimpleExpression ne(String fieldName, Object value) {
        if (DataUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Operator.NE);
    }

    /**
     * 模糊匹配
     *
     * @param fieldName 字段
     * @param value 值
     * @return SimpleExpression
     */
    public static SimpleExpression like(String fieldName, Object value) {
        if (DataUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Operator.LIKE);
    }

    /**
     * 大于
     *
     * @param fieldName 字段
     * @param value 值
     * @return SimpleExpression
     */
    public static SimpleExpression gt(String fieldName, Object value) {
        if (DataUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Operator.GT);
    }

    /**
     * 小于
     *
     * @param fieldName 字段
     * @param value 值
     * @return SimpleExpression
     */
    public static SimpleExpression lt(String fieldName, Object value) {
        if (DataUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Operator.LT);
    }

    /**
     * 小于等于
     *
     * @param fieldName 字段
     * @param value 值
     * @return SimpleExpression
     */
    public static SimpleExpression lte(String fieldName, Object value) {
        if (DataUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Operator.LTE);
    }

    /**
     * 大于等于
     *
     * @param fieldName 字段
     * @param value 值
     * @return SimpleExpression
     */
    public static SimpleExpression gte(String fieldName, Object value) {
        if (DataUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Operator.GTE);
    }

    /**
     * 并且
     *
     * @param criterion 表达式
     * @return LogicalExpression
     */
    public static LogicalExpression and(Criterion... criterion) {
        return new LogicalExpression(criterion, Operator.AND);
    }

    /**
     * 或者
     *
     * @param criterion 表达式
     * @return LogicalExpression
     */
    public static LogicalExpression or(Criterion... criterion) {
        return new LogicalExpression(criterion, Operator.OR);
    }

    /**
     * 包含于
     *
     * @param fieldName 字段
     * @param value 值
     * @param ignoreNull 是否忽略
     * @return LogicalExpression
     */
    public static LogicalExpression in(String fieldName, Collection<?> value, boolean ignoreNull) {
        if (ignoreNull || value == null || value.isEmpty()) {
            return null;
        }
        SimpleExpression[] ses = new SimpleExpression[value.size()];
        int i = 0;
        for (Object obj : value) {
            ses[i] = new SimpleExpression(fieldName, obj, Operator.EQ);
            i++;
        }
        return new LogicalExpression(ses, Operator.OR);
    }

    /**
     * between
     *
     * @param fieldName 字段
     * @param value 值
     * @return Criterion
     */
    public static Criterion between(String fieldName, Object value) {
        if (DataUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Operator.BE);
    }

}
