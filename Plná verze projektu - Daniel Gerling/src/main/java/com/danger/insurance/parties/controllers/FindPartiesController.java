//  (•_•)
// <)   )╯ Clean
// /    \   Code
package com.danger.insurance.parties.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.service.PartiesAssigningServices;
import com.danger.insurance.parties.models.service.PartiesVerifyingServices;

/**
 * Controller responsible for handling search and discovery of party entities.
 * <p>
 * Mapped under the "/parties" base path, this controller provides endpoints for initiating
 * search workflows, processing filter criteria, and displaying matched party profiles.
 * It supports both simple keyword-based searches and advanced multi-criteria filtering.
 * </p>
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("parties")
public class FindPartiesController {

	// Object initialization
	
	@Autowired
	private PartiesAssigningServices assigningServices;
	
	@Autowired
	private PartiesVerifyingServices verifyingServices;
	
	// Start of code
	
	/**
	 * Renders the form view for searching policy owner profiles.
	 * <p>
	 * Triggered via GET at "/parties/find/policy-owner", this method prepares the model
	 * with the necessary attributes to display a search interface for locating policy owners.
	 * It supports both simple keyword-based queries and advanced filtering options.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Initialize the form backing object for search input</li>
	 *   <li>Populate the model with auxiliary data (e.g., filter options, regions)</li>
	 *   <li>Forward to the view responsible for rendering the policy owner search form</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code searchDTO} – empty DTO for binding user input</li>
	 *   <li>{@code regionOptions} – list of selectable regions or jurisdictions</li>
	 *   <li>{@code partyTypeOptions} – list of party types relevant to policy owners</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by administrators or users to locate specific policy owner records</li>
	 *   <li>Serves as the first step in a multi-step search and discovery workflow</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Enable autocomplete or suggestion features</li>
	 *   <li>Support saved search templates or recent queries</li>
	 *   <li>Integrate fuzzy matching or phonetic search</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the policy owner search form
	 */
	@GetMapping("find/policy-owner")
	public String renderFindPolicyOwner(Model model) {
		String formAction = "policy-owners";
		
		return assigningServices.addFindPartyAttributes(formAction, FormNames.POLICY_OWNER_FIND, model);
		
	}
	
	/**
	 * Renders the form view for searching insured party profiles.
	 * <p>
	 * Triggered via GET at "/parties/find/insured", this method prepares the model
	 * with the necessary attributes to display a search interface for locating insured individuals.
	 * It supports both basic and advanced search criteria, such as name, policy number, or region.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Initialize the form backing object for insured search input</li>
	 *   <li>Populate the model with auxiliary data (e.g., region filters, policy types)</li>
	 *   <li>Forward to the view responsible for rendering the insured search form</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code insuredSearchDTO} – empty DTO for binding user input</li>
	 *   <li>{@code regionOptions} – list of selectable regions or jurisdictions</li>
	 *   <li>{@code policyTypeOptions} – list of policy types relevant to insured parties</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by administrators or agents to locate insured individuals</li>
	 *   <li>Serves as the first step in verification, claims, or relationship workflows</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Enable search by policy number or insured ID</li>
	 *   <li>Support autocomplete or recent search history</li>
	 *   <li>Integrate fuzzy matching or phonetic search for names</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the insured search form
	 */
	@GetMapping("find/insured")
	public String renderFindInsured(Model model) {
		String formAction = "insured";
		
		return assigningServices.addFindPartyAttributes(formAction, FormNames.INSURED_FIND, model);
	}
	
	/**
	 * Renders the form view for searching uninsured party profiles.
	 * <p>
	 * Triggered via GET at "/parties/find/uninsured", this method prepares the model
	 * with the necessary attributes to display a search interface for locating individuals
	 * who are not currently associated with any active insurance policy. It supports
	 * administrative workflows such as outreach, onboarding, or eligibility review.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Initialize the form backing object for uninsured search input</li>
	 *   <li>Populate the model with auxiliary data (e.g., region filters, status options)</li>
	 *   <li>Forward to the view responsible for rendering the uninsured search form</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code uninsuredSearchDTO} – empty DTO for binding user input</li>
	 *   <li>{@code regionOptions} – list of selectable regions or jurisdictions</li>
	 *   <li>{@code eligibilityFlags} – optional indicators for outreach or onboarding readiness</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by administrators or agents to identify uninsured individuals</li>
	 *   <li>Supports workflows for outreach, enrollment, or policy matching</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Enable search by demographic or contact criteria</li>
	 *   <li>Support bulk outreach or enrollment actions</li>
	 *   <li>Integrate eligibility scoring or risk assessment</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the uninsured search form
	 */
	@GetMapping("find/uninsured")
	public String renderFindUninsured(Model model) {
		String formAction = "uninsured";
		
		return assigningServices.addFindPartyAttributes(formAction, FormNames.PARTY_FIND, model);

	}
	
	/**
	 * Processes the search form submission for locating policy owner profiles.
	 * <p>
	 * Triggered via POST at "/parties/find/policy-owners", this method receives a populated
	 * {@code PartiesDetailsDTO} containing user-defined search criteria. It performs validation
	 * and initiates the search logic via the service layer. Matching results are passed using
	 * {@code RedirectAttributes} and forwarded to the appropriate results view.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Receive and interpret search criteria from the submitted DTO</li>
	 *   <li>Invoke service logic to locate matching policy owner records</li>
	 *   <li>Pass results or feedback via flash attributes for rendering</li>
	 *   <li>Redirect to the results view or fallback page if no matches are found</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On success → redirect to "/parties/found/profiles" with matched results</li>
	 *   <li>On no matches → redirect to "/parties/not-found" with feedback</li>
	 * </ul>
	 *
	 * <h2>Security Considerations:</h2>
	 * <ul>
	 *   <li>Ensure user is authorized to perform search operations</li>
	 *   <li>Log search activity for audit or diagnostic purposes</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Support fuzzy matching or partial keyword search</li>
	 *   <li>Enable pagination or sorting of results</li>
	 *   <li>Integrate search history or saved templates</li>
	 * </ul>
	 *
	 * @param dto DTO containing search criteria for policy owners
	 * @param redirectAttributes used to pass search results or feedback across redirects
	 * @return redirect path to the results view or fallback page
	 */
	@PostMapping("find/policy-owners")
	public String handleSearchPolicyOwnerFormSubmit(PartiesDetailsDTO dto, RedirectAttributes redirectAttributes) {
		String failRedirect = "parties/find/policy-owner";
		String successRedirect = "parties/found/profiles";
		
		return verifyingServices.verifyFindPartyFormPost(dto, PartyStatus.POLICY_OWNER, failRedirect, successRedirect, redirectAttributes);
	}
	
	/**
	 * Processes the search form submission for locating policy owner profiles.
	 * <p>
	 * Triggered via POST at "/parties/find/policy-owners", this method receives a populated
	 * {@code PartiesDetailsDTO} containing user-defined search criteria. It performs validation
	 * and initiates the search logic via the service layer. Matching results are passed using
	 * {@code RedirectAttributes} and forwarded to the appropriate results view.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Receive and interpret search criteria from the submitted DTO</li>
	 *   <li>Invoke service logic to locate matching policy owner records</li>
	 *   <li>Pass results or feedback via flash attributes for rendering</li>
	 *   <li>Redirect to the results view or fallback page if no matches are found</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On success → redirect to "/parties/found/profiles" with matched results</li>
	 *   <li>On no matches → redirect to "/parties/not-found" with feedback</li>
	 * </ul>
	 *
	 * <h2>Security Considerations:</h2>
	 * <ul>
	 *   <li>Ensure user is authorized to perform search operations</li>
	 *   <li>Log search activity for audit or diagnostic purposes</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Support fuzzy matching or partial keyword search</li>
	 *   <li>Enable pagination or sorting of results</li>
	 *   <li>Integrate search history or saved templates</li>
	 * </ul>
	 *
	 * @param dto DTO containing search criteria for policy owners
	 * @param redirectAttributes used to pass search results or feedback across redirects
	 * @return redirect path to the results view or fallback page
	 */
	@PostMapping("find/insured")
	public String handleSearchInsuredFormSubmit(PartiesDetailsDTO dto, RedirectAttributes redirectAttributes) {
		String failRedirect = "parties/find/insured";
		String successRedirect = "parties/found/profiles";
		
		return verifyingServices.verifyFindPartyFormPost(dto, PartyStatus.INSURED, failRedirect, successRedirect, redirectAttributes);
	}
	
	/**
	 * Processes the search form submission for locating uninsured party profiles.
	 * <p>
	 * Triggered via POST at "/parties/find/uninsured", this method receives a populated
	 * {@code PartiesDetailsDTO} containing search criteria for individuals not currently
	 * associated with any active insurance policy. It delegates the search logic to the
	 * service layer and uses {@code RedirectAttributes} to pass results or feedback to
	 * the next view.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Interpret search criteria from the submitted DTO</li>
	 *   <li>Invoke service logic to locate matching uninsured records</li>
	 *   <li>Pass results or status messages via flash attributes</li>
	 *   <li>Redirect to the results view or fallback page if no matches are found</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On success → redirect to "/parties/found/profiles" with matched results</li>
	 *   <li>On no matches → redirect to "/parties/not-found" with feedback</li>
	 * </ul>
	 *
	 * <h2>Security Considerations:</h2>
	 * <ul>
	 *   <li>Ensure user is authorized to perform uninsured party searches</li>
	 *   <li>Log search activity for audit and diagnostics</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Enable search by demographic or contact criteria</li>
	 *   <li>Support outreach workflows or eligibility scoring</li>
	 *   <li>Integrate search history or saved templates</li>
	 * </ul>
	 *
	 * @param dto DTO containing search criteria for uninsured individuals
	 * @param redirectAttributes used to pass search results or feedback across redirects
	 * @return redirect path to the results view or fallback page
	 */
	@PostMapping("find/uninsured")
	public String handleSearchUninsuredFormSubmit(PartiesDetailsDTO dto, RedirectAttributes redirectAttributes) {
		String failRedirect = "parties/find/uninsured";
		String successRedirect = "parties/found/profiles";
		PartyStatus noStatus = null;
		
		return verifyingServices.verifyFindPartyFormPost(dto, noStatus, failRedirect, successRedirect, redirectAttributes);
	}

}