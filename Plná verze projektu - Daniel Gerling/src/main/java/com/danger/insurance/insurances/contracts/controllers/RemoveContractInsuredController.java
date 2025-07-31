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

import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsAssigningServices;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsProcesingServices;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsVeryfyingServices;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.validation.groups.OnRemoveContract;

/**
 * Controller for removing insured individuals from insurance contracts.
 * Mapped under "/insurances" and equipped with @SessionAttributes("removalReasonsDTO"),
 * this class handles workflows where contracts need to be updated to unbind an insured party.
 * It guides users through reason capture, eligibility validation, confirmation, and final removal.
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
@SessionAttributes("removalReasonsDTO")
public class RemoveContractInsuredController {

	@Autowired
	private ContractsAssigningServices assigningServices;
	
	@Autowired
	private ContractsProcesingServices procesingServices;
	
	@Autowired
	private ContractsVeryfyingServices veryfyingServices;
	
	/**
	 * Initializes the RemovalReasonsDTO model attribute for session-scoped usage.
	 * Bound via @ModelAttribute("removalReasonsDTO"), this method ensures a non-null DTO
	 * exists throughout the removal workflow handled by RemoveContractInsuredController.
	 * It allows seamless transition between form steps while retaining selected reason context.
	 *
	 * Benefits & Use Cases:
	 * - Pre-populates removal reason form fields (e.g., voluntary exit, deceased, duplicate)
	 * - Maintains reason state across multi-step flows (search → validation → confirmation)
	 * - Enables audit trail tagging or compliance logging based on selected values
	 *
	 * @return a new, empty instance of RemoveContractReasonsDTO
	 */
	@ModelAttribute("removalReasonsDTO")
	public RemoveContractReasonsDTO reasonsDto() {
	    return new RemoveContractReasonsDTO(); 											// Only called once, when session starts
	}
	
	/**
	 * Renders the initial removal form for unbinding an insured party from a contract.
	 * Accessed via GET at "/insurances/remove", this method sets the stage for selecting
	 * an insured individual, capturing the removal reason, and guiding users through
	 * confirmation and policy update.
	 *
	 * Common Use Case:
	 * - Agents or admins removing coverage due to policy changes, voluntary exit, or disqualification
	 * - Preparing session-scoped reason DTO for contextual validation and feedback
	 * - Displaying contract-party list with action controls and reason selector
	 *
	 * @param model Spring MVC model used to supply attributes to the removal form view
	 * @return name of the form view initiating the removal workflow
	 */
	@GetMapping("remove")
	public String renderRemoveInsuredFromContract(Model model) {
		String formName = FormNames.CONTRACTS_REMOVE_INSURED.getDisplayName();
		String formAction = "remove/validate";
		String returnedPage = "pages/insurances/contracts/remove-form";
		
		return assigningServices.addAppropriateFormDTOWithAttributes(reasonsDto(), formName, formAction, ButtonLabels.CONFIRM, returnedPage, model);
	}
	
	/**
	 * Validates the removal form submission for detaching an insured party from a contract.
	 * Invoked via POST to "/insurances/remove/validate", this method checks whether the provided
	 * removal reason and selected party data meet eligibility and format expectations. On failure,
	 * the user is redirected back with error feedback. On success, session context is preserved
	 * for confirmation and final removal.
	 *
	 * Validation Strategy:
	 * - Enforce @Validated(OnRemoveContract.class) group constraints
	 * - Use BindingResult to capture field-level errors (e.g. empty reason, invalid selection)
	 * - Preserve form input across redirect to maintain user context
	 *
	 * @param removeContractReasonsDTO DTO containing selected party and removal reason
	 * @param bindingResult container for validation results
	 * @param redirectAttributes Flash attributes for user feedback and model transfer
	 * @return redirect to confirmation view on success or back to removal form on failure
	 */
	@PostMapping("remove/validate")
	public String validateRemoveFormPost(@Validated(OnRemoveContract.class) @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String successRedirect = "insurances/remove/find";
		String failRedirect = "insurances/remove";
		
		return veryfyingServices.verifyRemoveInsurancesFormPost(removeContractReasonsDTO, bindingResult, redirectAttributes, successRedirect, failRedirect);
	}
	
	/**
	 * Renders the form to select the insured party intended for removal from a contract.
	 * Accessed via GET at "/insurances/remove/find", this handler initiates the identification
	 * of the insured individual, using criteria stored in the session-scoped RemoveContractReasonsDTO.
	 * It sets up the UI with input controls to search or browse parties eligible for contract removal.
	 *
	 * Ideal Features:
	 * - Preloaded filter options (e.g., contract ID, region, insured party status)
	 * - Smart hints based on party's current claim activity or contract tier
	 * - Display of parties with assignment metadata and removal eligibility badges
	 *
	 * @param removeContractReasonsDTO session DTO holding removal reason and selection context
	 * @param model Spring MVC model used to populate the insured party selection form
	 * @return name of the view responsible for displaying selection interface
	 */
	@GetMapping("remove/find")
	public String renderSelectInsuredForm(@ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		RemoveContractReasonsDTO wrongDTO = null;
		String formName = FormNames.CONTRACTS_REMOVE_INSURED.getDisplayName() + " - vyhledání pojištěnce";
		String formAction = "find/validate";
		String returnedPage = "pages/parties/find";
		
		return assigningServices.addAppropriateFormDTOWithAttributes(wrongDTO, formName, formAction, ButtonLabels.FIND, returnedPage, model);
	}
	
	/**
	 * Validates the form used to search for an insured party to be removed from a contract.
	 * Invoked via POST to "/insurances/remove/find/validate", this method checks that the input
	 * criteria are sufficient and meaningful for locating eligible parties for removal.
	 * If no matches are found or input is invalid, error feedback is provided. Otherwise,
	 * search results and relevant state are passed forward for selection.
	 *
	 * Validation & Search Flow:
	 * - Validate that essential search fields (e.g., name, birthdate, ID) are filled
	 * - Optionally filter out parties already removed or locked by active claims
	 * - Preserve form input and feedback across redirect on failure
	 * - Pass search results forward to display on selection view

	 * @param removeContractReasonsDTO session DTO tracking removal reason and context
	 * @param partiesDetailsDTO form DTO with user-entered search criteria
	 * @param redirectAttributes Flash container for results, errors, or form data
	 * @return redirect to selection view if successful or back to search form on error
	 */
	@PostMapping("remove/find/validate")
	public String handleSelectInsuredFormPost(@ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, @ModelAttribute("formDTO") PartiesDetailsDTO partiesDetailsDTO, RedirectAttributes redirectAttributes) {
		return procesingServices.processSelectInsuredList(partiesDetailsDTO, redirectAttributes);
	}
	
	/**
	 * Displays a list of insured parties eligible for removal from contracts.
	 * Accessed via GET at "/insurances/remove/select", this method populates the view with
	 * candidates previously matched based on search criteria and verified as removable.
	 * It leverages the session-scoped RemoveContractReasonsDTO to retain context about
	 * removal justification and party attributes.
	 *
	 * Ideal UX Elements:
	 * - Table view of candidates with contract IDs, names, and current status
	 * - Indicators for eligibility conflicts (e.g., locked by active claims)
	 * - Selection mechanism (radio buttons, action links) to choose target party
	 * - Optional tooltips explaining removal consequences
	 *
	 * @param removeContractReasonsDTO Session DTO carrying removal reason context
	 * @param model Spring MVC model supplying result data and metadata to the view
	 * @return View name presenting the list of removable insured parties
	 */
	@GetMapping("remove/select")
	public String renderSelectInsuredList(@ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		String refrenceLink = "selected-";
		String returnedPage = "pages/parties/found-parties";
		
		return assigningServices.addSelectListAttributes(refrenceLink, returnedPage, model);
	}
	
	/**
	 * Displays a list of insurance contracts associated with the specified party for removal consideration.
	 * Accessed via GET at "/insurances/remove/selected-{partyId}", this method uses the party ID and 
	 * session-scoped removal reason DTO to retrieve all active contracts that the insured party 
	 * may be eligible to be removed from. It helps users choose the specific contract for unbinding.
	 *
	 * Typical UX Flow:
	 * - Table of contracts with key metadata (e.g., coverage tier, expiration, status)
	 * - Removal eligibility indicators (locked by claims, expired, etc.)
	 * - Action controls (select button, tooltip with warnings or consequences)
	 * - Session state enriched with chosen party for downstream confirmation
	 *
	 * @param partyId ID of the insured individual selected for removal
	 * @param removalReasonsDTO session-bound DTO capturing removal reason and context
	 * @param model Spring MVC model used to supply attributes to the view
	 * @return name of the view showing the list of eligible contracts for removal
	 */
	@GetMapping("remove/selected-{partyId}")
	public String renderSelectContractToRemoveList(@PathVariable("partyId") long partyId, @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		return assigningServices.addSelectContractToRemoveListAttributes(partyId, model);
	}
	
	/**
	 * Renders the final confirmation view before removing an insured party from a contract.
	 * Accessed via GET at "/insurances/remove/selected-{partyId}/confirm-{contractId}", this method
	 * consolidates removal reason, contract details, and party identity into a review interface
	 * where users affirm the action before proceeding with contract update.
	 *
	 * Key Features to Include:
	 * - Display of party info (e.g., name, region, status)
	 * - Contract metadata (e.g., tier, expiration, coverage type)
	 * - Selected removal reason and optional impact summary
	 * - Confirmation disclaimer or approval checkbox
	 *
	 * @param partyId ID of the insured individual marked for removal
	 * @param contractId ID of the contract they are being removed from
	 * @param removalReasonsDTO session-scoped DTO tracking reason and selection context
	 * @param model Spring MVC model supplying attributes to the confirmation view
	 * @return name of the review page for removal confirmation
	 */
	@GetMapping("remove/selected-{partyId}/confirm-{contractId}")
	public String renderRemoveInsuredFromContractConfirmation(@PathVariable("partyId") long partyId, @PathVariable("contractId") long contractId, @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		return assigningServices.addRemoveInsuredFromContractConfirmationAttributes(partyId, contractId, model);
	}
	
	/**
	 * Finalizes the removal of the specified insured party from a contract.
	 * Accessed via POST at "/insurances/remove/selected-{partyId}/confirmed-{contractId}",
	 * this endpoint completes the unbinding process by updating the contract to detach the party.
	 * It ensures that the removal reason is valid and persists the change via service logic.
	 * Feedback is provided for successful or failed execution.
	 *
	 * Workflow Goals:
	 * - Validate party and contract relationship before removal
	 * - Persist removal reason to audit trail or contract history
	 * - Trigger post-removal notifications or updates (optional)
	 * - Provide success or error feedback for user journey continuity
	 *
	 * @param partyId ID of the insured party to be removed
	 * @param contractId ID of the contract they are being removed from
	 * @param removalReasonsDTO session-bound DTO carrying context and justification
	 * @param model Spring MVC model for fallback rendering
	 * @param redirectAttributes used for feedback and state carry across redirects
	 * @return redirect path to contract overview or fallback error view
	 */
	@PostMapping("remove/selected-{partyId}/confirmed-{contractId}")
	public String handleRemoveContractInsuredConfirmation(@PathVariable("partyId") long partyId, @PathVariable("contractId") long contractId, @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model, RedirectAttributes redirectAttributes) {
		return procesingServices.processRemoveContractInsuredConfirmation(removeContractReasonsDTO, partyId, contractId, redirectAttributes);
	}
	
}