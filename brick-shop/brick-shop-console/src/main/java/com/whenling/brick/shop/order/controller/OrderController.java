package com.whenling.brick.shop.order.controller;

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
import com.whenling.brick.shop.order.entity.Order;

@Controller
@RequestMapping("/order")
public class OrderController extends CrudController<Order, Long> {

	@Autowired
	private SnRepository snRepository;

	public OrderController() {
		super(true);
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<Order> doPage(Predicate predicate, Pageable pageable) {
		return getRepository().findAll(predicate, pageable);
	}

	@Override
	protected void onShowEditPage(Order entity, Model model) {
		super.onShowEditPage(entity, model);

		if (Strings.isNullOrEmpty(entity.getSn())) {
			entity.setSn(snRepository.generate(Order.SN_TYPE));
		}
	}

}
