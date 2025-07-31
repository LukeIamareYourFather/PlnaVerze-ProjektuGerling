package com.danger.insurance.insurances.contracts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsAssigningServices;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsProcesingServices;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsVeryfyingServices;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

/**
 * Controller responsible for managing the workflow of adding insured individuals to insurance contracts.
 * Mapped under "/insurances", this class orchestrates form handling, validation, contract lookup,
 * and insured data submission into contract records.
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
public class AddInsuredToContractController {

	@Autowired
	private ContractsAssigningServices assigningServices;
	
	@Autowired
	private ContractsProcesingServices procesingServices;
	
	@Autowired
	private ContractsVeryfyingServices veryfyingServices;
	
	/**
	 * Renders the form for searching and selecting an insured individual to be added to a contract.
	 * Accessed via GET request to "/insurances/add", this method initializes the model with
	 * a blank search form DTO and optionally preloads dropdowns or hints to guide user input.
	 *
	 * Ideal Use Cases:
	 * - Search by name, ID, birthdate, or eligibility criteria
	 * - Dynamic filtering based on contract type or regional rules
	 * - Provide user feedback for duplicate entries or matching insured records
	 *
	 * @param model the Spring MVC model used to populate the view with form data
	 * @return the name of the form view template for adding an insured person
	 */
	@GetMapping("add")
	public String renderInsuredSearchForm(Model model) {
		return assigningServices.addInsuredSearchFromAttributes(model);
	}
	
	/**
	 * Validates the party search form submission and prepares redirect flow.
	 * This POST handler under "/insurances/add/validate" processes user input
	 * contained in PartiesDetailsDTO to confirm search intent or select party details
	 * for contract association. Based on input, it may guide the user to a selection
	 * interface or display feedback for missing or incorrect data.
	 *
	 * Common Use Cases:
	 * - Confirm that search criteria are provided (e.g. name, birthdate)
	 * - Redirect to party selection or contract match preview
	 * - Flash error feedback and preserve input on failure
	 *
	 * @param partyDetails DTO containing user-provided party search details
	 * @param redirectAttributes used to pass data/messages between redirects
	 * @return redirect URL based on validation and match outcome
	 */
	@PostMapping("add/validate")
	public String validatePartySearchPost(@ModelAttribute("formDTO") PartiesDetailsDTO partyDetails, RedirectAttributes redirectAttributes) {
		return veryfyingServices.verifyPartySearchPost(partyDetails, redirectAttributes);
	}
	
	/**
	 * Renders the list of insured individuals available for selection and contract association.
	 * Triggered via GET request to "/insurances/add/select", this method prepares the UI with
	 * pre-filtered candidates gathered from earlier search criteria. It helps users choose
	 * the correct party to add into an insurance contract.
	 *
	 * Common Features:
	 * - Display match results from search validation step
	 * - Enable clickable rows or radio buttons for selection
	 * - Include tooltips, duplicate detection, or flags for contract eligibility
	 *
	 * @param model Spring MVC model to supply view attributes
	 * @return view name for the insured selection interface
	 */
	@GetMapping("add/select")
	public String renderSelectInsuredList(Model model) {
		String referenceLink = "select/party-";
		String returnedPage = "pages/parties/found-parties";
		
		return assigningServices.addSelectListAttributes(referenceLink, returnedPage, model);
	}
	
	/**
	 * Renders the contract selection form for the specified insured party.
	 * Triggered via GET request to "/insurances/add/select/party-{partyId}", this method
	 * facilitates the next step in adding an insured individual by presenting a list of 
	 * available insurance contracts they may be linked to.
	 *
	 * Usage Scenarios:
	 * - Filter contracts based on the party's region, plan eligibility, or association history
	 * - Display warnings for contracts that are full, expired, or restricted
	 * - Highlight recommended contracts based on age, relationship, or prior claims
	 *
	 * @param partyId the ID of the insured party selected for addition
	 * @param model Spring model for passing data to the view
	 * @return the name of the view displaying contract selection UI
	 */
	@GetMapping("add/select/party-{partyId}")
	public String renderContractSearchForm(@PathVariable("partyId") long partyId, Model model) {
		return assigningServices.addContractSearchFormAttributes(partyId, model);
	}
	
	/**
	 * Validates the contract selection for the insured party and prepares for confirmation.
	 * Invoked via POST request to "/insurances/add/select/party-{partyId}/validate", this method
	 * ensures a valid contract has been selected for the given party. If validation passes, 
	 * the workflow proceeds to confirmation; otherwise, the user is redirected back with feedback.
	 *
	 * Features worth adding:
	 * - Ensure selected contract ID is present and belongs to eligible list
	 * - Display alerts for expired, full, or ineligible contracts
	 * - Preserve context on failure and reset session flow on success
	 *
	 * @param partyId the ID of the insured party
	 * @param contractsDTO DTO containing selected contract information
	 * @param redirectAttributes used to carry data and status across redirects
	 * @return redirect URL based on validation outcome
	 */
	@PostMapping("add/select/party-{partyId}/validate")
	public String validateContractSearchFormPost(@PathVariable("partyId") long partyId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, RedirectAttributes redirectAttributes) {
		return veryfyingServices.verifyContractSearchFormPost(partyId, contractsDTO, redirectAttributes);
	}
	
	/**
	 * Displays a list of selectable contracts for the specified insured party.
	 * Accessed via GET at "/insurances/add/select/party-{partyId}/pick", this method retrieves
	 * all eligible or active contracts available to that party, filtered by business rules or plan eligibility.
	 * It enables users to review options before making a final selection for contract binding.
	 *
	 * Possible UI Enhancements:
	 * - Show contract details like coverage tier, expiration date, and associated beneficiaries
	 * - Add tooltips or icons for contract status (e.g. active, pending, restricted)
	 * - Include sorting by price, coverage scope, or recommendation score
	 *
	 * @param partyId ID of the insured party initiating contract selection
	 * @param model MVC model to pass contract list and metadata to view
	 * @return view name presenting the contract selection interface
	 */
	@GetMapping("add/select/party-{partyId}/pick")
	public String renderSelectContractList(@PathVariable("partyId") long partyId, Model model) {
		String referenceLink = "contract-";
		String returnedPage = "pages/insurances/contracts/list";
		
		return assigningServices.addSelectListAttributes(referenceLink, returnedPage, model);
	}
	
	/**
	 * Renders the confirmation overview before binding the insured party to the selected contract.
	 * This GET endpoint at "/insurances/add/select/party-{partyId}/contract-{contractId}" prepares 
	 * a final review screen that summarizes both the insured party and the chosen contract details.
	 * It gives users a chance to verify all inputs before committing to the enrollment.
	 *
	 * Typical Review Content:
	 * - Insured party personal and eligibility details
	 * - Contract metadata (coverage scope, expiration, tier)
	 * - Flags or warnings (e.g. duplicate entry, conflicting policies)
	 * - Optional confirmation controls (agree/disagree buttons, disclaimers)
	 *
	 * @param partyId ID of the selected insured individual
	 * @param contractId ID of the contract intended for assignment
	 * @param model MVC model used to pass attributes to the view
	 * @return name of the confirmation overview template
	 */
	@GetMapping("add/select/party-{partyId}/contract-{contractId}")
	public String renderConfirmOverview(@PathVariable("partyId") long partyId, @PathVariable("contractId") long contractId, Model model) {
		return assigningServices.addConfirmOverviewAttributes(partyId, contractId, model);
	}
	
	/**
	 * Finalizes the enrollment of the insured party into the selected contract.
	 * Triggered via POST to "/insurances/add/select/party-{partyId}/contract-{contractId}/confirmed",
	 * this method performs the actual binding operation between the insured and the insurance contract.
	 * It includes validation, persistence, and redirect messaging to confirm success.
	 *
	 * Key Workflow Steps:
	 * - Validate party and contract relationship eligibility
	 * - Persist the association via service layer
	 * - Provide UI feedback (success or failure)
	 * - Optionally log the event for audit trail purposes
	 *
	 * @param partyId ID of the insured individual
	 * @param contractId ID of the contract they are being enrolled into
	 * @param model used to inject any final attributes or fallback messaging
	 * @param redirectAttributes used to flash success feedback across redirect
	 * @return redirect path to final success page or contract overview
	 */
	@PostMapping("add/select/party-{partyId}/contract-{contractId}/confirmed")
	public String handleAddInsuredToContractConfirmation(@PathVariable("partyId") long partyId, @PathVariable("contractId") long contractId, Model model, RedirectAttributes redirectAttributes) {
		return procesingServices.processAddInsuredToContractConfirmation(partyId, contractId, redirectAttributes);
	}
	
}