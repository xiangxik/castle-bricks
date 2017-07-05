package com.whenling.brick.shop.member.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.brick.shop.member.entity.DeliveryCenter;
import com.whenling.brick.shop.member.entity.QDeliveryCenter;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface DeliveryCenterRepository
		extends BaseJpaRepository<DeliveryCenter, Long>, QuerydslBinderCustomizer<QDeliveryCenter> {
	@Override
	default void customize(QuerydslBindings bindings, QDeliveryCenter root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
	}
}
