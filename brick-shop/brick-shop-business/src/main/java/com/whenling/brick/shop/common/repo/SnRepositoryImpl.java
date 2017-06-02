package com.whenling.brick.shop.common.repo;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.whenling.brick.shop.common.entity.Sn;

public class SnRepositoryImpl implements SnRepositoryCustom {

	private Map<String, HiloOptimizer> CACHE = new HashMap<>();

	@PersistenceContext
	protected EntityManager entityManager;

	@Transactional
	@Override
	public String generate(String type) {
		Assert.notNull(type, "type must be not null");
		HiloOptimizer hiloOptimizer = CACHE.get(type);
		if (hiloOptimizer == null) {
			hiloOptimizer = new HiloOptimizer(type, 100);
		}
		return hiloOptimizer.generate();
	}

	private long getLastValue(String type) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sn> query = criteriaBuilder.createQuery(Sn.class);
		Root<Sn> root = query.from(Sn.class);
		query.where(criteriaBuilder.equal(root.get("type"), type));
		Sn sn = entityManager.createQuery(query).setFlushMode(FlushModeType.COMMIT).setLockMode(LockModeType.PESSIMISTIC_WRITE).getSingleResult();
		long lastValue = sn.getLastValue();
		sn.setLastValue(lastValue + 1);
		entityManager.merge(sn);
		return lastValue;
	}

	private class HiloOptimizer {

		private String type;
		private int maxLo;
		private int lo;
		private long hi;
		private long lastValue;

		public HiloOptimizer(String type, int maxLo) {
			this.type = type;
			this.maxLo = maxLo;
			this.lo = maxLo + 1;
		}

		public synchronized String generate() {
			if (lo > maxLo) {
				lastValue = getLastValue(type);
				lo = lastValue == 0 ? 1 : 0;
				hi = lastValue * (maxLo + 1);
			}
			return String.valueOf(hi + lo++);
		}
	}

}
