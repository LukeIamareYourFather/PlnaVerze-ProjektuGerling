package com.danger.insurance.accounts.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.accounts.models.dto.AccountsDTO;
import com.danger.insurance.accounts.models.dto.get.AccountCreateDTO;
import com.danger.insurance.accounts.models.dto.mappers.AccountsMapper;
import com.danger.insurance.accounts.models.exceptions.DuplicateEmailException;
import com.danger.insurance.accounts.models.exceptions.PasswordsDoNotEqualException;
import com.danger.insurance.accounts.models.services.AccountsServiceImplementation;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.validation.groups.OnCreateAccount;

@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("accounts")
public class CreateAccountsController {

	@Autowired
    private AccountsServiceImplementation accountsService;
	
	@Autowired
	private AccountsMapper accountsMapper;
	
    @GetMapping("register")
    public String renderRegister(Model model) {
    	addRegisterFormAttributes(model);
    	
    	//
    	if (!model.containsAttribute("accountDTO")) {
    		model.addAttribute("accountDTO", new AccountCreateDTO());
    	}
    	
        return "/pages/accounts/register";
    }
    
    @PostMapping("register")
    public String register(@Validated(OnCreateAccount.class) @ModelAttribute("accountDTO") AccountCreateDTO userDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        
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
        	addRegisterFormAttributes(model);
            return "pages/accounts/register";
        } catch (PasswordsDoNotEqualException e) {
        	bindingResult.rejectValue("userPassword", "error", "Prosím zadejte jinou E-mailovou adresu");
        	bindingResult.rejectValue("confirmPassword", "error", "Prosím zadejte schodná hesla kvůli kontrole");
        	model.addAttribute("error", FlashMessages.PASSWORDS_MISMATCH.getDisplayName());
        	addRegisterFormAttributes(model);
            return "pages/accounts/register";
        }

        redirectAttributes.addFlashAttribute("success", FlashMessages.ACCOUNT_CREATED.getDisplayName());
        return "redirect:/accounts";
    }
    
    private void addRegisterFormAttributes(Model model) {
    	model.addAttribute("formName", FormNames.ACCOUNT_CREATE.getDisplayName());
    	model.addAttribute("buttonLabel", ButtonLabels.CREATE.getDisplayName());
		model.addAttribute("formAction", "register");
		model.addAttribute("isSearchForm", false);
		model.addAttribute("isDetailForm", false);
	}
    
}