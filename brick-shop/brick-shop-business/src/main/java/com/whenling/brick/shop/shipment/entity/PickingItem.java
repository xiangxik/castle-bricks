package com.whenling.brick.shop.shipment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "tbl_picking_item")
public class PickingItem extends BaseEntity<Long> {

	private static final long serialVersionUID = -8206855783489950942L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Picking picking;

	@NotNull
	@Column(nullable = false, updatable = false)
	private String productSn;

	@Column(nullable = false)
	private String productName;

	@Min(0)
	@Column(nullable = false)
	private Integer quantity;

	public Picking getPicking() {
		return picking;
	}

	public void setPicking(Picking picking) {
		this.picking = picking;
	}

	public String getProductSn() {
		return productSn;
	}

	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
