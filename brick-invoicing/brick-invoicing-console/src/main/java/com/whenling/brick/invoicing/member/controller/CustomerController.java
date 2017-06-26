package com.whenling.brick.invoicing.member.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.whenling.brick.console.support.CrudController;
import com.whenling.brick.invoicing.member.entity.Address;
import com.whenling.brick.invoicing.member.entity.Customer;
import com.whenling.brick.invoicing.member.entity.DeliveryCenter;
import com.whenling.brick.invoicing.member.entity.QDeliveryCenter;
import com.whenling.brick.invoicing.member.repo.DeliveryCenterRepository;
import com.whenling.castle.repo.domain.Result;
import com.whenling.castle.repo.domain.SortNoComparator;

@Controller
@RequestMapping("/customer")
public class CustomerController extends CrudController<Customer, Long> {

	@Autowired
	private DeliveryCenterRepository deliveryCenterRepository;
	
	public CustomerController() {
		super(true);
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<Customer> doPage(Predicate predicate, Pageable pageable) {
		return getRepository().findAll(predicate, pageable);
	}

	@Override
	protected void onShowEditPage(Customer entity, Model model) {
		super.onShowEditPage(entity, model);
		entity.getAddresses().sort(SortNoComparator.COMPARATOR);
	}

	@Override
	protected void onBeforeSave(Customer entity) {
		super.onBeforeSave(entity);

		Integer addressCount = getParameter("addressCount", Integer.class, 0);
		entity.setAddresses(entity.getAddresses().subList(0, addressCount));
	}

	@RequestMapping(value = "/delivery/{id}", method = RequestMethod.GET)
	public String delivery(@PathVariable("id") Customer entity, Model model) {
		model.addAttribute("entity", entity);
		return getBaseTemplatePath() + "/delivery";
	}

	@RequestMapping(value = "/address", method = RequestMethod.GET)
	@ResponseBody
	public List<Address> getAddress(@RequestParam("id") Customer entity) {
		return entity.getAddresses();
	}

	@RequestMapping(value = "/submitDeliveryCenters", method = RequestMethod.POST)
	@ResponseBody
	public Result doAddDelivery(@RequestParam("id") Customer entity, @RequestParam("deliveryCenters[]") Set<DeliveryCenter> deliveryCenters,
			Model model) {
		entity.getDeliveryCenters().addAll(deliveryCenters);
		getRepository().save(entity);
		return Result.success();
	}

	@RequestMapping(value = "/delivery/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<DeliveryCenter> deliveryPage(@RequestParam("customer") Customer entity) {
		return new PageImpl<>(Lists.newArrayList(entity.getDeliveryCenters()));
	}

	@RequestMapping(value = "/delivery/select", method = RequestMethod.POST)
	@ResponseBody
	public Page<DeliveryCenter> deliverySelectPage(Predicate predicate, Pageable pageable, @RequestParam("customer") Customer entity) {
		BooleanBuilder booleanBuilder = new BooleanBuilder(predicate);
		booleanBuilder.and(QDeliveryCenter.deliveryCenter.notIn(entity.getDeliveryCenters()));
		return deliveryCenterRepository.findAll(booleanBuilder, pageable);
	}

}
