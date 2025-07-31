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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsAssigningServices;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsProcesingServices;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsVeryfyingServices;
import com.danger.insurance.validation.groups.OnRemoveContract;

/**
 * Controller for orchestrating contract-level removal operations in the insurance domain.
 * Mapped under "/insurances", this class handles workflows where entire insurance contracts
 * may be voided, terminated, or retracted based on predefined criteria. The controller supports
 * session-scoped handling via @SessionAttributes("removalReasonsDTO") to preserve removal justification
 * throughout multi-step flows.
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
@SessionAttributes("removalReasonsDTO")
public class RemoveContractsContoller {
	
	@Autowired
	private ContractsAssigningServices assigningServices;
	
	@Autowired
	private ContractsProcesingServices procesingServices;
	
	@Autowired
	private ContractsVeryfyingServices veryfyingServices;

	/**
	 * Initializes the RemoveContractReasonsDTO for session usage during contract termination flows.
	 * Bound as a model attribute named "removalReasonsDTO", this method ensures that the controller
	 * has a non-null DTO available across requests, preserving the reason context throughout
	 * multi-step removal interactions.
	 *
	 * Application Role:
	 * - Tracks selected removal reason (e.g., voluntary termination, duplicate coverage)
	 * - Enables continuity across confirmation and finalization stages
	 * - Can support overrides, severity indicators, or multi-party tracking as needed

	 * @return a fresh RemoveContractReasonsDTO instance
	 */
	@ModelAttribute("removalReasonsDTO")
	public RemoveContractReasonsDTO reasonsDto() {
	    return new RemoveContractReasonsDTO(); 											// Only called once, when session starts
	}
	
	/**
	 * Renders the form interface for terminating one or more insurance contracts.
	 * Accessed via GET at "/insurances/terminate", this method presents active contracts 
	 * with associated metadata and prompts the user to select a termination reason.
	 * It initiates the workflow for contract-level removal under administrative or business criteria.
	 *
	 * Ideal UI Elements:
	 * - List of active contracts with filtering options (e.g., by tier, region, expiration)
	 * - Dropdown or radio buttons for selecting termination reason
	 * - Optional conflict badges (e.g., “locked by claims” or “pending audit”)
	 * - Breadcrumb or hint section to guide user through termination implications
	 *
	 * @param model Spring MVC model used to supply contract data and form setup to the view
	 * @return name of the JSP/Thymeleaf page that renders the termination form
	 */
	@GetMapping("terminate")
	public String renderRemoveInsurancesForm(Model model) {
		return assigningServices.addRemoveInsurancesFormAttributes(reasonsDto(), model);
	}
	
	/**
	 * Validates the termination form for removing one or more insurance contracts.
	 * Accessed via POST at "/insurances/terminate/validate", this method checks that
	 * the termination reason and contract selection are valid and eligible for removal.
	 * On validation failure, it redirects with error feedback and preserved form input.
	 * On success, it prepares the session context for confirmation.
	 *
	 * Validation Goals:
	 * - Enforce @Validated(OnRemoveContract.class) rules (e.g., required reason, valid contract)
	 * - Use BindingResult to flag missing or conflicting fields
	 * - Confirm selected contract is terminable (e.g., no pending claims or locks)
	 * - Carry context forward for confirmation page setup
	 *
	 * @param removeContractReasonsDTO DTO holding selected contract and termination reason
	 * @param bindingResult container for validation feedback
	 * @param redirectAttributes flash container to preserve form state across redirect
	 * @return redirect to confirmation view on success, or back to form on failure
	 */
	@PostMapping("terminate/validate")
	public String validateRemoveInsurancesFormPost(@Validated(OnRemoveContract.class) @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return veryfyingServices.verifyRemoveInsurancesFormPost(removeContractReasonsDTO, bindingResult, redirectAttributes, "insurances/terminate/find", "insurances/terminate");
	}
		
	/**
	 * Renders the contract search form for locating insurance contracts eligible for termination.
	 * Accessed via GET at "/insurances/terminate/find", this method initiates the identification
	 * process using filter inputs or metadata preserved in RemoveContractReasonsDTO.
	 * It helps agents, admins, or managers search for contracts based on criteria such as region, tier, party, or expiration date.
	 *
	 * Ideal Additions:
	 * - Search input fields (e.g., contract number, insured name, coverage region)
	 * - Smart hints or warnings for contracts nearing expiration or flagged by compliance
	 * - Optional autocomplete or filter presets based on session DTO
	 *
	 * @param removeContractReasonsDTO session attribute storing context like termination reason or scope
	 * @param model Spring MVC model used to populate the search form
	 * @return name of the view that presents the contract lookup interface
	 */
	@GetMapping("terminate/find")
	public String renderFindContractToRemoveForm(@SessionAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		return assigningServices.addFindContractToRemoveForm(model);
	}
	
	/**
	 * Validates input from the contract search form used to locate terminable insurance contracts.
	 * Triggered via POST to "/insurances/terminate/find/validate", this method ensures that the user's
	 * search input (stored in ContractsDTO) is sufficient to perform a meaningful lookup. If input fails
	 * validation or no matching contracts are found, feedback is provided. Otherwise, matched contracts
	 * are passed forward for user selection.
	 *
	 * Validation Flow:
	 * - Ensure ContractsDTO contains at least one valid search criterion
	 * - Use RemoveContractReasonsDTO to filter contracts by removal context (e.g. reason, region)
	 * - Preserve search input and feedback across redirect on error
	 * - Pass matched results and context to selection view upon success
	 *
	 * @param removeContractReasonsDTO Session DTO storing reason and scope of termination
	 * @param contractsDTO DTO with user input for searching contracts
	 * @param redirectAttributes Flash container for results, messages, and state continuity
	 * @return Redirect path to selection view or back to form on validation failure
	 */
	@PostMapping("terminate/find/validate")
	public String validateFindContractToRemoveForm(@SessionAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, RedirectAttributes redirectAttributes) {
		return veryfyingServices.verifyFindContractToremoveFormPost(contractsDTO, redirectAttributes);
	}
	
	/**
	 * Displays a list of contracts eligible for termination based on prior search input.
	 * Accessed via GET at "/insurances/terminate/select", this method presents insurance contracts
	 * that matched validation criteria and termination reason scope, allowing the user to choose
	 * which contract to proceed with for removal.
	 *
	 * Features Worth Including:
	 * - Table or card layout for contract metadata (e.g., contract ID, coverage type, tier)
	 * - Icons or indicators for termination eligibility, claim holds, or compliance risks
	 * - Select controls (radio or action links) to initiate confirmation of termination
	 * - Breadcrumbs or page hints for user orientation
	 *
	 * @param removeContractReasonsDTO session-bound DTO storing termination reason and context
	 * @param contractsDTO DTO holding search criteria and optional matched results
	 * @param model MVC model used to populate the list view
	 * @return view name displaying matched terminable contracts
	 */
	@GetMapping("terminate/select")
	public String renderSelectContractList(@SessionAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		return assigningServices.addSelectContractListAttributes(contractsDTO, model);
	}
	
	/**
	 * Renders the final confirmation view for terminating a specific insurance contract.
	 * Accessed via GET at "/insurances/terminate/selected-{contractId}", this method provides
	 * users with detailed contract information, the selected termination reason, and any relevant
	 * impact notes prior to committing to removal. It’s a pivotal checkpoint for policy integrity.
	 *
	 * Ideal Data to Display:
	 * - Contract details (e.g. ID, tier, coverage dates, status)
	 * - Associated insured parties, if applicable
	 * - Reason for termination and optional metadata (e.g. severity, source of request)
	 * - Confirmation disclaimer and optional approval checkbox
	 *
	 * @param contractId ID of the insurance contract being considered for termination
	 * @param removeContractReasonsDTO DTO tracking reason and session context
	 * @param model Spring MVC model used to pass contract data and messaging to the view
	 * @return name of the view displaying contract termination confirmation
	 */
	@GetMapping("terminate/selected-{contractId}") 
	public String renderDeleteContractConfirmation(@PathVariable("contractId") long contractId, @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		return assigningServices.addDeleteContractConfirmationAttributes(contractId, model);
	}
	
	/**
	 * Finalizes the termination of a specified insurance contract.
	 * This POST handler at "/insurances/terminate/selected-{contractId}/confirmed" commits the contract
	 * removal, logs the termination reason, and redirects with feedback. It’s the final step in the 
	 * contract removal workflow, ensuring the action is recorded and consequences are cascaded.
	 *
	 * Operations Performed:
	 * - Verifies contract eligibility for termination (e.g., not locked by audits or pending claims)
	 * - Persists termination reason for compliance and auditing
	 * - Executes contract deactivation via service logic
	 * - Optionally triggers notifications or downstream updates
	 * - Provides feedback to user upon success or failure
	 *
	 * @param contractId ID of the contract selected for termination
	 * @param removeContractReasonsDTO session-scoped DTO carrying termination reason and context
	 * @param redirectAttributes flash container for status messages
	 * @return redirect path to overview page or fallback error view
	 */
	@PostMapping("terminate/selected-{contractId}/confirmed")
	public String handleDeleteConfirmationPost(@PathVariable("contractId") long contractId, @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, RedirectAttributes redirectAttributes) {
		return procesingServices.processDeleteConfirmationPost(removeContractReasonsDTO, contractId, redirectAttributes);
	}
	
}