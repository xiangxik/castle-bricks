package com.whenling.brick.shop.shipment.controller;

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
import com.whenling.brick.shop.shipment.entity.Shipping;
import com.whenling.castle.integration.webapp.json.PathFilter;

@Controller
@RequestMapping("/shipping")
public class ShippingController extends CrudController<Shipping, Long> {

	@Autowired
	private SnRepository snRepository;

	public ShippingController() {
		super(true);
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	@PathFilter("*,*.*,*.order.sn")
	public Page<Shipping> doPage(Predicate predicate, Pageable pageable) {
		return getRepository().findAll(predicate, pageable);
	}
	
	@Override
	protected void onShowEditPage(Shipping entity, Model model) {
		super.onShowEditPage(entity, model);

		if (Strings.isNullOrEmpty(entity.getSn())) {
			entity.setSn(snRepository.generate(Shipping.SN_TYPE));
		}
	}

	@Override
	protected void onBeforeSave(Shipping entity) {
		super.onBeforeSave(entity);

		entity.setOperator(getCurrentUser().getName());
	}

}
