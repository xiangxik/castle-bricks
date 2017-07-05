package com.whenling.brick.shop.member.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.brick.shop.member.entity.QSupplier;
import com.whenling.brick.shop.member.entity.Supplier;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface SupplierRepository extends BaseJpaRepository<Supplier, Long>, QuerydslBinderCustomizer<QSupplier> {

	@Override
	default void customize(QuerydslBindings bindings, QSupplier root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
	}
}
