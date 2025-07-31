package com.danger.insurance.accounts.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.danger.insurance.accounts.models.dto.post.AccountEmailUpdateDTO;
import com.danger.insurance.accounts.models.dto.post.AccountPasswordUpdateDTO;
import com.danger.insurance.accounts.models.dto.post.AccountRoleUpdateDTO;
import com.danger.insurance.accounts.models.services.AccountsAssigningServices;
import com.danger.insurance.accounts.models.services.AccountsVerifyingServices;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.validation.groups.OnUpdateAccount;

/**
 * Controller class responsible for handling account update operations.
 * Mapped under the /accounts path, this class defines endpoints for retrieving
 * account data for editing and submitting modifications to existing accounts.
 */
@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("accounts")
public class UpdateAccountsController {
	
	@Autowired
	private AccountsAssigningServices assigningServices;
	
	@Autowired
	private AccountsVerifyingServices verifyingServices;
	
	/**
     * Handles HTTP GET requests to the /accounts/user-{accountId}/update/email endpoint.
     * Renders the email update form view for the specified account, allowing the user
     * to modify their registered email address.
     *
     * This method typically loads the current account data into the model so the form
     * can be pre-populated with the existing email address, ensuring a smoother UX.
     *
     * URL Mapping:
     * GET /accounts/user-{accountId}/update/email
     *
     * @param accountId the unique identifier of the account to be edited
     * @param model the Spring Model used to pass account data to the view
     * @return the name of the view template that displays the email update form
     */
	@GetMapping("user-{accountId}/update/email") 
	public String renderUpdateAccountEmailForm(@PathVariable("accountId") long accountId, Model model) {
		String formAction = "email/process";
		
		return assigningServices.assignUpdateFormAttributes(AccountCredentials.EMAIL, FormNames.EMAIL_UPDATE, formAction, model);
	}
	
	/**
     * Handles HTTP POST requests to /accounts/user-{accountId}/update/email/process.
     * Processes the submitted email update form for the specified account ID.
     * Performs validation using the OnUpdateAccount group, checks for binding errors,
     * and either returns to the form view or executes the email update operation.
     * Provides feedback via flash attributes to be shown after redirect.
     *
     * URL Mapping:
     * POST /accounts/user-{accountId}/update/email/process
     *
     * @param accountId the unique identifier of the account to be updated
     * @param emailUpdateDTO the DTO containing the updated email address submitted by the user
     * @param bindingResult holds the results of form validation and binding errors
     * @param redirectAttributes used to pass success or error messages after redirect
     * @return the logical view name or redirect route based on validation outcome
     */
	@PostMapping("user-{accountId}/update/email/process")
	public String processUpdateAccountEmailFormPost(@PathVariable("accountId") long accountId, @Validated(OnUpdateAccount.class) @ModelAttribute("updateDTO") AccountEmailUpdateDTO emailUpdateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String failRedirect = "email";
		AccountPasswordUpdateDTO passwordUpdateDTO = null;
		AccountRoleUpdateDTO roleUpdateDTO = null;
		
		return verifyingServices.verifyUpdateFormPost(emailUpdateDTO, passwordUpdateDTO, roleUpdateDTO, accountId, failRedirect, bindingResult, redirectAttributes);
	}
	
	/**
     * Handles HTTP GET requests to the /accounts/user-{accountId}/update/password endpoint.
     * Renders the password update form for the specified account, enabling the user
     * to securely modify their current password.
     *
     * This method typically prepares the model with any necessary attributes,
     * such as a password update DTO or security prompts, and directs the user
     * to the appropriate view for input.
     *
     * URL Mapping:
     * GET /accounts/user-{accountId}/update/password
     *
     * @param accountId the unique identifier of the account whose password is being updated
     * @param model the Spring Model used to pass form data to the view layer
     * @return the name of the view template that displays the password update form
     */
	@GetMapping("user-{accountId}/update/password") 
	public String renderUpdateAccountPasswordForm(@PathVariable("accountId") long accountId, Model model) {
		String formAction = "password/process";
		
		return assigningServices.assignUpdateFormAttributes(AccountCredentials.PASSWORD, FormNames.PASSWORD_UPDATE, formAction, model);
	}
	
	/**
     * Handles HTTP POST requests to /accounts/user-{accountId}/update/password/process.
     * Processes the submitted password update form for the specified account ID.
     * Validates the provided password using the OnUpdateAccount validation group,
     * checks for binding errors, and either redisplays the form with error messages
     * or proceeds to securely update the account's password.
     *
     * Responsibilities:
     * - Enforce password validation rules (e.g. strength, confirmation)
     * - Handle validation and binding errors gracefully
     * - Perform the password update operation via the service layer
     * - Provide user feedback through redirect flash attributes
     *
     * URL Mapping:
     * POST /accounts/user-{accountId}/update/password/process
     *
     * @param accountId the unique identifier of the account to update
     * @param passwordUpdateDTO DTO containing the user's new password data
     * @param bindingResult holds validation and binding errors for the submitted form
     * @param redirectAttributes used to send feedback messages after redirect
     * @return the redirect view name or logical view based on validation result
     */
	@PostMapping("user-{accountId}/update/password/process")
	public String processUpdateAccountPasswordFormPost(@PathVariable("accountId") long accountId, @Validated(OnUpdateAccount.class) @ModelAttribute("updateDTO") AccountPasswordUpdateDTO passwordUpdateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String failRedirect = "password";
		AccountEmailUpdateDTO emailUpdateDTO = null;
		AccountRoleUpdateDTO roleUpdateDTO = null;
		
		return verifyingServices.verifyUpdateFormPost(emailUpdateDTO, passwordUpdateDTO, roleUpdateDTO, accountId, failRedirect, bindingResult, redirectAttributes);
	}
	
	/**
     * Handles HTTP GET requests to /accounts/user-{accountId}/update/role.
     * Renders the form view that allows users to modify the role assigned
     * to a specific account (e.g., switching from USER to ADMIN).
     *
     * This method typically retrieves current account role information,
     * prepares role options for the view (e.g., dropdown of available roles),
     * and populates the form with existing data to support editing.
     *
     * URL Mapping:
     * GET /accounts/user-{accountId}/update/role
     *
     * @param accountId the unique identifier of the account whose role is to be updated
     * @param model the Spring Model object used to pass data to the role update form view
     * @return the name of the view template that displays the role update form
     */
	@GetMapping("user-{accountId}/update/role") 
	public String renderUpdateAccountRoleForm(@PathVariable("accountId") long accountId, Model model) {
		String formAction = "role/process";
		
		return assigningServices.assignUpdateFormAttributes(AccountCredentials.ROLE, FormNames.EMAIL_UPDATE, formAction, model);
	}
	
	/**
     * Handles HTTP POST requests to /accounts/user-{accountId}/update/role/process.
     * Processes the submitted role update form for the specified account ID.
     * Validates the updated role using the OnUpdateAccount validation group,
     * checks for binding errors, and either redirects back to the form with
     * error feedback or proceeds to apply the new role to the account.
     *
     * Responsibilities:
     * - Ensure valid role selection (e.g., USER, ADMIN, MODERATOR)
     * - Handle form validation and binding errors gracefully
     * - Persist role change via service layer logic
     * - Provide feedback via flash attributes on success or failure
     *
     * URL Mapping:
     * POST /accounts/user-{accountId}/update/role/process
     *
     * @param accountId the unique identifier of the account to update
     * @param roleUpdateDTO DTO carrying the selected role submitted from the form
     * @param bindingResult collects validation and binding error information
     * @param redirectAttributes used to communicate feedback after redirect
     * @return the redirect route or view name based on validation and update outcome
     */
	@PostMapping("user-{accountId}/update/role/process")
	public String processUpdateAccountRoleFormPost(@PathVariable("accountId") long accountId, @Validated(OnUpdateAccount.class) @ModelAttribute("updateDTO") AccountRoleUpdateDTO roleUpdateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String failRedirect = "role";
		AccountEmailUpdateDTO emailUpdateDTO = null;
		AccountPasswordUpdateDTO passwordUpdateDTO = null;
		
		return verifyingServices.verifyUpdateFormPost(emailUpdateDTO, passwordUpdateDTO, roleUpdateDTO, accountId, failRedirect, bindingResult, redirectAttributes);
	}
	
}