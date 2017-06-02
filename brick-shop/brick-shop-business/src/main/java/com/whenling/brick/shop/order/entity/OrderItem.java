package com.whenling.brick.shop.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.whenling.brick.shop.product.entity.Product;
import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "tbl_order_item")
public class OrderItem extends BaseEntity<Long> {

	private static final long serialVersionUID = -2679552863586371498L;

	/** 订单 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders", nullable = false, updatable = false)
	private Order order;

	/** 商品 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	/** 商品编号 */
	@NotNull
	@Column(nullable = false, updatable = false)
	private String sn;

	/** 商品名称 */
	@Column(nullable = false, updatable = false)
	private String name;

	/** 商品全称 */
	@Column(nullable = false, updatable = false)
	private String fullName;

	/** 数量 */
	@NotNull
	@Min(1)
	@Max(100000000)
	@Column(nullable = false)
	private Integer quantity;

	/** 已发货数量 */
	@Column(nullable = false)
	private Integer shippedQuantity;

	/** 已退货数量 */
	@Column(nullable = false)
	private Integer returnQuantity;

	/** 预约发货数量 */
	@Min(0)
	@Column(nullable = false)
	private Integer reservateQuantity;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getShippedQuantity() {
		return shippedQuantity;
	}

	public void setShippedQuantity(Integer shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}

	public Integer getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(Integer returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public Integer getReservateQuantity() {
		return reservateQuantity;
	}

	public void setReservateQuantity(Integer reservateQuantity) {
		this.reservateQuantity = reservateQuantity;
	}

}
