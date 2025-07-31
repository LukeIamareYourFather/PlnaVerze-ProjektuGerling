package com.danger.insurance.accounts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.accounts.models.dto.get.AccountDetailsDTO;
import com.danger.insurance.accounts.models.services.AccountsAssigningServices;
import com.danger.insurance.accounts.models.services.AccountsProcessingServices;

/**
 * Controller class responsible for handling account search operations.
 * Part of the Spring Boot web layer, this class typically exposes endpoints
 * that allow clients to find accounts based on various criteria such as ID,
 * username, email, or other attributes.
 *
 */
@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("accounts")
public class FindAccountsController {
	
	@Autowired 
	private AccountsAssigningServices assigningServices;
	
	@Autowired
	private AccountsProcessingServices processingServices;

    /**
     * Handles HTTP GET requests to the /accounts/find endpoint.
     * Renders the form view that allows users to input search criteria
     * for locating specific accounts (e.g. by username, email, or ID).
     *
     * This method typically adds necessary attributes to the model,
     * such as an empty account object or default search parameters.
     *
     * URL Mapping:
     * GET /accounts/find
     *
     * @param model the Spring Model object used to pass data to the view layer.
     * @return the name of the view template that displays the account search form.
     */
	@GetMapping("find")
	public String renderFindAccountForm(Model model) {
		String formAction = "find/select";
		boolean isSearchForm = true;
		boolean isDetailForm = false;
		
		return assigningServices.addFindAccountAttributes(formAction, isSearchForm, isDetailForm, model);
	}
	
	/**
     * Handles HTTP POST requests to the /accounts/find/select endpoint.
     * This method processes the submitted account search form, extracts the
     * search criteria from the AccountDetailsDTO, and retrieves a list of matching accounts.
     * It then adds the result list to the model and prepares any status messages
     * using RedirectAttributes for post-redirect feedback.
     *
     * This is typically part of a search workflow where users first enter search
     * criteria and then view a filtered list of accounts that meet those criteria.
     *
     * URL Mapping:
     * POST /accounts/find/select
     *
     * @param accountDetailsDTO the DTO containing search criteria input from the form
     * @param model the Spring Model used to pass results to the view layer
     * @param redirectAttributes used to pass status messages or feedback after redirect
     * @return the name of the view template displaying the filtered account list
     */
	@PostMapping("find/select")
	public String renderSelectAccountList(@ModelAttribute("accountDTO") AccountDetailsDTO accountDetailsDTO, Model model, RedirectAttributes redirectAttributes) {
		return processingServices.processAssignSelectAccountListPost(accountDetailsDTO, redirectAttributes, model);
	}
	
}