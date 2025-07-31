package com.danger.insurance.accounts.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.accounts.data.entities.AccountsEntity;
import com.danger.insurance.accounts.models.dto.AccountsDTO;
import com.danger.insurance.accounts.models.dto.get.AccountCreateDTO;
import com.danger.insurance.accounts.models.dto.get.AccountDetailsDTO;
import com.danger.insurance.accounts.models.dto.mappers.AccountsMapper;
import com.danger.insurance.accounts.models.exceptions.DuplicateEmailException;
import com.danger.insurance.accounts.models.exceptions.PasswordsDoNotEqualException;
import com.danger.insurance.infopages.data.enums.FlashMessages;

@Service
public class AccountsProcessingServices {
	
	@Autowired
	private AccountsServiceImplementation accountsService;

	@Autowired
	private AccountsMapper accountsMapper;
	
	@Autowired 
	private AccountsSupportServices accountsCommonSupportServices;
	
	public String processAssignSelectAccountListPost(AccountDetailsDTO accountDetailsDTO, RedirectAttributes redirectAttributes, Model model) {
		
		//
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

	public String processRegisterFormPost(AccountCreateDTO userDTO, String formAction, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		
		//
    	if (bindingResult.hasErrors()) {
    		redirectAttributes.addFlashAttribute("accountDTO", userDTO);
    		redirectAttributes.addFlashAttribute("error", FlashMessages.INVALID_INPUT.getDisplayName());
    		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.accountDTO", bindingResult);
    		
            return "redirect:/accounts/register";
    	}
    	
    	//
        try {
        	accountsService.create(accountsMapper.mergeToAccountsDTO(new AccountsDTO(), userDTO), userDTO.getConfirmPassword());
        } catch (DuplicateEmailException e) {
        	bindingResult.rejectValue("userEmail", "error", FlashMessages.EMAIL_DUPLICATE.getDisplayName());
        	model.addAttribute("error", FlashMessages.EMAIL_DUPLICATE.getDisplayName());
        	accountsCommonSupportServices.addRegisterFormAttributes(formAction, model);
            return "pages/accounts/register";
        } catch (PasswordsDoNotEqualException e) {
        	bindingResult.rejectValue("userPassword", "error", "Prosím zadejte jinou E-mailovou adresu");
        	bindingResult.rejectValue("confirmPassword", "error", "Prosím zadejte schodná hesla kvůli kontrole");
        	model.addAttribute("error", FlashMessages.PASSWORDS_MISMATCH.getDisplayName());
        	accountsCommonSupportServices.addRegisterFormAttributes(formAction, model);
            return "pages/accounts/register";
        }

        redirectAttributes.addFlashAttribute("success", FlashMessages.ACCOUNT_CREATED.getDisplayName());
        return "redirect:/accounts";
	}
	
	public String processRemoveAccountPost(long accountId, RedirectAttributes redirectAttributes) {
		accountsService.delete(accountId);
		redirectAttributes.addFlashAttribute("success", FlashMessages.ACCOUNT_REMOVED.getDisplayName());
		
		return "redirect:/accounts";
	}
	
}