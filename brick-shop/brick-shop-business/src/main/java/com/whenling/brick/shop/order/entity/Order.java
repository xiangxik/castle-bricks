package com.whenling.brick.shop.order.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.whenling.brick.base.entity.Admin;
import com.whenling.brick.base.entity.User;
import com.whenling.castle.repo.jpa.DataEntity;

/**
 * 订单
 * 
 * @author 孔祥溪
 * @date 2017年5月9日 上午11:06:46
 */
@Entity
@Table(name = "tbl_order")
public class Order extends DataEntity<Admin, Long> {

	private static final long serialVersionUID = 434106145845439014L;

	/** 订单名称分隔符 */
	private static final String NAME_SEPARATOR = " ";
	
	public static final String SN_TYPE = "order";

	/**
	 * 订单状态
	 */
	public enum OrderStatus {

		/** 未确认 */
		unconfirmed,

		/** 已确认 */
		confirmed,

		/** 已完成 */
		completed,

		/** 已取消 */
		cancelled
	}

	/**
	 * 支付状态
	 */
	public enum PaymentStatus {

		/** 未支付 */
		unpaid,

		/** 部分支付 */
		partialPayment,

		/** 已支付 */
		paid,

		/** 部分退款 */
		partialRefunds,

		/** 已退款 */
		refunded
	}

	/**
	 * 配送状态
	 */
	public enum ShippingStatus {

		/** 未发货 */
		unshipped,

		/** 部分发货 */
		partialShipment,

		/** 已发货 */
		shipped,

		/** 部分退货 */
		partialReturns,

		/** 已退货 */
		returned
	}

	/** 编号 */
	@Pattern(regexp = "^[0-9a-zA-Z_-]+$")
	@Size(max = 100)
	@Column(nullable = false, unique = true, length = 100)
	private String sn;

	/** 订单状态 */
	@Column(nullable = false)
	private OrderStatus orderStatus = OrderStatus.unconfirmed;

	/** 支付状态 */
	@Column(nullable = false)
	private PaymentStatus paymentStatus = PaymentStatus.unpaid;

	/** 配送状态 */
	@Column(nullable = false)
	private ShippingStatus shippingStatus = ShippingStatus.unshipped;

	/** 用户 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private User user;

	/** 操作员 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Admin operator;

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

	/** 附言 */
	@Size(max = 200)
	private String memo;

	/** 订单项 */
	@Valid
	@NotNull
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();

	/** 订单日志 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createdDate asc")
	private Set<OrderLog> orderLogs = new HashSet<>();

	/** 发货单 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createdDate asc")
	private Set<Shipping> shippings = new HashSet<Shipping>();

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Set<OrderLog> getOrderLogs() {
		return orderLogs;
	}

	public void setOrderLogs(Set<OrderLog> orderLogs) {
		this.orderLogs = orderLogs;
	}

	public Set<Shipping> getShippings() {
		return shippings;
	}

	public void setShippings(Set<Shipping> shippings) {
		this.shippings = shippings;
	}

	/**
	 * 获取订单名称
	 * 
	 * @return 订单名称
	 */
	@Transient
	public String getName() {
		StringBuffer name = new StringBuffer();
		if (getOrderItems() != null) {
			for (OrderItem orderItem : getOrderItems()) {
				if (orderItem != null && orderItem.getFullName() != null) {
					name.append(NAME_SEPARATOR).append(orderItem.getFullName());
				}
			}
			if (name.length() > 0) {
				name.deleteCharAt(0);
			}
		}
		return name.toString();
	}

	/**
	 * 获取商品数量
	 * 
	 * @return 商品数量
	 */
	@Transient
	public int getQuantity() {
		int quantity = 0;
		if (getOrderItems() != null) {
			for (OrderItem orderItem : getOrderItems()) {
				if (orderItem != null && orderItem.getQuantity() != null) {
					quantity += orderItem.getQuantity();
				}
			}
		}
		return quantity;
	}

	/**
	 * 获取已发货数量
	 * 
	 * @return 已发货数量
	 */
	@Transient
	public int getShippedQuantity() {
		int shippedQuantity = 0;
		if (getOrderItems() != null) {
			for (OrderItem orderItem : getOrderItems()) {
				if (orderItem != null && orderItem.getShippedQuantity() != null) {
					shippedQuantity += orderItem.getShippedQuantity();
				}
			}
		}
		return shippedQuantity;
	}

	/**
	 * 获取订单项
	 * 
	 * @param sn
	 *            商品编号
	 * @return 订单项
	 */
	@Transient
	public OrderItem getOrderItem(String sn) {
		if (sn != null && getOrderItems() != null) {
			for (OrderItem orderItem : getOrderItems()) {
				if (orderItem != null && sn.equalsIgnoreCase(orderItem.getSn())) {
					return orderItem;
				}
			}
		}
		return null;
	}

}
