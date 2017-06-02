package com.whenling.brick.shop.order.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.brick.shop.order.entity.Order;
import com.whenling.brick.shop.order.entity.QOrder;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface OrderRepository extends BaseJpaRepository<Order, Long>, QuerydslBinderCustomizer<QOrder> {

	@Override
	default void customize(QuerydslBindings bindings, QOrder root) {
		bindings.bind(root.sn).first((path, value) -> path.contains(value));
	}

}
