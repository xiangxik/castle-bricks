package com.whenling.brick.invoicing.member.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.types.Predicate;
import com.whenling.brick.console.support.CrudController;
import com.whenling.brick.invoicing.member.entity.DeliveryCenter;

@Controller
@RequestMapping("/deliveryCenter")
public class DeliveryCenterController extends CrudController<DeliveryCenter, Long> {

	public DeliveryCenterController() {
		super(true);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<DeliveryCenter> doPage(Predicate predicate, Pageable pageable) {
		return getRepository().findAll(predicate, pageable);
	}

}
