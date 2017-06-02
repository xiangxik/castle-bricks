package com.whenling.brick.shop.product.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.whenling.brick.base.entity.Admin;
import com.whenling.brick.shop.order.entity.OrderItem;
import com.whenling.castle.repo.jpa.DataEntity;

/**
 * 商品
 * 
 * @author 孔祥溪
 * @date 2017年5月9日 上午11:06:55
 */
@Entity
@Table(name = "tbl_product")
public class Product extends DataEntity<Admin, Long> {

	private static final long serialVersionUID = -6934807834358886933L;

	/** 全称规格前缀 */
	public static final String FULL_NAME_SPECIFICATION_PREFIX = "[";

	/** 全称规格后缀 */
	public static final String FULL_NAME_SPECIFICATION_SUFFIX = "]";

	/** 全称规格分隔符 */
	public static final String FULL_NAME_SPECIFICATION_SEPARATOR = " ";
	
	/** 编号 */
	@NotNull
	@Pattern(regexp = "^[0-9a-zA-Z_-]+$")
	@Size(max = 100)
	@Column(nullable = false, unique = true, length = 100)
	private String sn;

	/** 名称 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String name;

	/** 全称 */
	@Column(nullable = false)
	private String fullName;

	/** 销售价 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal price;

	/** 市场价 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal marketPrice;

	/** 展示图片 */
	@Size(max = 200)
	private String image;

	/** 单位 */
	@Size(max = 100)
	private String unit;

	/** 重量 */
	@Min(0)
	private Integer weight;

	/** 库存 */
	@Min(0)
	private Integer stock;

	/** 是否上架 */
	private Boolean isMarketable;

	/** 是否列出 */
	@NotNull
	@Column(nullable = false)
	private boolean isList = false;

	/** 是否置顶 */
	@NotNull
	@Column(nullable = false)
	private boolean isTop;

	/** 介绍 */
	@Lob
	private String introduction;

	/** 备注 */
	@Size(max = 200)
	private String memo;

	/** 销量 */
	@Min(0)
	private Long sales;

	/** 货品 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Goods goods;

	/** 商品图片 */
	@Valid
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "tbl_product_image")
	private List<ProductImage> productImages = new ArrayList<>();

	/** 规格 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_product_specification")
	@OrderBy("sortNo asc")
	private Set<Specification> specifications = new HashSet<>();

	/** 规格值 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_product_specification_value")
	@OrderBy("specification asc")
	private Set<SpecificationValue> specificationValues = new HashSet<SpecificationValue>();

	/** 订单项 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();

	/** 页面标题 */
	@Size(max = 200)
	private String seoTitle;

	/** 页面关键词 */
	@Size(max = 200)
	private String seoKeywords;

	/** 页面描述 */
	@Size(max = 200)
	private String seoDescription;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Boolean getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	public boolean isList() {
		return isList;
	}

	public void setList(boolean isList) {
		this.isList = isList;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getSales() {
		return sales;
	}

	public void setSales(Long sales) {
		this.sales = sales;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public Set<Specification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(Set<Specification> specifications) {
		this.specifications = specifications;
	}

	public Set<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}

	public void setSpecificationValues(Set<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	/**
	 * 获取同货品商品
	 * 
	 * @return 同货品商品，不包含自身
	 */
	@Transient
	public List<Product> getSiblings() {
		List<Product> siblings = new ArrayList<Product>();
		if (getGoods() != null && getGoods().getProducts() != null) {
			for (Product product : getGoods().getProducts()) {
				if (!this.equals(product)) {
					siblings.add(product);
				}
			}
		}
		return siblings;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<OrderItem> orderItems = getOrderItems();
		if (orderItems != null) {
			for (OrderItem orderItem : orderItems) {
				orderItem.setProduct(null);
			}
		}
	}

}
