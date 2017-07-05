package com.whenling.brick.shop.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.brick.base.entity.Admin;
import com.whenling.castle.repo.jpa.DataEntity;

/**
 * 发货点
 * 
 * @author 孔祥溪
 * @date 2017年5月9日 上午11:06:35
 */
@Entity
@Table(name = "tbl_delivery_center")
public class DeliveryCenter extends DataEntity<Admin, Long> {

	private static final long serialVersionUID = 3068020458925742990L;

	/** 名称 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String name;

	/** 物流公司 */
	@Size(max = 200)
	private String deliveryCorp;

	/** 联系人 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String contact;

	/** 地址 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String address;

	/** 邮编 */
	@Size(max = 200)
	private String zipCode;

	/** 电话 */
	@Size(max = 200)
	private String phone;

	/** 备注 */
	@Size(max = 200)
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeliveryCorp() {
		return deliveryCorp;
	}

	public void setDeliveryCorp(String deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

}
