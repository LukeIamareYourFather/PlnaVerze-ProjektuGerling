package com.danger.insurance.accounts.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("accounts")
public class DisplayAccountsController {

	@GetMapping
	public String renderIndex() {
		return "pages/accounts/index";
	}
	
}
