package com.whenling.brick.shop.shipment.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import com.whenling.brick.console.support.CrudController;
import com.whenling.brick.shop.common.repo.SnRepository;
import com.whenling.brick.shop.order.entity.Order;
import com.whenling.brick.shop.order.entity.OrderItem;
import com.whenling.brick.shop.order.repo.OrderRepository;
import com.whenling.brick.shop.shipment.entity.Shipping;
import com.whenling.brick.shop.shipment.entity.ShippingItem;
import com.whenling.castle.integration.webapp.json.PathFilter;

@Controller
@RequestMapping("/shipping")
public class ShippingController extends CrudController<Shipping, Long> {

	@Autowired
	private SnRepository snRepository;

	@Autowired
	private OrderRepository orderRepository;

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

		List<ShippingItem> shippingItems = entity.getShippingItems();
		if (shippingItems != null) {
			List<ShippingItem> filteredItems = new ArrayList<>();
			for (ShippingItem shippingItem : shippingItems) {
				if (shippingItem.getQuantity() != null && shippingItem.getQuantity() > 0
						|| shippingItem.getQuantityPiece() != null && shippingItem.getQuantityPiece() > 0) {
					if (shippingItem.getQuantity() == null) {
						shippingItem.setQuantity(0);
					}
					if (shippingItem.getQuantityPiece() == null) {
						shippingItem.setQuantityPiece(0);
					}
					filteredItems.add(shippingItem);
				}
			}
			filteredItems.forEach(filteredItem -> filteredItem.setShipping(entity));
		}
	}

	@Override
	protected void onAfterSave(Shipping entity) {
		super.onAfterSave(entity);

		resetOrderItemQuantity(entity.getOrder());
	}

	@Override
	protected void onDelete(Shipping entity) {
		Order order = entity.getOrder();
		super.onDelete(entity);
		resetOrderItemQuantity(order);
	}

	protected void resetOrderItemQuantity(Order order) {
		Set<Shipping> shippings = order.getShippings();

		Map<String, Integer> totalQuantity = new HashMap<>();
		Map<String, Integer> totalQuantityPiece = new HashMap<>();
		for (Shipping shipping : shippings) {
			List<ShippingItem> shippingItems = shipping.getShippingItems();
			shippingItems.forEach(shippingItem -> {
				Integer q = totalQuantity.get(shippingItem.getSn());
				if (q == null) {
					q = 0;
				}
				q = q + MoreObjects.firstNonNull(shippingItem.getQuantity(), 0);
				totalQuantity.put(shippingItem.getSn(), q);

				Integer qp = totalQuantityPiece.get(shippingItem.getSn());
				if (qp == null) {
					qp = 0;
				}
				qp = qp + MoreObjects.firstNonNull(shippingItem.getQuantityPiece(), 0);
				totalQuantityPiece.put(shippingItem.getSn(), qp);
			});
		}

		totalQuantity.keySet().forEach(sn -> {
			Integer quantity = totalQuantity.get(sn);
			Integer quantityPiece = totalQuantityPiece.get(sn);
			OrderItem orderItem = order.getOrderItem(sn);

			orderItem.setShippedQuantity(quantity);
			orderItem.setShippedQuantityPiece(quantityPiece);
		});

		orderRepository.save(order);
	}
}
