package com.danger.insurance.accounts.models.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.danger.insurance.accounts.data.enums.AccountCredentials;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;

@Service
public class AccountsSupportServices {
	
	public void addRegisterFormAttributes(String formAction, Model model) {
    	model.addAttribute("formName", FormNames.ACCOUNT_CREATE.getDisplayName());
    	model.addAttribute("buttonLabel", ButtonLabels.CREATE.getDisplayName());
		model.addAttribute("formAction", formAction);
		model.addAttribute("isSearchForm", false);
		model.addAttribute("isDetailForm", false);
	}
	
	public void assignFormVisibilityAttributes(AccountCredentials credentialToUpdate, Model model) {
		String[] attributeNames = {"isEmailForm", "isPasswordForm", "isRoleForm"};
		Boolean[] attributeValues = determineVisibilityAttributes(credentialToUpdate);
		
		for (int i = 0; i < attributeNames.length; i++) {
			model.addAttribute(attributeNames[i], attributeValues[i]);
		}
		
	}
	
	private Boolean[] determineVisibilityAttributes(AccountCredentials credentialToUpdate) {
		Boolean[] visibilityAttributes = {false, false, false};
		
		if (credentialToUpdate == AccountCredentials.EMAIL) {
			visibilityAttributes[0] = true;
		} else if (credentialToUpdate == AccountCredentials.PASSWORD) {
			visibilityAttributes[1] = true;
		} else {
			visibilityAttributes[2] = true;
		}
		
		return visibilityAttributes;
	}
	
}