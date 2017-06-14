package com.whenling.brick.shop.common.repo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.base.Strings;
import com.whenling.brick.shop.common.entity.Sn;

public class SnRepositoryImpl implements SnRepositoryCustom {

	private Map<String, HiloOptimizer> CACHE = new HashMap<>();

	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMdd");

	@PersistenceContext
	protected EntityManager entityManager;

	@Transactional
	@Override
	public String generate(String type) {
		Assert.notNull(type, "type must be not null");
		HiloOptimizer hiloOptimizer = CACHE.get(type);
		if (hiloOptimizer == null) {
			hiloOptimizer = new HiloOptimizer(type);
			CACHE.put(type, hiloOptimizer);
		}
		return hiloOptimizer.generate();
	}

	private long getLastValue(String type) {
		Sn sn = getSn(type);
		long lastValue = sn.getLastValue();
		sn.setLastValue(lastValue + 1);
		entityManager.merge(sn);
		return lastValue;
	}

	private Sn getSn(String type) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sn> query = criteriaBuilder.createQuery(Sn.class);
		Root<Sn> root = query.from(Sn.class);
		query.where(criteriaBuilder.equal(root.get("type"), type));
		try {
			return entityManager.createQuery(query).setFlushMode(FlushModeType.COMMIT).setLockMode(LockModeType.PESSIMISTIC_WRITE).getSingleResult();
		} catch (NoResultException e) {
			Sn sn = new Sn();
			sn.setType(type);
			sn.setLastValue(0l);
			entityManager.persist(sn);
			return sn;
		} catch (NonUniqueResultException e) {
			throw new RuntimeException(e);
		}
	}

	private class HiloOptimizer {

		private String type;

		public HiloOptimizer(String type) {
			this.type = type;
		}

		public synchronized String generate() {
			long lastValue = getLastValue(type);
			return DATE_FORMAT.format(new Date()) + Strings.padStart("" + lastValue, 4, '0');
		}
	}

}
