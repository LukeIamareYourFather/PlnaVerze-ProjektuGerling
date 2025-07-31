package com.danger.insurance.parties.controllers;

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

import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.service.PartiesAssigningServices;
import com.danger.insurance.parties.models.service.PartiesProcesingServices;
import com.danger.insurance.validation.groups.OnCreatePolicyOwner;

/**
 * Controller responsible for handling the creation of party entities.
 * <p>
 * Mapped under the "/parties" base path, this controller provides endpoints for rendering
 * the party creation form, validating user input, and submitting new party records to the system.
 * It supports workflows for both internal and external party registration, such as clients,
 * vendors, or organizational units.
 * </p>
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("parties")
public class CreatePartiesController {

	// Object initialization
	
	@Autowired
	private PartiesAssigningServices assigningServices;
	
	@Autowired
	private PartiesProcesingServices procesingServices;
	
	// Start of code
	

	/**
	 * Renders the form view for creating a new policy owner (party).
	 * <p>
	 * Triggered via GET at "/parties/create", this method prepares the model with the necessary
	 * attributes to display an empty party creation form. It initializes a fresh {@code PartyCreateDTO}
	 * (or equivalent) object for form binding and may include metadata such as party types, regions,
	 * or default values.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Initialize the form backing object for party creation</li>
	 *   <li>Populate the model with auxiliary data (e.g., dropdown options, defaults)</li>
	 *   <li>Forward to the view responsible for rendering the party creation interface</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code policyOwnerDTO} – empty DTO for user input binding</li>
	 *   <li>{@code partyTypeOptions} – list of available party types</li>
	 *   <li>{@code regionOptions} – list of selectable regions or jurisdictions</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by administrators or onboarding staff to register new policy owners</li>
	 *   <li>Serves as the first step in the party creation workflow</li>
	 * </ul>
	 *
	 * <h2>Error Handling:</h2>
	 * <ul>
	 *   <li>If required metadata is missing → show fallback message or redirect</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the party creation form
	 */
	@GetMapping("create")
	public String renderCreatePolicyOwner(Model model) {
		String formAction = "/parties/create";
		
		return assigningServices.addCreateFormAttributes(formAction, model);
	}
	
	/**
	 * Handles submission of the policy owner creation form and processes the input.
	 * <p>
	 * Triggered via POST at "/parties/create", this method validates the user-submitted
	 * {@code PartiesCreateDTO} using the {@code OnCreatePolicyOwner} validation group.
	 * If validation fails, it returns to the form view with error messages. If successful,
	 * it persists the new party record and redirects to a confirmation or listing page.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Validate the submitted party data</li>
	 *   <li>Handle binding errors and preserve user input across redirects</li>
	 *   <li>Persist the new party via the service layer</li>
	 *   <li>Provide feedback using flash attributes</li>
	 * </ul>
	 *
	 * <h2>Validation Group:</h2>
	 * <ul>
	 *   <li>{@code OnCreatePolicyOwner} – ensures required fields are present and valid for creation</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On validation failure → return to "/parties/create" with error messages</li>
	 *   <li>On success → redirect to "/parties/list" or confirmation view</li>
	 * </ul>
	 *
	 * <h2>Security Considerations:</h2>
	 * <ul>
	 *   <li>Ensure user is authorized to register new parties</li>
	 *   <li>Log creation actions for audit and traceability</li>
	 * </ul>
	 *
	 * @param partiesCreateDTO DTO containing the new party's data
	 * @param bindingResult result of validation checks
	 * @param model Spring MVC model used to pass attributes to the view
	 * @param redirectAttributes used to pass flash messages across redirects
	 * @return redirect path to either the creation form (on error) or party list view (on success)
	 */
	@PostMapping("create")
	public String handleCreatePolicyOwnerFormPost(@Validated(OnCreatePolicyOwner.class) @ModelAttribute("formDTO") PartiesCreateDTO partiesCreateDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		PartiesDetailsDTO wrongDTO = null;
		String successRedirect = "parties/profile-";
		String failRedirect = "parties/create";
		
		return procesingServices.processPartyValuesFormSubmit(bindingResult, redirectAttributes, partiesCreateDTO, wrongDTO, successRedirect, failRedirect);
	}
	
}