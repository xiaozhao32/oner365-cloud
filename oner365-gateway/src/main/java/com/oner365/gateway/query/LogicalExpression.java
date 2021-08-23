package com.oner365.gateway.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.collect.Lists;

/**
 * 逻辑条件表达式 用于复杂条件时使用，如但属性多对应值的OR查询等
 * @author zhaoyong
 */
public class LogicalExpression implements Criterion {
    /** 逻辑表达式中包含的表达式 */
    private Criterion[] criterion;
    /** 计算符 */
    private Operator operator;

    public LogicalExpression(Criterion[] criterion, Operator operator) {
        this.criterion = criterion;
        this.operator = operator;
    }

    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = Lists.newArrayList();
        for (Criterion value : this.criterion) {
            predicates.add(value.toPredicate(root, query, builder));
        }
        if (Operator.OR.equals(operator)) {
            return builder.or(predicates.toArray(new Predicate[predicates.size()]));
        }
        return null;
    }

}
