package com.oner365.common.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

/**
 * 定义一个查询条件容器
 *
 * @author zhaoyong
 */
public class Criteria<T> implements Specification<T> {

	private static final long serialVersionUID = 1L;
	private final List<Criterion> criterionList = new ArrayList<>();

	@Override
	public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query,
			@NonNull CriteriaBuilder builder) {
		if (!criterionList.isEmpty()) {
			List<Predicate> predicates = new ArrayList<>();
			criterionList.forEach(c -> predicates.add(c.toPredicate(root, query, builder)));
			query.distinct(true);
			// 将所有条件用 and 联合起来
			if (!predicates.isEmpty()) {
				return builder.and(predicates.toArray(new Predicate[0]));
			}
		}
		return builder.conjunction();
	}

	/***
	 * 增加简单条件表达式
	 *
	 * @param criterion 表达式
	 */
	public void add(Criterion criterion) {
		if (criterion != null) {
			criterionList.add(criterion);
		}
	}
}
