package com.oner365.common.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 条件接口 - 用户提供条件表达式接口
 * @author zhaoyong
 */
public interface Criterion {
    enum Operator {
        /** 条件表达式 */
        EQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR, IN, BE
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
