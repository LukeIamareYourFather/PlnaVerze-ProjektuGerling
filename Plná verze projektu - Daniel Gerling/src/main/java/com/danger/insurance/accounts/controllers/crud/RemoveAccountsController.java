package com.danger.insurance.accounts.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.accounts.models.services.AccountsServiceImplementation;
import com.danger.insurance.infopages.data.enums.FlashMessages;

@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("accounts")
public class RemoveAccountsController {

	@Autowired
	private AccountsServiceImplementation accountsService;
	
	@GetMapping("user-{accountId}/remove")
	public String handleRemoveAccountRequest(@PathVariable("accountId") long accountId, RedirectAttributes redirectAttributes) {
		accountsService.delete(accountId);
		redirectAttributes.addFlashAttribute("success", FlashMessages.ACCOUNT_REMOVED.getDisplayName());
		
		return "redirect:/accounts";
	}
	
}