package com.danger.insurance.accounts.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("account")
public class RemoveAccountsController {

	@GetMapping("delete")
	public String handleRemoveAccountRequest() {
		return "redirect:/account";
	}
}