package com.whenling.brick.shop.member.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.domain.Sortable;

/**
 * 地址
 * 
 * @author 孔祥溪
 * @date 2017年5月9日 上午11:06:04
 */
@Embeddable
public class Address implements Sortable, Serializable {

	private static final long serialVersionUID = -6373430415678496189L;

	/** 收货人 */
	@Size(max = 200)
	private String consignee;

	/** 地址 */
	@Size(max = 200)
	private String detail;

	/** 邮编 */
	@Size(max = 200)
	private String zipCode;

	/** 电话 */
	@Size(max = 100)
	private String phone;

	/** 排序 */
	private Integer sortNo;

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	@Override
	public Integer getSortNo() {
		return sortNo;
	}

	@Override
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

}
