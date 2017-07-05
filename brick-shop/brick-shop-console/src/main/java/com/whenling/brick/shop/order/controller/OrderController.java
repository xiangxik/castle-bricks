package com.whenling.brick.shop.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import com.whenling.brick.console.support.CrudController;
import com.whenling.brick.shop.common.repo.SnRepository;
import com.whenling.brick.shop.order.entity.Order;
import com.whenling.brick.shop.order.entity.OrderItem;
import com.whenling.castle.integration.webapp.json.PathFilter;

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

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ResponseBody
	@PathFilter("id,consignee,phone,address,memo,orderItems,orderItems.sn,orderItems.fullName,"
			+ "orderItems.quantity,orderItems.shippedQuantity,orderItems.returnQuantity,orderItems.reservateQuantity,orderItems.unShippedQuantity,"
			+ "orderItems.quantityPiece,orderItems.shippedQuantityPiece,orderItems.returnQuantityPiece,orderItems.reservateQuantityPiece,orderItems.unShippedQuantityPiece")
	public Order getDetail(@RequestParam("id") Order entity) {
		return super.getInfo(entity);
	}

	@Override
	protected void onShowEditPage(Order entity, Model model) {
		super.onShowEditPage(entity, model);

		if (Strings.isNullOrEmpty(entity.getSn())) {
			entity.setSn(snRepository.generate(Order.SN_TYPE));
		}
	}

	@Override
	protected void onBeforeSave(Order entity) {
		super.onBeforeSave(entity);

		Integer orderItemsCount = getParameter("orderItemsCount", Integer.class, 0);
		entity.setOrderItems(entity.getOrderItems().subList(0, orderItemsCount));
		for (OrderItem orderItem : entity.getOrderItems()) {
			orderItem.setName(orderItem.getProduct().getName());
			orderItem.setFullName(orderItem.getProduct().getFullName());
			if (orderItem.getShippedQuantity() == null) {
				orderItem.setShippedQuantity(0);
			}
			if (orderItem.getReservateQuantity() == null) {
				orderItem.setReservateQuantity(0);
			}
			if (orderItem.getReturnQuantity() == null) {
				orderItem.setReturnQuantity(0);
			}
			orderItem.setOrder(entity);
		}
		entity.setOperator(getCurrentUser());
	}

}
