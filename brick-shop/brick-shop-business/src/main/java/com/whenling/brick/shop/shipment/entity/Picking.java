package com.whenling.brick.shop.shipment.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.whenling.brick.base.entity.Admin;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "tbl_picking")
public class Picking extends DataEntity<Admin, Long> {

	private static final long serialVersionUID = 5240644028049843648L;

	/** 编号 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	private String sn;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "picking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PickingItem> pickingItems = new ArrayList<>();

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public List<PickingItem> getPickingItems() {
		return pickingItems;
	}

	public void setPickingItems(List<PickingItem> pickingItems) {
		this.pickingItems = pickingItems;
	}

}
