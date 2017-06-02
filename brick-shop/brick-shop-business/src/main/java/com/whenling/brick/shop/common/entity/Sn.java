package com.whenling.brick.shop.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "tbl_sn")
public class Sn extends BaseEntity<Long> {

	private static final long serialVersionUID = 4473093650056889715L;

	/** 类型 */
	@Column(nullable = false, updatable = false, unique = true)
	private String type;

	/** 末值 */
	@Column(nullable = false)
	private long lastValue;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getLastValue() {
		return lastValue;
	}

	public void setLastValue(long lastValue) {
		this.lastValue = lastValue;
	}

}
