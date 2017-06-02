package com.whenling.brick.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.whenling.brick.base.entity.Admin;
import com.whenling.brick.base.repo.AdminRepository;
import com.whenling.brick.web.menu.MenuUtils;
import com.whenling.brick.web.setting.SettingUtils;
import com.whenling.castle.integration.ApplicationInitializer;

@Component
@Order(0)
public class ConsoleInitializer extends ApplicationInitializer {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ThymeleafViewResolver thymeleafViewResolver;

	@Override
	public void onRootContextRefreshed() {
		if (adminRepository.count() == 0) {
			Admin admin = new Admin();
			admin.setName("管理员");
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("asd123"));
			adminRepository.save(admin);
		}

		MenuUtils.register("console", "menu-console.xml");
		MenuUtils.register("shortcut", "menu-shortcut.xml");
	}

	@Override
	public void onServletContextRefreshed() {
		thymeleafViewResolver.addStaticVariable("setting", SettingUtils.get());
		thymeleafViewResolver.addStaticVariable("consoleMenu", MenuUtils.get("console"));
		thymeleafViewResolver.addStaticVariable("shortcutMenu", MenuUtils.get("shortcut"));
	}

}