package com.whenling.brick.invoicing.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.whenling.brick.base.entity.Admin;
import com.whenling.castle.repo.jpa.DataEntity;

/**
 * 供应商
 * 
 * @author 孔祥溪
 * @date 2017年5月9日 上午11:08:30
 */
@Entity
@Table(name = "tbl_supplier")
public class Supplier extends DataEntity<Admin, Long> {

	private static final long serialVersionUID = 1004123116514272091L;

	private String name;

	@Size(max = 200)
	private String contact;

	@Size(max = 200)
	private String phone;

	@Size(max = 200)
	private String area;

	@Size(max = 1000)
	@Column(length = 2000)
	private String memo;

	@Valid
	@ElementCollection
	@CollectionTable(name = "tbl_supplier_address")
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

}
