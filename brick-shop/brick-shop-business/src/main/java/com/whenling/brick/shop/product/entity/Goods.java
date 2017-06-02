package com.whenling.brick.shop.product.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "tbl_goods")
public class Goods extends BaseEntity<Long> {

	private static final long serialVersionUID = -7408792533276115302L;

	@OneToMany(mappedBy = "goods", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Product> products = new HashSet<>();

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Transient
	public Set<SpecificationValue> getSpecificationValues() {
		Set<SpecificationValue> specificationValues = new HashSet<SpecificationValue>();
		if (getProducts() != null) {
			for (Product product : getProducts()) {
				specificationValues.addAll(product.getSpecificationValues());
			}
		}
		return specificationValues;
	}

}
