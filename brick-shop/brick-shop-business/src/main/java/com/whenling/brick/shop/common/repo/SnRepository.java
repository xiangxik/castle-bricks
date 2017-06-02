package com.whenling.brick.shop.common.repo;

import com.whenling.brick.shop.common.entity.Sn;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface SnRepository extends BaseJpaRepository<Sn, Long>, SnRepositoryCustom {

	Sn findByType(String type);
}
