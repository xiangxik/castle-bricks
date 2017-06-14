package com.whenling.brick.shop.order.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.types.Predicate;
import com.whenling.brick.console.support.CrudController;
import com.whenling.brick.shop.order.entity.Shipping;

@Controller
@RequestMapping("/shipping")
public class ShippingController extends CrudController<Shipping, Long> {

	public ShippingController() {
		super(true);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<Shipping> doPage(Predicate predicate, Pageable pageable) {
		return getRepository().findAll(predicate, pageable);
	}

}
