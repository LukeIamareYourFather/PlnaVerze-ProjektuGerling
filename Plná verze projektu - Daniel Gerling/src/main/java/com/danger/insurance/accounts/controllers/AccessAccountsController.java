package com.danger.insurance.accounts.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("accounts")
public class AccessAccountsController {

	@GetMapping("log-in") 
	public String renderLogInForm() {
		return "pages/accounts/login";
	}
	
	@GetMapping("log-out")
	public String handleLogOutRequest() {
		return "pages/accounts/logout";
	}
	
}