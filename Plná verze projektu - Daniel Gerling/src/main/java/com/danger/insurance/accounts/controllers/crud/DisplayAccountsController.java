package com.danger.insurance.accounts.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.danger.insurance.accounts.models.services.AccountsAssigningServices;
import com.danger.insurance.shared.enums.ActivePageTokens;
import com.danger.insurance.shared.services.CommonSupportServiceShared;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller class responsible for displaying account-related information.
 * Handles HTTP requests under the /accounts path and defines endpoints
 * for rendering views that present account details or summaries to users.
 */
@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("accounts")
public class DisplayAccountsController {

	@Autowired
	private CommonSupportServiceShared supportServiceShared;
	
	@Autowired
	private AccountsAssigningServices assigningServices;
	
    /**
     * Handles HTTP GET requests to the base /accounts endpoint.
     * This method renders the main index or dashboard view related to accounts.
     * It may also perform initial session setup or clearing, populate the model
     * with relevant data, and provide access to available account actions.
     *
     * Common responsibilities:
     * - Interpret session status or request parameters for routing logic
     * - Prepare introductory view content (e.g. summaries, welcome text)
     * - Reset session attributes if needed (using SessionStatus)
     *
     * URL Mapping:
     * GET /accounts
     *
     * @param httpRequest the HTTP request object containing client request data
     * @param sessionStatus used to check or clear session-scoped attributes
     * @param model the Spring Model used to pass account data to the view layer
     * @return the name of the view template that serves as the account index or landing page
     */
	@GetMapping
	public String renderIndex(HttpServletRequest httpRequest, SessionStatus sessionStatus, Model model) {
		supportServiceShared.removeAllSessionAttributes(ActivePageTokens.ACCOUNTS, httpRequest, sessionStatus, model);
		
		return "pages/accounts/index";
	}
	
    /**
     * Handles HTTP GET requests to the /accounts/user-{accountId} endpoint.
     * Responsible for rendering the account profile view based on the provided account ID.
     * This method retrieves specific account details and passes them to the view layer
     * for display, allowing users to see individual profile information.
     *
     * URL Mapping:
     * GET /accounts/user-{accountId}
     *
     * @param accountId the unique identifier of the account to be displayed
     * @param model the Spring Model used to pass account data to the view
     * @return the name of the view template that presents the account profile details
     */
	@GetMapping("user-{accountId}")
	public String renderAccountsProfileDetails(@PathVariable("accountId") long accountId, Model model) {
		return assigningServices.assignAccountProfileDetailAttributes(accountId, model);
	}
	
}
