package com.whenling.brick.shop.shipment.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.brick.base.entity.Admin;
import com.whenling.brick.shop.order.entity.Order;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "tbl_shipping")
public class Shipping extends DataEntity<Admin, Long> {

	private static final long serialVersionUID = 6662739372149174927L;
	
	public static final String SN_TYPE = "shipping";

	/** 编号 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	private String sn;

	/** 配送方式 */
//	@NotNull
//	@Column(nullable = false, updatable = false)
	private String shippingMethod;

	/** 物流公司 */
//	@NotNull
//	@Column(nullable = false, updatable = false)
	private String deliveryCorp;

	/** 运单号 */
	@Size(max = 200)
	@Column(updatable = false)
	private String trackingNo;

	/** 收货人 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String consignee;

	/** 地址 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String address;

	/** 邮编 */
	@Size(max = 200)
	private String zipCode;

	/** 电话 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String phone;

	/** 操作员 */
	@Column(nullable = false)
	private String operator;

	/** 备注 */
	@Size(max = 200)
	private String memo;

	/** 订单 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders", nullable = false, updatable = false)
	private Order order;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "shipping", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ShippingItem> shippingItems = new ArrayList<>();

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getDeliveryCorp() {
		return deliveryCorp;
	}

	public void setDeliveryCorp(String deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<ShippingItem> getShippingItems() {
		return shippingItems;
	}

	public void setShippingItems(List<ShippingItem> shippingItems) {
		this.shippingItems = shippingItems;
	}
}
