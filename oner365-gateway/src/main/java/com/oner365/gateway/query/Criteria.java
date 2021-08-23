package com.oner365.gateway.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.google.common.collect.Lists;

/**
 * 定义一个查询条件容器
 * @author zhaoyong
 */
public class Criteria<T> implements Specification<T>{

    private static final long serialVersionUID = 1L;
    private List<Criterion> criterionList = Lists.newArrayList();

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
            CriteriaBuilder builder) {
        if (!criterionList.isEmpty()) {
            List<Predicate> predicates = Lists.newArrayList();
            for(Criterion c : criterionList){
                predicates.add(c.toPredicate(root, query,builder));
            }
            query.distinct(true);
            // 将所有条件用 and 联合起来
            if (!predicates.isEmpty()) {
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }
        return builder.conjunction();
    }

    /***
     * 增加简单条件表达式
     * @param criterion 表达式
     */
    public void add(Criterion criterion){
        if(criterion!=null){
            criterionList.add(criterion);
        }
    }
}
