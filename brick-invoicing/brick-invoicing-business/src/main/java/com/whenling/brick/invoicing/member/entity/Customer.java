package com.whenling.brick.invoicing.member.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.brick.base.entity.Admin;
import com.whenling.castle.repo.jpa.DataEntity;

/**
 * 客户
 * 
 * @author 孔祥溪
 * @date 2017年5月9日 上午11:06:22
 */
@Entity
@Table(name = "tbl_customer")
public class Customer extends DataEntity<Admin, Long> {

	private static final long serialVersionUID = -3916107487166341918L;

	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String name;

	@Size(max = 200)
	private String contact;

	@Size(max = 200)
	private String phone;

	@Size(max = 200)
	private String area;

	@Size(max = 200)
	private String salesman;

	@Size(max = 1000)
	@Column(length = 2000)
	private String memo;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_customer_delivery_center")
	private Set<DeliveryCenter> deliveryCenters = new HashSet<>();

	@Valid
	@ElementCollection
	@CollectionTable(name = "tbl_customer_address")
	private List<Address> addresses = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Set<DeliveryCenter> getDeliveryCenters() {
		return deliveryCenters;
	}

	public void setDeliveryCenters(Set<DeliveryCenter> deliveryCenters) {
		this.deliveryCenters = deliveryCenters;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

}
