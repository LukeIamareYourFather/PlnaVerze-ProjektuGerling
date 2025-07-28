package com.danger.insurance.accounts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.accounts.data.entities.AccountsEntity;
import com.danger.insurance.accounts.models.dto.get.AccountDetailsDTO;
import com.danger.insurance.accounts.models.services.AccountsServiceImplementation;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;

@Controller
@RequestMapping("accounts")
public class FindAccountsController {
	
	@Autowired
	private AccountsServiceImplementation accountsService;

	@GetMapping("find")
	public String renderFindAccountForm(Model model) {
		model.addAttribute("accountDTO", new AccountDetailsDTO());
		model.addAttribute("formName", FormNames.ACCOUNT_FIND.getDisplayName());
		model.addAttribute("buttonLabel", ButtonLabels.FIND.getDisplayName());
		model.addAttribute("formAction", "find/select");
		model.addAttribute("isSearchForm", true);
		model.addAttribute("isDetailForm", false);
		
		return "pages/accounts/find";
	}
	
	@PostMapping("find/select")
	public String renderSelectAccountList(@ModelAttribute("accountDTO") AccountDetailsDTO accountDetailsDTO, Model model, RedirectAttributes redirectAttributes) {
		
		if (accountDetailsDTO.getUserEmail().equals("") && accountDetailsDTO.getUserRole() == null) {
			redirectAttributes.addFlashAttribute("error", FlashMessages.MISSING_INPUT.getDisplayName());
			
			return "redirect:/accounts/find";
		}
		
		List<AccountsEntity> foundAccounts = accountsService.findAccountIds(accountDetailsDTO);
		
		//
		if (foundAccounts.isEmpty()) {
			return "redirect:/accounts/not-found";
		}
		
		model.addAttribute("accountsList", foundAccounts);
		model.addAttribute("referenceLink", "/accounts/user-");
		
		return "pages/accounts/list";
	}
	
}