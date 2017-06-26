package com.whenling.brick.invoicing.member.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.types.Predicate;
import com.whenling.brick.console.support.CrudController;
import com.whenling.brick.invoicing.member.entity.Supplier;
import com.whenling.castle.repo.domain.SortNoComparator;

@Controller
@RequestMapping("/supplier")
public class SupplierController extends CrudController<Supplier, Long> {

	public SupplierController() {
		super(true);
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<Supplier> doPage(Predicate predicate, Pageable pageable) {
		return getRepository().findAll(predicate, pageable);
	}

	@Override
	protected void onShowEditPage(Supplier entity, Model model) {
		super.onShowEditPage(entity, model);
		entity.getAddresses().sort(SortNoComparator.COMPARATOR);
	}

	@Override
	protected void onBeforeSave(Supplier entity) {
		super.onBeforeSave(entity);

		Integer addressCount = getParameter("addressCount", Integer.class, 0);
		entity.setAddresses(entity.getAddresses().subList(0, addressCount));
	}
}
