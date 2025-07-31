package com.danger.insurance.accounts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.shared.services.RequiredDataInjecterService;

/**
 * Controller class for handling account-related access operations.
 * This class is part of the web layer in a Spring Boot application.
 */

@Controller
@RequestMapping("accounts")
public class AccessAccountsController {

	@Autowired
	private RequiredDataInjecterService requiredDataInjecter;
	
	/**
     * Handles HTTP GET requests to the /accounts/log-in endpoint.
     * This method is responsible for rendering the login form view,
     * allowing users to enter their credentials.
     *
     * URL Mapping:
     * GET /accounts/log-in
     *
     * @return the name of the view template that displays the login form.
     */
	@GetMapping("log-in") 
	public String renderLogInForm() {
		requiredDataInjecter.injectRequiredAccountIfMissing();
		
		return "pages/accounts/login";
	}
	
	/**
     * Handles HTTP GET requests to the /accounts/log-out endpoint.
     * This method initiates the logout process for an authenticated user,
     * typically by invalidating the session or clearing security context.
     * It then redirects the user to a post-logout view, such as a login page
     * or a generic logged-out confirmation page.
     *
     * URL Mapping:
     * GET /accounts/log-out
     *
     * @return the name of the view template shown after logout.
     */
	@GetMapping("log-out")
	public String handleLogOutRequest() {
		return "pages/accounts/logout";
	}
	
}