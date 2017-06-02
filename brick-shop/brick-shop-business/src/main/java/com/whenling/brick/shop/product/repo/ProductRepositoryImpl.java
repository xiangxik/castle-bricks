package com.whenling.brick.shop.product.repo;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public boolean snExists(String sn) {
		if (sn == null) {
			return false;
		}
		
		String jpql = "select count(*) from Product product where lower(product.sn) = lower(:sn)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("sn", sn).getSingleResult();
		return count > 0;
	}

}
