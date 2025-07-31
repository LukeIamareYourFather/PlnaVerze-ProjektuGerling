package com.danger.insurance.insurances.contracts.controllers;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsAssigningServices;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsProcesingServices;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsVeryfyingServices;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesFoundSendDTO;
import com.danger.insurance.parties.models.service.PartiesAssigningServices;
import com.danger.insurance.parties.models.service.PartiesVerifyingServices;
import com.danger.insurance.validation.groups.OnCreateContract;

/**
 * Controller responsible for assigning insured parties to specific insurance contracts.
 * Decorated with @SessionAttributes("contractDTO") to maintain contract context across 
 * multiple request mappings during the assignment workflow. This enables seamless navigation
 * through form steps, validation, and confirmation before finalizing the assignment.
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
@SessionAttributes("contractDTO")
public class InsurancesAssigningController {

	@Autowired
	private PartiesAssigningServices partiesAssigningServices;
	
	@Autowired
	private PartiesVerifyingServices partiesVerifyingServices;
	
	@Autowired
	private ContractsAssigningServices assigningServices;
	
	@Autowired
	private ContractsProcesingServices procesingServices;
	
	@Autowired
	private ContractsVeryfyingServices veryfyingServices;
	
	/**
	 * Initializes the ContractsDTO model attribute for session-scoped use.
	 * This method ensures that a fresh instance of ContractsDTO is available
	 * throughout the insurance assignment workflow handled by InsurancesAssigningController.
	 * It allows seamless tracking of contract data between request phases.
	 *
	 * Benefits:
	 * - Supports session persistence across multiple steps
	 * - Enables pre-population or dynamic modification during the flow
	 * - Prevents null pointer exceptions from missing model attributes
	 *
	 * @return a new, empty ContractsDTO instance
	 */
	@ModelAttribute("contractDTO")
	public ContractsDTO contractDTO() {
	    return new ContractsDTO(); 											// Only called once, when session starts
	}
	
	/**
	 * Renders the initial form for selecting an insured party to assign to a contract.
	 * Accessed via GET request to "/insurances/create", this method kicks off the assignment
	 * workflow by presenting a UI for party selection—potentially filtered by contract tier,
	 * region, or agent role, depending on business logic.
	 *
	 * Use Case Highlights:
	 * - Serve as the first step in assigning insured individuals to contracts
	 * - Enable filtering or direct search for eligible parties
	 * - Prepare session-bound contractDTO for context-aware flow continuation
	 *
	 * @param model the Spring MVC model used to supply data for rendering the view
	 * @return name of the form view that begins the assignment process
	 */
	@GetMapping("create")
	public String renderInsuranceAssignFormSelect(Model model) {		
		return assigningServices.addInsuranceAssignFormSelectAttributes(contractDTO(), model);
	}
	
	/**
	 * Validates contract assignment details submitted via the initial form.
	 * This POST method at "/insurances/create/validate" confirms the integrity and eligibility
	 * of the selected insured party and contract details captured in the ContractsDTO.
	 * On validation failure, it redirects back with error messages and preserved input.
	 * On success, it prepares for confirmation by redirecting to an overview page.
	 *
	 * Features You Could Add:
	 * - Business rule checks (e.g. party not already assigned, capacity not exceeded)
	 * - Role-based validation paths (agent vs admin)
	 * - Contract tier eligibility flags with redirect messaging
	 *
	 * @param contractsDTO session-bound DTO holding selected contract data
	 * @param bindingResult container for validation errors
	 * @param redirectAttributes used to pass feedback and model state across redirects
	 * @return redirect path to confirmation view or back to form on error
	 */
	@PostMapping("create/validate")
	public String validateInsuranceAssignFormPost(@Validated(OnCreateContract.class) @ModelAttribute("contractDTO") ContractsDTO contractsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return veryfyingServices.verifyInsuranceAssignFormPost(contractsDTO, bindingResult, redirectAttributes);
	}
	
	/**
	 * Renders the insurance selection page using the session-scoped contractDTO.
	 * Accessed via GET request at "/insurances/create/select", this method provides an overview
	 * of contract-related options or insured party context based on the state held in the ContractsDTO.
	 * It may be used as a midpoint in an assigning workflow or as a guided preview before confirmation.
	 *
	 * Possible Enhancements:
	 * - Display contract eligibility, region, or policy type from DTO
	 * - Visual indicators of previous steps (e.g. party selected, coverage tier)
	 * - Conditional rendering based on DTO completeness or business logic
	 *
	 * @param contractsDTO session-bound data holding current contract selection state
	 * @param model MVC model to supply view with contextual attributes
	 * @return name of the selection view to guide user forward in the assignment flow
	 */
	@GetMapping("create/select")
	public String renderInsuranceSelect(@ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		return assigningServices.addInsuranceSelectAttributes(contractsDTO, model);
	}
	
	/**
	 * Renders the form for locating a user (insured party) to assign to a selected insurance contract.
	 * Accessed via GET request at "/insurances/create/selected-{insurancesId}/user-find", this method
	 * initializes the search interface for agents or admins to pinpoint eligible parties that match
	 * the context of the selected insurance contract.
	 *
	 * Workflow Highlights:
	 * - Prepares form input (e.g., name, birthdate, identifier) for party lookup
	 * - Maintains the selected contract context via session-bound ContractsDTO
	 * - Can preload filter hints such as regional eligibility or assignment capacity
	 *
	 * @param insurancesId ID of the insurance contract to which a user is being assigned
	 * @param contractsDTO session-scoped DTO maintaining assignment flow state
	 * @param model Spring MVC model used to pass form and context data to view
	 * @return name of the insured party search form view
	 */
	@GetMapping("create/selected-{insurancesId}")
	public String handleSelectedInsurance(@PathVariable("insurancesId") long insurancesId , @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		String redirectPage = "redirect:/insurances/create/selected-" + insurancesId + "/user-find";

		return redirectPage;
	}

	/**
	 * Renders the user search form to locate a party for assignment to a specific insurance contract.
	 * Accessed via GET at "/insurances/create/selected-{insurancesId}/user-find", this method initializes
	 * the insured party lookup form and ensures that the selected insurance ID is reflected in the session-scoped DTO.
	 * It allows agents or admins to search for eligible users based on predefined attributes.
	 *
	 * Useful Features:
	 * - Preloaded filters like region, age group, or contract eligibility
	 * - Smart hints based on remaining capacity or contract restrictions
	 * - Supports partial match or fuzzy search logic for user-friendly input
	 *
	 * @param insurancesId ID of the insurance contract selected for user assignment
	 * @param contractsDTO session-scoped DTO tracking contract context
	 * @param model MVC model used to populate view with form and hint data
	 * @return name of the party search form view for contract assignment
	 */
	@GetMapping("create/selected-{insurancesId}/user-find")
	public String renderFindPartyToAssignContractForm(@PathVariable("insurancesId") long insurancesId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		String formAction = "user-find/validate";
		
		return partiesAssigningServices.addFindPartyAttributes(formAction, FormNames.PARTY_FIND, model);
	}
	
	/**
	 * Validates the user search form for assigning to a selected insurance contract.
	 * This POST endpoint at "/insurances/create/selected-{insurancesId}/user-find/validate" ensures that
	 * the provided party details are sufficient for lookup and assignment. If validation succeeds,
	 * it redirects to a selection view where eligible candidates are displayed; otherwise,
	 * errors are flashed and the form is reloaded with user input preserved.
	 *
	 * Practical Enhancements:
	 * - Confirm that minimum search criteria (e.g., name or birthdate) are provided
	 * - Highlight incomplete or conflicting data via BindingResult
	 * - Preserve form state and provide contextual feedback on validation failure
	 * - Filter out parties already assigned or ineligible for this insurance ID
	 *
	 * @param insurancesId ID of the selected insurance contract
	 * @param contractsDTO session-scoped DTO holding contract context
	 * @param partiesDetailsDTO user-input party search details
	 * @param bindingResult Spring container holding validation results
	 * @param redirectAttributes for carrying model state and error messaging across redirects
	 * @return redirect path to user selection view or back to form on failure
	 */
	@PostMapping("create/selected-{insurancesId}/user-find/validate")
	public String validateFindPartyToAssignContractFormPost(@PathVariable("insurancesId") long insurancesId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, @ModelAttribute("formDTO") PartiesDetailsDTO partiesDetailsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String prefixRedirect = "insurances/create/selected-" + insurancesId;
		String failRedirect =  prefixRedirect + "/user-find";
		String successRedirect = prefixRedirect + "/user-select";
		
		return partiesVerifyingServices.verifyFindPartyFormPost(partiesDetailsDTO, PartyStatus.REGISTERED, failRedirect, successRedirect, redirectAttributes);
	}
	
	/**
	 * Renders the list of found parties eligible for assignment to a selected insurance contract.
	 * Accessed via GET at "/insurances/create/selected-{insurancesId}/user-select", this method 
	 * displays parties previously matched through search criteria. It supports selection of one 
	 * or more users to be added to the contract identified by insurancesId.
	 *
	 * Features to Consider:
	 * - Display candidate party attributes (e.g., name, age, region)
	 * - Include validation markers or assignment flags (e.g., already assigned, ineligible)
	 * - Enable selection controls like checkboxes or action links for confirmation
	 * - Incorporate pagination or filters based on party volume
	 *
	 * @param insurancesId ID of the insurance contract to assign users to
	 * @param contractsDTO session-scoped DTO maintaining contract context
	 * @param sendDto DTO containing matched parties from previous step
	 * @param model Spring MVC model used to populate the view with result data
	 * @return name of the view displaying party selection interface
	 */
	@GetMapping("create/selected-{insurancesId}/user-select") 
	public String renderFoundPartiesToAssignContractList(@PathVariable("insurancesId") long insurancesId , @ModelAttribute("contractDTO") ContractsDTO contractsDTO, @ModelAttribute("findingsDto") PartiesFoundSendDTO sendDto, Model model) {
		return assigningServices.addFoundPartiesToAssignContractListAttributes(sendDto, model);
	}
	
	/**
	 * Renders the confirmation form for assigning a party to a selected insurance contract.
	 * Accessed via GET at "/insurances/create/selected-{insurancesId}/party-{partyId}", this method
	 * prepares a final review page where agents or admins verify both the insurance contract
	 * and the party before completing the assignment. It's a chance to affirm alignment
	 * in coverage, eligibility, and business rules before committing.
	 *
	 * Ideal Confirmation Page Content:
	 * - Party information (e.g., name, age, eligibility status)
	 * - Contract details (e.g., tier, capacity, expiration)
	 * - Visual indicators for conflicts, duplicate assignments, or restrictions
	 * - Confirmation controls and disclaimers (e.g., "I certify this assignment is accurate")
	 *
	 * @param insurancesId ID of the selected insurance contract
	 * @param partyId ID of the party intended for contract assignment
	 * @param contractsDTO session-scoped DTO holding assignment context
	 * @param model MVC model to pass review data to the confirmation view
	 * @return name of the confirmation review page template
	 */
	@GetMapping("create/selected-{insurancesId}/party-{partyId}")
	public String renderAssignContractToPartyConfirmationForm(@PathVariable("insurancesId") long insurancesId , @PathVariable("partyId") long partyId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		return assigningServices.addAssignContractToPartyConfirmationAttributes(contractsDTO, partyId, insurancesId, model);
	}
	
	/**
	 * Finalizes assignment of the specified party to the selected insurance contract.
	 * This POST endpoint at "/insurances/create/selected-{insurancesId}/party-{partyId}/confirmed"
	 * completes the workflow by persisting the relationship between party and contract. 
	 * On success, feedback is flashed and the user is redirected to a success or contract view. 
	 * On failure, fallback messaging is rendered for retry or correction.
	 *
	 * Core Responsibilities:
	 * - Persist the insured-party-to-contract link via service layer
	 * - Validate contract and party eligibility before binding
	 * - Return feedback for either confirmation or error resolution
	 *
	 * @param insurancesId ID of the selected insurance contract
	 * @param partyId ID of the party being assigned
	 * @param contractsDTO session-scoped DTO containing assignment context
	 * @param model Spring MVC model for rendering fallback messaging if needed
	 * @param redirectAttributes Flash scope for success messaging or state transfer
	 * @return redirect to assignment success page or fallback error view
	 */
	@PostMapping("create/selected-{insurancesId}/party-{partyId}/confirmed")
	public String handleConfirmedContractAssignmentToPartyPost(@PathVariable("insurancesId") long insurancesId , @PathVariable("partyId") long partyId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model, RedirectAttributes redirectAttributes) {
		return procesingServices.processConfirmedContractAssignmentToPartyPost(contractsDTO, insurancesId, partyId, redirectAttributes);
	}
	
}