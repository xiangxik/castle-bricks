package com.whenling.brick.shop.product.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.types.Predicate;
import com.whenling.brick.console.support.CrudController;
import com.whenling.brick.shop.product.entity.Specification;
import com.whenling.brick.shop.product.entity.SpecificationValue;

@Controller
@RequestMapping("/specification")
public class SpecificationController extends CrudController<Specification, Long> {

	public SpecificationController() {
		super(true);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<Specification> doPage(Predicate predicate, Pageable pageable) {
		return getRepository().findAll(predicate, pageable);
	}
	
	@Override
	protected void onBeforeSave(Specification entity) {
		super.onBeforeSave(entity);

		Integer specificationValueCount = getParameter("specificationValueCount", Integer.class, 0);
		List<SpecificationValue> submitedValues = entity.getSpecificationValues().subList(0, specificationValueCount);
		submitedValues.forEach(specificationValue -> specificationValue.setSpecification(entity));
		entity.setSpecificationValues(submitedValues);
	}

}
