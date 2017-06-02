package com.whenling.brick.shop.order.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.whenling.brick.base.entity.Admin;
import com.whenling.castle.repo.jpa.DataEntity;
 
/**
  * 进货 
  * 
  * @author 孔祥溪
  * @date 2017年5月9日 上午11:08:18
  */
@Entity
@Table(name = "tbl_stock")
public class Stock extends DataEntity<Admin, Long> {

	private static final long serialVersionUID = 2706258668768206231L;

	/** 编号 */
	private String sn;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
}
