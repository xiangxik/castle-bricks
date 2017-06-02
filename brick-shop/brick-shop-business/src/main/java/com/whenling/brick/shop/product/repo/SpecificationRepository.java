package com.whenling.brick.shop.product.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.brick.shop.product.entity.QSpecification;
import com.whenling.brick.shop.product.entity.Specification;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface SpecificationRepository
		extends BaseJpaRepository<Specification, Long>, QuerydslBinderCustomizer<QSpecification> {
	@Override
	default void customize(QuerydslBindings bindings, QSpecification root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
	}
}
