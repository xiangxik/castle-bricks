package com.whenling.brick.console.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.brick.base.other.Setting;
import com.whenling.brick.console.support.BaseController;
import com.whenling.brick.web.mail.MailService;
import com.whenling.brick.web.setting.SettingUtils;
import com.whenling.castle.repo.domain.Result;

@Controller
@RequestMapping("/setting")
public class SettingController extends BaseController {

	@Autowired
	private MailService mailService;

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String show(Model model) {
		return "classpath:/setting";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid Setting setting, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError().addProperties("data", bindingResult.getAllErrors());
		}

		SettingUtils.set(setting);

		return Result.success();
	}

	@RequestMapping(value = "/mail_test", method = RequestMethod.POST)
	@ResponseBody
	public Result doMailTest(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername,
			String smtpPassword, String toMail) {
		try {
			mailService.send(smtpFromMail, smtpHost, smtpPort, smtpUsername, smtpPassword, toMail, "测试邮件", "该邮件仅用于测试",
					null, false);
		} catch (MailSendException e) {
			return Result.failure();
		}

		return Result.success();
	}

}
