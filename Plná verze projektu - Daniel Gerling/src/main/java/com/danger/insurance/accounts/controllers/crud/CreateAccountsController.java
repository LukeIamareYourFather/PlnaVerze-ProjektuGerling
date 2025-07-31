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

import com.danger.insurance.accounts.models.dto.get.AccountCreateDTO;
import com.danger.insurance.accounts.models.services.AccountsAssigningServices;
import com.danger.insurance.accounts.models.services.AccountsProcessingServices;
import com.danger.insurance.validation.groups.OnCreateAccount;

/**
 * Controller class responsible for creating new accounts.
 * Mapped to handle HTTP requests under the /accounts path, this class
 * typically contains methods for displaying account creation forms,
 * validating user input, and persisting new account data.
 */
@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("accounts")
public class CreateAccountsController {
	
	@Autowired
	private AccountsAssigningServices assigningServices;
	
	@Autowired
	private AccountsProcessingServices processingServices;
	
	/**
     * Handles HTTP GET requests to the /accounts/register endpoint.
     * This method renders the account registration form view,
     * allowing users to input necessary details to create a new account.
     *
     * It typically prepares and adds attributes to the model, such as
     * an empty account DTO or form defaults, which the view binds to.
     *
     * URL Mapping:
     * GET /accounts/register
     *
     * @param model the Spring Model used to pass form-related data to the view layer.
     * @return the name of the view template that displays the registration form.
     */
    @GetMapping("register")
    public String renderRegisterForm(Model model) {
    	String formAction = "register";
    	boolean isSearchFrom = false;
    	boolean isDetailForm = false;
    	
    	return assigningServices.addRegisterIndexAttributes(formAction, isSearchFrom, isDetailForm, model);
    }
    
    /**
     * Handles HTTP POST requests to the /accounts/register endpoint.
     * Processes the submitted registration form containing user account data.
     * Performs validation based on the OnCreateAccount validation group,
     * checks for binding errors, and either displays validation messages
     * or proceeds with account creation logic.
     *
     * Responsibilities:
     * - Validate form input using annotations and specified validation group
     * - Handle any binding errors and return to the registration view if errors exist
     * - Save new account data via service layer if validation passes
     * - Use RedirectAttributes to pass success/failure messages post-redirect
     *
     * URL Mapping:
     * POST /accounts/register
     *
     * @param userDTO the data transfer object carrying user-submitted registration info
     * @param bindingResult object that holds the results of the validation and binding
     * @param model the Spring Model to pass data to the view in case of validation failure
     * @param redirectAttributes used to pass flash messages after redirect
     * @return the logical view name, typically the form (on failure) or a confirmation page (on success)
     */
    @PostMapping("register")
    public String handleRegisterFormPost(@Validated(OnCreateAccount.class) @ModelAttribute("accountDTO") AccountCreateDTO userDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        String formAction = "register";
    	
    	return processingServices.processRegisterFormPost(userDTO, formAction, bindingResult, redirectAttributes, model);
    }
    
}