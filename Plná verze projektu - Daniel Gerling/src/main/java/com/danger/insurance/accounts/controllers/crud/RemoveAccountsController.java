package com.danger.insurance.accounts.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.accounts.models.services.AccountsProcessingServices;

/**
 * Controller class responsible for managing account removal operations.
 * Mapped under the /accounts path, it typically defines endpoints that handle
 * deletion requests for user accounts, either via confirmation views or direct calls.
 */
@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("accounts")
public class RemoveAccountsController {

	@Autowired
	private AccountsProcessingServices processingServices;
	
	/**
     * Handles HTTP GET requests to the /accounts/user-{accountId}/remove endpoint.
     * Initiates the account removal process by identifying the target account via its ID.
     * This method typically prepares the deletion flow — it may present a confirmation view
     * or directly mark the account for removal depending on application rules.
     *
     * URL Mapping:
     * GET /accounts/user-{accountId}/remove
     *
     * @param accountId the unique ID of the account intended for deletion
     * @param redirectAttributes used to pass status messages or feedback after redirect
     * @return the name of the view or redirection route to proceed with removal confirmation
     */
	@GetMapping("user-{accountId}/remove")
	public String handleRemoveAccountRequest(@PathVariable("accountId") long accountId, RedirectAttributes redirectAttributes) {
		return processingServices.processRemoveAccountPost(accountId, redirectAttributes);
	}
	
}