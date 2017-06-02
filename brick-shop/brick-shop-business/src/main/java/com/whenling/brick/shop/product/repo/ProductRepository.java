package com.whenling.brick.shop.product.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.brick.shop.product.entity.Product;
import com.whenling.brick.shop.product.entity.QProduct;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface ProductRepository
		extends BaseJpaRepository<Product, Long>, QuerydslBinderCustomizer<QProduct>, ProductRepositoryCustom {

	Product findBySn(String sn);

	@Override
	default void customize(QuerydslBindings bindings, QProduct root) {
		bindings.bind(root.name, root.sn).first((path, value) -> path.contains(value));
	}

}
