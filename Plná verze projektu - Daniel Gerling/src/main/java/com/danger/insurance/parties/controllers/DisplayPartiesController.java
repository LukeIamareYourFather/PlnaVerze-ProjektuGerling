package com.danger.insurance.parties.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.danger.insurance.parties.models.dto.PartiesFoundSendDTO;
import com.danger.insurance.parties.models.service.PartiesAssigningServices;
import com.danger.insurance.shared.enums.ActivePageTokens;
import com.danger.insurance.shared.services.CommonSupportServiceShared;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller responsible for displaying party entities such as policy owners, clients, or vendors.
 * <p>
 * Mapped under the "/parties" base path, this controller provides endpoints for viewing
 * individual party details, listing all registered parties, and optionally filtering or
 * paginating results. It supports both administrative and user-facing views depending on
 * the context and access level.
 * </p>
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("/parties")
public class DisplayPartiesController {
	
	// Object initialization
	
	@Autowired
	private CommonSupportServiceShared supportServiceShared;
	
	@Autowired
	private PartiesAssigningServices assigningServices;
	
	// Start of code
	
	/**
	 * Renders the index view for the parties module.
	 * <p>
	 * Triggered via GET at "/parties", this method serves as the landing page for users
	 * interacting with party entities such as policy owners, clients, or vendors. It clears
	 * any session-scoped attributes to reset previous workflows and prepares the model for
	 * rendering the index interface.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Reset session state to ensure a clean start</li>
	 *   <li>Prepare model attributes for rendering the index view</li>
	 *   <li>Optionally inspect the HTTP request for personalization or analytics</li>
	 * </ul>
	 *
	 * <h2>Session Management:</h2>
	 * <ul>
	 *   <li>{@code SessionStatus.setComplete()} – clears session attributes from prior flows</li>
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
	 *   <li>Acts as the entry point for party-related operations</li>
	 *   <li>May include links to create, view, or manage party records</li>
	 * </ul>
	 *
	 * @param httpRequest the incoming HTTP request, used for context or analytics
	 * @param sessionStatus used to clear session attributes and reset workflow state
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the parties index page
	 */
	@GetMapping
	public String renderIndex(HttpServletRequest httpRequest, SessionStatus sessionStatus, Model model) {
		supportServiceShared.removeAllSessionAttributes(ActivePageTokens.PARTIES, httpRequest, sessionStatus, model);
		
		return "pages/parties/index";															// Redirect to the main page
	}
	
	/**
	 * Renders the view displaying party profiles that match search or filter criteria.
	 * <p>
	 * Triggered via GET at "/parties/found/profiles", this method receives a populated
	 * {@code PartiesFoundSendDTO} containing search parameters or filter selections.
	 * It retrieves the matching party profiles and prepares the model for rendering.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Interpret search or filter criteria from {@code findingsDto}</li>
	 *   <li>Fetch matching party profiles from the service or repository layer</li>
	 *   <li>Populate the model with results and contextual metadata</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code matchedProfiles} – list of party entities that match the criteria</li>
	 *   <li>{@code searchSummary} – optional summary of applied filters or keywords</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed after submitting a search or filter form</li>
	 *   <li>Used by administrators or users to locate specific party records</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Support pagination and sorting of results</li>
	 *   <li>Enable export of matched profiles to CSV or PDF</li>
	 *   <li>Integrate fuzzy search or synonym matching</li>
	 * </ul>
	 *
	 * @param sendDto DTO containing search or filter parameters
	 * @param model Spring MVC model used to pass matched profiles to the view
	 * @return name of the view template that displays the filtered party profiles
	 */
	@GetMapping("found/profiles")
	public String renderFoundProfiles(@ModelAttribute("findingsDto") PartiesFoundSendDTO sendDto, Model model) {
		return assigningServices.addFoundProfilesAttributes(sendDto, model);
	}
	
	/**
	 * Renders the detail view for a specific party profile.
	 * <p>
	 * Triggered via GET at "/parties/profile-{partyId}", this method retrieves the full
	 * profile of the party identified by {@code partyId} and prepares the model for rendering.
	 * It supports both administrative and user-facing views, depending on access level and context.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Fetch party details from the service or repository layer</li>
	 *   <li>Populate the model with party data for rendering</li>
	 *   <li>Handle missing or invalid party IDs gracefully</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code partyProfile} – full metadata and attributes of the selected party</li>
	 *   <li>{@code displayMode} – optional flag for customizing the view (e.g. read-only, editable)</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed from party lists, search results, or confirmation flows</li>
	 *   <li>May include links to edit, delete, or audit the party record</li>
	 * </ul>
	 *
	 * <h2>Error Handling:</h2>
	 * <ul>
	 *   <li>If party not found → redirect to error page or show fallback message</li>
	 * </ul>
	 *
	 * @param partyId ID of the party entity to display
	 * @param model Spring MVC model used to pass party data to the view
	 * @return name of the view template that displays the party profile
	 */
	@GetMapping("profile-{partyId}")
	public String renderProfile(@PathVariable long partyId, Model model) {
		return assigningServices.addProfilesListAttributes(partyId, model);
	}
	
	/**
	 * Renders the view displaying all registered party profiles.
	 * <p>
	 * Triggered via GET at "/parties/parties-list", this method retrieves the complete
	 * collection of party entities (e.g., policy owners, clients, vendors) and prepares
	 * the model for rendering. It supports administrative oversight, bulk actions, and
	 * general browsing of party records.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Fetch all party profiles from the service or repository layer</li>
	 *   <li>Populate the model with the full list of parties</li>
	 *   <li>Support dynamic UI elements such as filters, status indicators, or action buttons</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code allProfiles} – list of all registered party entities</li>
	 *   <li>{@code listMetadata} – optional metadata for pagination, sorting, or filtering</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by administrators or analysts for oversight and reporting</li>
	 *   <li>May include links to view, edit, or remove individual party records</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Enable export to CSV, Excel, or PDF</li>
	 *   <li>Integrate advanced filtering (e.g. by region, type, registration date)</li>
	 *   <li>Support bulk actions such as tagging or archiving</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass party data to the view
	 * @return name of the view template that displays the full party list
	 */
	@GetMapping("parties-list")
	public String renderAllProfilesList(Model model) {
		return assigningServices.addAllProfilesListAttributes(model);
	}
	
	/**
	 * Renders the view displaying all deleted or archived party profiles.
	 * <p>
	 * Triggered via GET at "/parties/deleted-parties-list", this method retrieves party entities
	 * that have been flagged as deleted, deactivated, or archived. It prepares the model for rendering
	 * a separate view that supports administrative review, restoration, or permanent removal.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Fetch all party profiles marked as deleted or inactive</li>
	 *   <li>Populate the model with the list of deleted parties</li>
	 *   <li>Support UI elements for restoration, audit, or cleanup actions</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code deletedProfiles} – list of party entities with deleted status</li>
	 *   <li>{@code deletionMetadata} – optional metadata such as deletion date or reason</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by administrators for audit, recovery, or cleanup operations</li>
	 *   <li>May include links to restore, permanently delete, or view history</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Enable filtering by deletion reason or date</li>
	 *   <li>Support bulk restoration or purge actions</li>
	 *   <li>Integrate audit trail or change history per profile</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass deleted party data to the view
	 * @return name of the view template that displays the deleted party list
	 */
	@GetMapping("deleted-parties-list")
	public String renderDeletedProfilesList(Model model) {
		return assigningServices.addDeletedProfilesListAttributes(model);
	}
	
	/**
	 * Renders the fallback view when a requested party profile is not found.
	 * <p>
	 * Triggered via GET at "/parties/not-found", this method displays a user-friendly
	 * error page indicating that the requested party entity could not be located.
	 * It may be used after failed lookups, invalid IDs, or deleted records.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Display a clear message explaining the missing party profile</li>
	 *   <li>Provide navigation options to return to search or list views</li>
	 *   <li>Optionally log the event for audit or diagnostic purposes</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Redirect target after failed profile lookups</li>
	 *   <li>Fallback for invalid or deleted party IDs</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Include search suggestions or recently viewed profiles</li>
	 *   <li>Offer contact support or report issue links</li>
	 * </ul>
	 *
	 * @return name of the view template that displays the "party not found" page
	 */
	@GetMapping("not-found")
	public String renderPartiesNotFoundPage(){
		return "pages/parties/not-found";
	}
	
}