package com.danger.insurance.insurances.contracts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsAssigningServices;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsProcesingServices;

/**
 * Controller for locating and reviewing insurance contracts.
 * Mapped under "/insurances", this class helps users search for existing contracts
 * based on identifiers, policyholder details, contract status, or policy tier.
 * It may support both direct ID lookup and filtered searches via form input.
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
public class FindContractsController {
	
	@Autowired
	private ContractsAssigningServices assigningServices;
	
	@Autowired
	private ContractsProcesingServices procesingServices;
	
	/**
	 * Renders the search form for locating insurance contracts.
	 * Triggered via GET request to "/insurances/find", this method initializes the model
	 * with an empty filter form and optional preloaded selectors to guide user input.
	 * It sets the stage for a refined contract lookup based on multiple criteria.
	 *
	 * Perfect for:
	 * - Searching contracts by holder ID, status, region, or policy type
	 * - Filtering active vs expired, or personal vs corporate plans
	 * - Loading dropdowns for agent or region-based filtering
	 *
	 * @param model Spring MVC model to supply attributes for search form rendering
	 * @return view name of the contract search UI
	 */
	@GetMapping("find")
	public String renderFindContractsForm(Model model) {
		return assigningServices.addFindContractAttributes(model);
	}
	
	/**
	 * Handles contract search validation and result presentation.
	 * Triggered via POST to "/insurances/find/validate", this method processes user-input
	 * search filters encapsulated in ContractsDTO. It validates the criteria, performs contract
	 * lookup via the service layer, and populates the model with matching results.
	 * If validation fails or no matches are found, user feedback is displayed.
	 *
	 * Considerations:
	 * - Ensure meaningful input exists (e.g. contract ID, status, policy type)
	 * - Display error if no results match the criteria
	 * - Preserve entered form data to allow refinement and retry
	 *
	 * @param contractsDTO DTO containing contract search criteria
	 * @param model Spring MVC model used to render form or results
	 * @param redirectAttributes used to carry data or messaging across redirects
	 * @return the name of the result view or redirect back to form on failure
	 */
	@PostMapping("find/validate")
	public String handleFindContractsFormPost(@ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model, RedirectAttributes redirectAttributes) {
		return procesingServices.processFindContractsFormPost(contractsDTO, redirectAttributes);
	}
	
	/**
	 * Displays the list of validated and matched insurance contracts.
	 * Accessed via GET at "/insurances/find/validated", this method prepares a result page
	 * showing contracts that matched the user's search criteria. It assumes that matching results
	 * were passed via RedirectAttributes or preloaded into the model.
	 *
	 * Ideal Features:
	 * - Paginated table of contract results with filtering or sorting controls
	 * - Inline action buttons to view, edit, or export contract details
	 * - Visual indicators for contract status (e.g., active, expired, pending)
	 *
	 * @param model Spring MVC model used to pass contract data to the view
	 * @return the name of the view displaying contract search results
	 */
	@GetMapping("find/validated")
	public String renderFoundContractsList(Model model) {
		String referenceLink = "/insurances/contract-";
		String returnedPage = "pages/insurances/contracts/list";
		
		
		return assigningServices.addSelectListAttributes(referenceLink, returnedPage, model);
	}

}