package com.whenling.brick.shop.shipment.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.brick.shop.shipment.entity.QShipping;
import com.whenling.brick.shop.shipment.entity.Shipping;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface ShippingRepository extends BaseJpaRepository<Shipping, Long>, QuerydslBinderCustomizer<QShipping> {

	@Override
	default void customize(QuerydslBindings bindings, QShipping root) {
		bindings.bind(root.sn).first((path, value) -> path.contains(value));
	}

}
