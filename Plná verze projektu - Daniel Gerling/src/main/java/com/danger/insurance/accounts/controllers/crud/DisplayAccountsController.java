package com.danger.insurance.accounts.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.accounts.models.services.AccountsServiceImplementation;
import com.danger.insurance.infopages.data.enums.FormNames;

@PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("accounts")
public class DisplayAccountsController {

	@Autowired
	private AccountsServiceImplementation accountsService;
	
	@GetMapping
	public String renderIndex() {
		return "pages/accounts/index";
	}
	
	@GetMapping("user-{accountId}")
	public String renderAccountsProfileDetails(@PathVariable("accountId") long accountId, Model model) {
		model.addAttribute("formName", FormNames.ACCOUNT_FIND.getDisplayName());
		model.addAttribute("accountDTO", accountsService.getDetailsById(accountId));
		model.addAttribute("isSearchForm", true);
		model.addAttribute("isDetailForm", true);
		model.addAttribute("editEmailLink", "/accounts/user-" + accountId + "/update/email");
		model.addAttribute("editPasswordLink", "/accounts/user-" + accountId + "/update/password");
		model.addAttribute("editRoleLink", "/accounts/user-" + accountId + "/update/role");
		model.addAttribute("removeLink", "/accounts/user-" + accountId + "/remove");
		
		return "pages/accounts/detail";
	}
	
}
