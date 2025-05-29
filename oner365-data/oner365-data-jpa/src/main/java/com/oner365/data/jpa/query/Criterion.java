package com.oner365.data.jpa.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 条件接口 - 用户提供条件表达式接口
 *
 * @author zhaoyong
 */
public interface Criterion {

    /**
     * 枚举参数
     */
    enum Operator {

        /* 相等 */
        EQ,
        /* 不相等 */
        NE,
        /* 模糊 */
        LIKE,
        /* 大于 */
        GT,
        /* 小于 */
        LT,
        /* 大于等于 */
        GTE,
        /* 小于等于 */
        LTE,
        /* 和 */
        AND,
        /* 或 */
        OR,
        /* 包含 */
        IN,
        /* Between */
        BE,
        /* 枚举 */
        ENUM

    }

    /**
     * 条件表达式
     * @param root Root
     * @param query CriteriaQuery
     * @param builder CriteriaBuilder
     * @return Predicate
     */
    Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder);

}
