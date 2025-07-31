package com.danger.insurance.accounts.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.accounts.models.dto.AccountsDTO;
import com.danger.insurance.accounts.models.dto.post.AccountEmailUpdateDTO;
import com.danger.insurance.accounts.models.dto.post.AccountPasswordUpdateDTO;
import com.danger.insurance.accounts.models.dto.post.AccountRoleUpdateDTO;
import com.danger.insurance.accounts.models.exceptions.DuplicateEmailException;
import com.danger.insurance.infopages.data.enums.FlashMessages;

@Service
public class AccountsVerifyingServices {
	
	@Autowired
	private AccountsServiceImplementation accountsService;

	public String verifyUpdateFormPost(AccountEmailUpdateDTO emailUpdateDTO, AccountPasswordUpdateDTO passwordUpdateDTO, AccountRoleUpdateDTO roleUpdateDTO, Long accountId, String failRedirect, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", FlashMessages.INVALID_INPUT.getDisplayName());
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updateDTO", bindingResult);
			
			return "redirect:/accounts/user-" + accountId + "/update/" + failRedirect;
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
			
			return "redirect:/accounts/user-" + accountId + "/update/" + failRedirect;
		}
		
		redirectAttributes.addFlashAttribute("success", FlashMessages.ACCOUNT_UPDATED.getDisplayName());
		
		return "redirect:/accounts/user-" + accountId;
	}

}