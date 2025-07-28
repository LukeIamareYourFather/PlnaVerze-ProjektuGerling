package com.danger.insurance.accounts.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.accounts.data.enums.AccountCredentials;
import com.danger.insurance.accounts.models.dto.AccountsDTO;
import com.danger.insurance.accounts.models.dto.post.AccountEmailUpdateDTO;
import com.danger.insurance.accounts.models.dto.post.AccountPasswordUpdateDTO;
import com.danger.insurance.accounts.models.dto.post.AccountRoleUpdateDTO;
import com.danger.insurance.accounts.models.exceptions.DuplicateEmailException;
import com.danger.insurance.accounts.models.services.AccountsServiceImplementation;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.validation.groups.OnUpdateAccount;

@Controller
@RequestMapping("accounts")
public class UpdateAccountsController {

	@Autowired
	private AccountsServiceImplementation accountsService;
	
	@GetMapping("user-{accountId}/update/email") 
	public String renderUpdateAccountEmailForm(@PathVariable("accountId") long accountId, Model model) {
		return assignUpdateFormAttributes(AccountCredentials.EMAIL, FormNames.EMAIL_UPDATE, "email/process", model);
	}
	
	@PostMapping("user-{accountId}/update/email/process")
	public String processUpdateAccountEmailFormPost(@PathVariable("accountId") long accountId, @Validated(OnUpdateAccount.class) @ModelAttribute("updateDTO") AccountEmailUpdateDTO emailUpdateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return validateUpdateFormPost(emailUpdateDTO, null, null, accountId, "email", bindingResult, redirectAttributes);
	}
	
	@GetMapping("user-{accountId}/update/password") 
	public String renderUpdateAccountPasswordForm(@PathVariable("accountId") long accountId, Model model) {
		return assignUpdateFormAttributes(AccountCredentials.PASSWORD, FormNames.PASSWORD_UPDATE, "password/process", model);
	}
	
	@PostMapping("user-{accountId}/update/password/process")
	public String processUpdateAccountPasswordFormPost(@PathVariable("accountId") long accountId, @Validated(OnUpdateAccount.class) @ModelAttribute("updateDTO") AccountPasswordUpdateDTO passwordUpdateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return validateUpdateFormPost(null, passwordUpdateDTO, null, accountId, "password", bindingResult, redirectAttributes);
	}
	
	@GetMapping("user-{accountId}/update/role") 
	public String renderUpdateAccountRoleForm(@PathVariable("accountId") long accountId, Model model) {
		return assignUpdateFormAttributes(AccountCredentials.ROLE, FormNames.EMAIL_UPDATE, "role/process", model);
	}
	
	@PostMapping("user-{accountId}/update/role/process")
	public String processUpdateAccountRoleFormPost(@PathVariable("accountId") long accountId, @Validated(OnUpdateAccount.class) @ModelAttribute("updateDTO") AccountRoleUpdateDTO roleUpdateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return validateUpdateFormPost(null, null, roleUpdateDTO, accountId, "role", bindingResult, redirectAttributes);
	}
	
	private String validateUpdateFormPost(AccountEmailUpdateDTO emailUpdateDTO, AccountPasswordUpdateDTO passwordUpdateDTO, AccountRoleUpdateDTO roleUpdateDTO, Long accountId, String redirectLink, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", FlashMessages.INVALID_INPUT.getDisplayName());
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updateDTO", bindingResult);
			
			return "redirect:/accounts/user-" + accountId + "/update/" + redirectLink;
		}
		
		AccountsDTO updateDTO = accountsService.getAccountsDTOById(accountId);
		boolean ifToUpdatePassword = false;
		
		if (passwordUpdateDTO != null) {
			
			if (!passwordUpdateDTO.getUserPassword().equals(passwordUpdateDTO.getConfirmPassword())) {
				redirectAttributes.addFlashAttribute("error", FlashMessages.PASSWORDS_MISMATCH.getDisplayName());
				
				return "redirect:/accounts/user-" + accountId + "/update/password";
			}
			
			updateDTO.setUserPassword(passwordUpdateDTO.getUserPassword());
			ifToUpdatePassword = true;
		} else if (emailUpdateDTO != null) {
			updateDTO.setUserEmail(emailUpdateDTO.getUserEmail());
		} else {
			updateDTO.setUserRole(roleUpdateDTO.getUserRole());
		}
		
		//
		try {
			accountsService.edit(updateDTO, ifToUpdatePassword);
		} catch (DuplicateEmailException e) {
			redirectAttributes.addFlashAttribute("error", FlashMessages.EMAIL_DUPLICATE.getDisplayName());
			
			return "redirect:/accounts/user-" + accountId + "/update/" + redirectLink;
		}
		
		redirectAttributes.addFlashAttribute("success", FlashMessages.ACCOUNT_UPDATED.getDisplayName());
		
		return "redirect:/accounts/user-" + accountId;
	}
	
	private String assignUpdateFormAttributes(AccountCredentials credentialToUpdate , FormNames formName, String formAction, Model model) {
		
		//
		if (credentialToUpdate == AccountCredentials.EMAIL) {
			model.addAttribute("updateDTO", new AccountEmailUpdateDTO());
			assignFormVisibilityAttributes(credentialToUpdate, model);
		}
		
		//
		if (credentialToUpdate == AccountCredentials.PASSWORD) {
			model.addAttribute("updateDTO", new AccountPasswordUpdateDTO());
			assignFormVisibilityAttributes(credentialToUpdate, model);
		}
		
		//
		if (credentialToUpdate == AccountCredentials.ROLE) {
			model.addAttribute("updateDTO", new AccountRoleUpdateDTO());
			assignFormVisibilityAttributes(credentialToUpdate, model);
		}

		model.addAttribute("formName", formName.getDisplayName());
		model.addAttribute("formAction", formAction);
		model.addAttribute("buttonLabel", ButtonLabels.CHANGE.getDisplayName());
		
		return "pages/accounts/update";
	}
	
	private void assignFormVisibilityAttributes(AccountCredentials credentialToUpdate, Model model) {
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