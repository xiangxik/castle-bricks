package com.whenling.brick.shop.product.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import com.whenling.brick.console.support.CrudController;
import com.whenling.brick.shop.common.repo.SnRepository;
import com.whenling.brick.shop.product.entity.Goods;
import com.whenling.brick.shop.product.entity.Product;
import com.whenling.brick.shop.product.entity.Specification;
import com.whenling.brick.shop.product.entity.SpecificationValue;
import com.whenling.brick.shop.product.repo.GoodsRepository;
import com.whenling.brick.shop.product.repo.ProductRepository;
import com.whenling.brick.shop.product.repo.SpecificationRepository;
import com.whenling.castle.integration.webapp.json.PathFilter;
import com.whenling.castle.repo.domain.SortNoComparator;

@Controller
@RequestMapping("/product")
public class ProductController extends CrudController<Product, Long> {

	@Autowired
	private GoodsRepository goodsRepository;

	@Autowired
	private SpecificationRepository specificationRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SnRepository snRepository;
	
	public ProductController() {
		super(true);
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	@PathFilter("*,*.id,*.name,*.fullName,*.sn,*.lastModifiedDate,*.lastModifiedBy,*.lastModifiedBy.id,*.lastModifiedBy.name")
	public Page<Product> doPage(Predicate predicate, Pageable pageable) {
		return getRepository().findAll(predicate, pageable);
	}

	@Override
	protected void onShowEditPage(Product entity, Model model) {
		super.onShowEditPage(entity, model);
		if (Strings.isNullOrEmpty(entity.getSn())) {
			entity.setSn(snRepository.generate(Product.SN_TYPE));
		}
		entity.getProductImages().sort(SortNoComparator.COMPARATOR);

		model.addAttribute("specifications", specificationRepository.findAll());
	}

	@Override
	protected void onBeforeSave(Product entity) {
		super.onBeforeSave(entity);

		Integer productImageCount = getParameter("productImageCount", Integer.class, 0);
		entity.setProductImages(entity.getProductImages().subList(0, productImageCount));

		Goods goods = entity.isNew() ? goodsRepository.newEntity() : entity.getGoods();
		List<Product> products = new ArrayList<Product>();
		List<Product> specificationProducts = getParameterValues("specificationProducts", Product.class);
		Set<Specification> specifications = entity.getSpecifications();
		if (specifications != null && specifications.size() > 0) {
			boolean initedProducts = false;
			for (Specification specification : specifications) {
				List<SpecificationValue> specificationValues = getParameterValues("specification_" + specification.getId(), SpecificationValue.class);
				if (specificationValues != null && specificationValues.size() > 0) {
					for (int j = 0; j < specificationValues.size(); j++) {
						if (!initedProducts) {
							if (j == 0) {
								entity.setSpecifications(new HashSet<>());
								entity.setSpecificationValues(new HashSet<>());
								entity.setGoods(goods);
								products.add(entity);
							} else {
								if (specificationProducts != null && j < specificationProducts.size()) {
									Product specificationProduct = specificationProducts.get(j);
									specificationProduct.setSpecifications(new HashSet<>());
									specificationProduct.setSpecificationValues(new HashSet<>());
									specificationProduct.setGoods(goods);
									products.add(specificationProduct);
								} else {
									Product specificationProduct = getRepository().newEntity();
									BeanUtils.copyProperties(entity, specificationProduct,
											new String[] { "id", "sn", "fullName", "specifications", "specificationValues" });
									specificationProduct.setOrderItems(null);
									specificationProduct.setFullName(null);
									specificationProduct.setSn(null);
									specificationProduct.setGoods(goods);
									specificationProduct.setSpecifications(new HashSet<>());
									specificationProduct.setSpecificationValues(new HashSet<>());
									products.add(specificationProduct);
								}
							}
						}
						Product specificationProduct = products.get(j);
						specificationProduct.getSpecifications().add(specification);
						specificationProduct.getSpecificationValues().add(specificationValues.get(j));
					}
					initedProducts = true;
				}

			}
		} else {
			entity.setSpecifications(null);
			entity.setSpecificationValues(null);
			entity.setGoods(goods);
			products.add(entity);
		}

		goods.getProducts().clear();
		goods.getProducts().addAll(products);

		for (Product product : products) {
			if (product == null) {
				continue;
			}
			if (Strings.isNullOrEmpty(product.getSn())) {
				String sn;
				do {
					sn = snRepository.generate(Product.SN_TYPE);
				} while (productRepository.snExists(sn));
				product.setSn(sn);
			}

			StringBuffer fullName = new StringBuffer(product.getName());
			if (product.getSpecificationValues() != null && !product.getSpecificationValues().isEmpty()) {
				List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue>(product.getSpecificationValues());
				Collections.sort(specificationValues, new Comparator<SpecificationValue>() {
					public int compare(SpecificationValue a1, SpecificationValue a2) {
						return new CompareToBuilder().append(a1.getSpecification(), a2.getSpecification()).toComparison();
					}
				});
				fullName.append(Product.FULL_NAME_SPECIFICATION_PREFIX);
				int i = 0;
				for (Iterator<SpecificationValue> iterator = specificationValues.iterator(); iterator.hasNext(); i++) {
					if (i != 0) {
						fullName.append(Product.FULL_NAME_SPECIFICATION_SEPARATOR);
					}
					fullName.append(iterator.next().getName());
				}
				fullName.append(Product.FULL_NAME_SPECIFICATION_SUFFIX);
			}
			product.setFullName(fullName.toString());
		}

		goodsRepository.save(goods);
	}

}
