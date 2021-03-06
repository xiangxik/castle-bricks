package com.whenling.brick.shop.member.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.brick.shop.member.entity.Customer;
import com.whenling.brick.shop.member.entity.QCustomer;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface CustomerRepository extends BaseJpaRepository<Customer, Long>, QuerydslBinderCustomizer<QCustomer> {

	@Override
	default void customize(QuerydslBindings bindings, QCustomer root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
	}
}
