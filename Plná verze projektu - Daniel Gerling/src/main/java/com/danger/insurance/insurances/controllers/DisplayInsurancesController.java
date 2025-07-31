package com.danger.insurance.insurances.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.danger.insurance.insurances.contracts.models.services.support.ContractsAssigningServices;
import com.danger.insurance.insurances.models.services.InsurancesAssigningServices;
import com.danger.insurance.shared.enums.ActivePageTokens;
import com.danger.insurance.shared.services.CommonSupportServiceShared;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller responsible for displaying insurance contract information.
 * <p>
 * Mapped under the "/insurances" base path, this controller provides endpoints
 * for viewing individual insurance contracts, listing all active or archived contracts,
 * and presenting filtered or paginated views for administrative or user-facing dashboards.
 * </p>
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
public class DisplayInsurancesController {

	@Autowired
	private CommonSupportServiceShared supportServiceShared;
	
	@Autowired
	private InsurancesAssigningServices assigningServices;
	
	@Autowired
	private ContractsAssigningServices contractsAssigningServices;
	
	/**
	 * Renders the index view for the insurance module.
	 * <p>
	 * Triggered via a GET request to "/insurances", this method serves as the landing page
	 * for users interacting with insurance contracts. It may display a dashboard, summary,
	 * or navigation options depending on the user's role and session state.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Clear any session-scoped attributes to reset workflow state</li>
	 *   <li>Prepare model attributes for rendering the index view</li>
	 *   <li>Optionally log or inspect request metadata for analytics or personalization</li>
	 * </ul>
	 *
	 * <h2>Session Management:</h2>
	 * <ul>
	 *   <li>{@code SessionStatus.setComplete()} – clears session attributes from previous flows</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code welcomeMessage} – optional greeting or status message</li>
	 *   <li>{@code userContext} – optional user-specific data for personalization</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Acts as the entry point for insurance-related operations</li>
	 *   <li>May include links to create, view, or delete contracts</li>
	 * </ul>
	 *
	 * @param httpRequest the incoming HTTP request, used for metadata or session inspection
	 * @param sessionStatus used to clear session attributes and reset workflow state
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the insurance index page
	 */
	@GetMapping
	public String renderIndex(HttpServletRequest httpRequest, SessionStatus sessionStatus, Model model) {
		supportServiceShared.removeAllSessionAttributes(ActivePageTokens.INSURANCES, httpRequest, sessionStatus, model);
		
		return "pages/insurances/index";
	}
	
	/**
	 * Renders the detail view for a specific insurance contract.
	 * <p>
	 * Triggered via GET at "/insurances/insurance-{insurancesId}", this method retrieves
	 * the full details of the insurance contract identified by {@code insurancesId} and
	 * populates the model for display. It supports both user-facing and administrative views,
	 * depending on the context and permissions.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Fetch contract data from the service or repository layer</li>
	 *   <li>Populate the model with contract attributes for rendering</li>
	 *   <li>Handle missing or invalid contract IDs gracefully</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code insuranceDetails} – full metadata of the selected insurance contract</li>
	 *   <li>{@code displayMode} – optional flag for user/admin view customization</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed from contract lists, search results, or confirmation flows</li>
	 *   <li>May include links to edit, delete, or audit the contract</li>
	 * </ul>
	 *
	 * <h2>Error Handling:</h2>
	 * <ul>
	 *   <li>If contract not found → redirect to error page or show fallback message</li>
	 * </ul>
	 *
	 * @param insurancesId ID of the insurance contract to display
	 * @param model Spring MVC model used to pass contract data to the view
	 * @return name of the view template that displays insurance contract details
	 */
	@GetMapping("insurance-{insurancesId}")
	public String renderInsuranceDetails(@PathVariable("insurancesId") long insurancesId, Model model) {
		return assigningServices.addInsuranceDetailsAttributes(insurancesId, model);
	}
	
	/**
	 * Renders the view displaying a list of insurance contracts.
	 * <p>
	 * Triggered via GET at "/insurances/list", this method retrieves a collection of
	 * insurance contracts and populates the model for rendering in a tabular or card-based
	 * format. It may support filtering, sorting, or pagination depending on the implementation.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Fetch all or filtered insurance contracts from the service layer</li>
	 *   <li>Populate the model with contract data for display</li>
	 *   <li>Support dynamic UI elements such as status indicators or action buttons</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code insuranceList} – collection of insurance contracts to be displayed</li>
	 *   <li>{@code listMetadata} – optional metadata for pagination, sorting, or filtering</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed from the index page or navigation menu</li>
	 *   <li>Used by administrators or users to browse available contracts</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Add search filters (e.g. by tier, expiration date, status)</li>
	 *   <li>Enable export to CSV or PDF</li>
	 *   <li>Integrate real-time updates or status badges</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the insurance contract list
	 */
	@GetMapping("list")
	public String renderInsuranceList(Model model) {
		String referenceLink = "insurance-";
		
		return assigningServices.addInsurancesListWithAttributes(referenceLink, model);
	}
	
	/**
	 * Renders the detail view for a specific contract.
	 * <p>
	 * Triggered via GET at "/insurances/contract-{contractId}", this method retrieves
	 * the full metadata of the contract identified by {@code contractId} and populates
	 * the model for display. It supports both administrative and user-facing views,
	 * depending on the context and permissions.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Fetch contract details from the service or repository layer</li>
	 *   <li>Populate the model with contract data for rendering</li>
	 *   <li>Handle missing or invalid contract IDs gracefully</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code contractDetails} – full metadata of the selected contract</li>
	 *   <li>{@code displayMode} – optional flag for customizing the view (e.g. read-only, editable)</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed from contract lists, search results, or confirmation flows</li>
	 *   <li>May include links to edit, delete, or audit the contract</li>
	 * </ul>
	 *
	 * <h2>Error Handling:</h2>
	 * <ul>
	 *   <li>If contract not found → redirect to error page or show fallback message</li>
	 * </ul>
	 *
	 * @param contractId ID of the contract to display
	 * @param model Spring MVC model used to pass contract data to the view
	 * @return name of the view template that displays contract details
	 */
	@GetMapping("contract-{contractId}")
	public String renderContractDetails(@PathVariable("contractId") long contractId, Model model) {
		return contractsAssigningServices.addContractDetailsAttributes(contractId, model);
	}
	
}