package com.danger.insurance.accounts.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.danger.insurance.accounts.data.enums.AccountCredentials;
import com.danger.insurance.accounts.models.dto.get.AccountCreateDTO;
import com.danger.insurance.accounts.models.dto.get.AccountDetailsDTO;
import com.danger.insurance.accounts.models.dto.post.AccountEmailUpdateDTO;
import com.danger.insurance.accounts.models.dto.post.AccountPasswordUpdateDTO;
import com.danger.insurance.accounts.models.dto.post.AccountRoleUpdateDTO;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;

@Service
public class AccountsAssigningServices {
	
	@Autowired
	private AccountsServiceImplementation accountsService;
	
	@Autowired
	private AccountsSupportServices accountsCommonSupportServices;

	public String addFindAccountAttributes(String formAction, Boolean isSearchForm, Boolean isDetailForm, Model model) {
		Long notAnProfileView = null;
		assignFormVisualAttributes(isSearchForm, isDetailForm, notAnProfileView, model);
		model.addAttribute("buttonLabel", ButtonLabels.FIND.getDisplayName());
		model.addAttribute("formAction", formAction);
		
		return "pages/accounts/find";
	}
	
	public String addRegisterIndexAttributes(String formAction, Boolean isSearchForm, Boolean isDetailForm, Model model) {
		accountsCommonSupportServices.addRegisterFormAttributes(formAction, model);
    	
    	//
    	if (!model.containsAttribute("accountDTO")) {
    		model.addAttribute("accountDTO", new AccountCreateDTO());
    	}
    	
        return "/pages/accounts/register";
	}
	
	public String assignAccountProfileDetailAttributes(long accountId, Model model) {
		boolean isSearchForm = true;
		boolean isDetailForm = true;
		assignFormVisualAttributes(isSearchForm, isDetailForm, accountId, model);
		model.addAttribute("editEmailLink", "/accounts/user-" + accountId + "/update/email");
		model.addAttribute("editPasswordLink", "/accounts/user-" + accountId + "/update/password");
		model.addAttribute("editRoleLink", "/accounts/user-" + accountId + "/update/role");
		model.addAttribute("removeLink", "/accounts/user-" + accountId + "/remove");
		
		return "pages/accounts/detail";
	}
	
	public String assignUpdateFormAttributes(AccountCredentials credentialToUpdate , FormNames formName, String formAction, Model model) {
		
		//
		if (credentialToUpdate == AccountCredentials.EMAIL) {
			model.addAttribute("updateDTO", new AccountEmailUpdateDTO());
			accountsCommonSupportServices.assignFormVisibilityAttributes(credentialToUpdate, model);
		}
		
		//
		if (credentialToUpdate == AccountCredentials.PASSWORD) {
			model.addAttribute("updateDTO", new AccountPasswordUpdateDTO());
			accountsCommonSupportServices.assignFormVisibilityAttributes(credentialToUpdate, model);
		}
		
		//
		if (credentialToUpdate == AccountCredentials.ROLE) {
			model.addAttribute("updateDTO", new AccountRoleUpdateDTO());
			accountsCommonSupportServices.assignFormVisibilityAttributes(credentialToUpdate, model);
		}

		model.addAttribute("formName", formName.getDisplayName());
		model.addAttribute("formAction", formAction);
		model.addAttribute("buttonLabel", ButtonLabels.CHANGE.getDisplayName());
		
		return "pages/accounts/update";
	}
	
	private void assignFormVisualAttributes(boolean isSearchForm, boolean isDetailForm, Long accountId, Model model) {
		model.addAttribute("formName", FormNames.ACCOUNT_FIND.getDisplayName());
		model.addAttribute("isSearchForm", isSearchForm);
		model.addAttribute("isDetailForm", isDetailForm);
		
		if (accountId != null) {
			model.addAttribute("accountDTO", accountsService.getDetailsById(accountId));
		} else {
			model.addAttribute("accountDTO", new AccountDetailsDTO());
		}
		
	}

}