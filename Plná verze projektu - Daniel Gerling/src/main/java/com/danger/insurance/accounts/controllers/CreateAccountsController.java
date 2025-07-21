package com.danger.insurance.accounts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.accounts.models.dto.AccountCreateDTO;
import com.danger.insurance.accounts.models.dto.AccountsDTO;
import com.danger.insurance.accounts.models.dto.mappers.AccountsMapper;
import com.danger.insurance.accounts.models.exceptions.DuplicateEmailException;
import com.danger.insurance.accounts.models.exceptions.PasswordsDoNotEqualException;
import com.danger.insurance.accounts.models.services.AccountsServiceImplementation;

import jakarta.validation.Valid;

@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("account")
public class CreateAccountsController {

	@Autowired
    private AccountsServiceImplementation accountsService;
	
	@Autowired
	private AccountsMapper accountsMapper;
	
    @GetMapping("register")
    public String renderRegister(Model model) {
    	model.addAttribute("accountDTO", new AccountCreateDTO());
    	
        return "/pages/accounts/register";
    }
    
    @PostMapping("register")
    public String register(@Valid @ModelAttribute("accountDTO") AccountCreateDTO userDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        
    	//
    	if (result.hasErrors())
            return "redirect:/account/register";

    	//
        try {
        	accountsService.create(accountsMapper.mergeToAccountsDTO(new AccountsDTO(), userDTO), userDTO.getConfirmPassword());
        } catch (DuplicateEmailException e) {
            result.rejectValue("email", "error", "E-mail je již zaregistrován.");
            return "/pages/account/register";
        } catch (PasswordsDoNotEqualException e) {
            result.rejectValue("password", "error", "Hesla se neschodují.");
            result.rejectValue("confirmPassword", "error", "Hesla se neschodují.");
            return "/pages/account/register";
        }

        redirectAttributes.addFlashAttribute("success", "User has been registered.");
        return "redirect:/";
    }
    
}