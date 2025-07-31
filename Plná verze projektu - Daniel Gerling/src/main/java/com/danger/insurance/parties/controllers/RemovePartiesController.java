//  (•_•)
// <)   )╯ Clean
// /    \   Code
package com.danger.insurance.parties.controllers;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesReasonsFormDTO;
import com.danger.insurance.parties.models.service.PartiesAssigningServices;
import com.danger.insurance.parties.models.service.PartiesProcesingServices;
import com.danger.insurance.validation.groups.OnDeleteParty;

/**
 * Controller responsible for handling the removal of party entities.
 * <p>
 * Mapped under the "/parties" base path and scoped with {@code @SessionAttributes("reasonsDTO")},
 * this controller provides endpoints for initiating, confirming, and executing the deletion
 * of party records such as policy owners, clients, or vendors. It supports workflows that
 * include reason tracking, audit logging, and conditional deletion logic.
 * </p>
 */
@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("parties")
@SessionAttributes("reasonsDTO")
public class RemovePartiesController {

	// Object initialization
	
	@Autowired
	private PartiesAssigningServices assigningServices;
	
	@Autowired
	private PartiesProcesingServices procesingServices;
	
	/**
	 * Initializes the {@code PartiesReasonsFormDTO} used to capture deletion reasons.
	 * <p>
	 * This method is annotated with {@code @ModelAttribute("reasonsDTO")} and is invoked
	 * automatically by Spring to populate the model and session scope with a fresh instance
	 * of {@code PartiesReasonsFormDTO}. It ensures that the deletion workflow has a consistent
	 * object available for binding user input across multiple steps.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Provide a clean instance of {@code PartiesReasonsFormDTO} for form binding</li>
	 *   <li>Enable session persistence of deletion reasons across controller methods</li>
	 *   <li>Support multi-step workflows that require reason tracking</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Used in {@code RemovePartiesController} to capture and retain deletion rationale</li>
	 *   <li>Supports audit logging and compliance workflows</li>
	 * </ul>
	 *
	 * @return a new instance of {@code PartiesReasonsFormDTO} for session and model binding
	 */
	@ModelAttribute("reasonsDTO")
	public PartiesReasonsFormDTO reasonsDto() {
	    return new PartiesReasonsFormDTO(); 											// Only called once, when session starts
	}
	
	// Start of code
	
	/**
	 * Renders the confirmation form for deleting a policy owner.
	 * <p>
	 * Triggered via GET at "/parties/delete", this method prepares the model with the necessary
	 * attributes to display a deletion confirmation interface. It may include contextual data
	 * such as the selected party’s name, deletion reasons, and warnings about irreversible actions.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Initialize the form backing object for capturing deletion reasons</li>
	 *   <li>Populate the model with party metadata or warnings</li>
	 *   <li>Forward to the view responsible for rendering the deletion confirmation form</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code reasonsDTO} – DTO used to capture user-provided deletion rationale</li>
	 *   <li>{@code partySummary} – optional metadata about the party being deleted</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by administrators before executing a deletion</li>
	 *   <li>Supports audit logging and accountability workflows</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Display related entities that may be affected by deletion</li>
	 *   <li>Include soft-delete toggle or recovery options</li>
	 *   <li>Require multi-factor confirmation for sensitive deletions</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the deletion confirmation form
	 */
	@GetMapping("delete")
	public String renderDeletePolicyOwnerForm(Model model) {
		return assigningServices.addDeletePolicyOwnerFormAttributes(reasonsDto(), model);
	}
	
	/**
	 * Validates and processes the deletion request for a policy owner.
	 * <p>
	 * Triggered via POST at "/parties/delete/validate", this method receives a populated
	 * {@code PartiesReasonsFormDTO} containing the user's rationale for deletion. It validates
	 * the input using the {@code OnDeleteParty} group, and if valid, proceeds to execute the
	 * deletion via the service layer. Feedback is passed using {@code RedirectAttributes}.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Validate the deletion reason and related input</li>
	 *   <li>Handle binding errors and return to the confirmation form if needed</li>
	 *   <li>Execute the deletion logic via the service layer</li>
	 *   <li>Pass success or error messages via flash attributes</li>
	 * </ul>
	 *
	 * <h2>Validation Group:</h2>
	 * <ul>
	 *   <li>{@code OnDeleteParty} – ensures required fields for deletion are present and valid</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On validation failure → return to "/parties/delete" with error messages</li>
	 *   <li>On success → redirect to "/parties/deleted-parties-list" with confirmation</li>
	 * </ul>
	 *
	 * <h2>Security Considerations:</h2>
	 * <ul>
	 *   <li>Ensure user is authorized to delete the specified party</li>
	 *   <li>Log deletion reason and action for audit and compliance</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Support soft-delete with recovery options</li>
	 *   <li>Include deletion impact analysis (e.g., linked policies or claims)</li>
	 *   <li>Trigger notifications or alerts upon deletion</li>
	 * </ul>
	 *
	 * @param reasonsDto DTO containing the user's deletion rationale
	 * @param bindingResult result of validation checks
	 * @param redirectAttributes used to pass feedback messages across redirects
	 * @return redirect path to either the confirmation form (on error) or deleted list view (on success)
	 */
	@PostMapping("delete/validate")
	public String handeDeletePolicyOwnerFormSubmit(@Validated(OnDeleteParty.class) @ModelAttribute("reasonsDTO") PartiesReasonsFormDTO reasonsDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return procesingServices.processDeletePolicyOwnerFormSubmit(reasonsDto, bindingResult, redirectAttributes);
	}
	
	/**
	 * Renders the search form for locating a policy owner to delete.
	 * <p>
	 * Triggered via GET at "/parties/delete/find", this method prepares the model with
	 * the necessary attributes to display a search interface for identifying a party
	 * entity targeted for deletion. It uses the session-scoped {@code reasonsDTO} to
	 * retain deletion rationale across steps and may include contextual feedback via
	 * {@code RedirectAttributes}.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Initialize or reuse the {@code reasonsDTO} for deletion tracking</li>
	 *   <li>Populate the model with search filters or metadata</li>
	 *   <li>Forward to the view responsible for rendering the search form</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code reasonsDTO} – session-scoped DTO for capturing deletion rationale</li>
	 *   <li>{@code searchOptions} – optional filters such as party type, region, or status</li>
	 * </ul>
	 *
	 * <h2>Redirect Attributes:</h2>
	 * <ul>
	 *   <li>Used to pass feedback or error messages from previous steps</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by administrators initiating a deletion workflow</li>
	 *   <li>Supports multi-step flows with reason tracking and audit compliance</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Enable autocomplete or fuzzy search for party names</li>
	 *   <li>Include deletion impact preview (e.g., linked policies)</li>
	 *   <li>Support bulk selection for multi-party deletion</li>
	 * </ul>
	 *
	 * @param reasonsDto session-scoped DTO used to retain deletion rationale
	 * @param model Spring MVC model used to pass attributes to the view
	 * @param redirectAttributes used to pass feedback messages across redirects
	 * @return name of the view template that displays the search form for deletion
	 */
	@GetMapping("delete/find")
	public String renderFindPolicyOwnerToDeleteForm(@ModelAttribute("reasonsDTO") PartiesReasonsFormDTO reasonsDto, Model model, RedirectAttributes redirectAttributes) {
		PartiesDetailsDTO wrongDTO = null;
		String firstKeyName = "formDTO";
		String secondKeyName = "formAction";
		String referenceLink = "/parties/delete/found";
		String formName = FormNames.PARTY_DELETE.getDisplayName() + " - " + FormNames.PARTY_FIND.getDisplayName().toLowerCase();
		String returnedPage = "pages/parties/find";
		boolean ifFindAssignmentRequested = true;
		
		return assigningServices.addSearchPolicyOwnersToDeleteAttributes(firstKeyName, secondKeyName, referenceLink, wrongDTO, PartyStatus.REGISTERED, model, ifFindAssignmentRequested, formName, returnedPage);
	}
	
	/**
	 * Processes the selection of a policy owner to delete after a search.
	 * <p>
	 * Triggered via POST at "/parties/delete/found", this method receives both the session-scoped
	 * {@code PartiesReasonsFormDTO} and the {@code PartiesDetailsDTO} representing the selected party.
	 * It prepares the model for rendering the final deletion confirmation view, combining the selected
	 * party’s metadata with the user’s rationale for deletion.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Bind the selected party details from the search result</li>
	 *   <li>Retain the deletion reason via {@code reasonsDTO}</li>
	 *   <li>Prepare the model for rendering the final confirmation interface</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code reasonsDTO} – session-scoped DTO containing deletion rationale</li>
	 *   <li>{@code partyDto} – DTO representing the selected party to be deleted</li>
	 *   <li>{@code confirmationMessage} – optional message summarizing the deletion intent</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Used in multi-step deletion workflows after identifying the target party</li>
	 *   <li>Supports audit logging and user accountability</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Display linked entities that may be affected by deletion</li>
	 *   <li>Include a preview of audit trail or deletion impact</li>
	 *   <li>Support multi-party selection and batch deletion</li>
	 * </ul>
	 *
	 * @param reasonsDto session-scoped DTO containing the user's deletion rationale
	 * @param partyDto DTO representing the selected party to be deleted
	 * @param model Spring MVC model used to pass attributes to the confirmation view
	 * @return name of the view template that displays the final deletion confirmation
	 */
	@PostMapping("delete/found")
	public String handleFoundPolicyOwnersToDelete(@ModelAttribute("reasonsDTO") PartiesReasonsFormDTO reasonsDto, @ModelAttribute("formDTO") PartiesDetailsDTO partyDto, Model model) {
		String firstKeyName = "foundParties";
		String secondKeyName = "referenceLink";
		String referenceLink = "/parties/delete/party-";
		String formName = null;
		String returnedPage = "pages/parties/found-parties";
		boolean ifFindAssignmentRequested = false;
		
		return assigningServices.addSearchPolicyOwnersToDeleteAttributes(firstKeyName, secondKeyName, referenceLink, partyDto, PartyStatus.REGISTERED, model, ifFindAssignmentRequested, formName, returnedPage);
		
	}
	
	/**
	 * Renders the final confirmation form for deleting a specific policy owner.
	 * <p>
	 * Triggered via GET at "/parties/delete/party-{partyId}", this method retrieves the party
	 * entity identified by {@code partyId} and prepares the model for rendering a confirmation
	 * interface. It uses the session-scoped {@code reasonsDTO} to retain the user's rationale
	 * and may include contextual warnings, metadata, or feedback via {@code RedirectAttributes}.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Fetch the party entity targeted for deletion</li>
	 *   <li>Retain and display the deletion reason via {@code reasonsDTO}</li>
	 *   <li>Populate the model with party metadata and confirmation prompts</li>
	 *   <li>Handle invalid or missing party IDs gracefully</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code reasonsDTO} – session-scoped DTO containing deletion rationale</li>
	 *   <li>{@code partyProfile} – metadata of the party to be deleted</li>
	 *   <li>{@code confirmationMessage} – optional message summarizing the deletion intent</li>
	 * </ul>
	 *
	 * <h2>Redirect Attributes:</h2>
	 * <ul>
	 *   <li>Used to pass feedback or error messages from previous steps</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Final step before executing deletion of a party entity</li>
	 *   <li>Supports audit logging, accountability, and user confirmation</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Display linked entities or dependencies affected by deletion</li>
	 *   <li>Include soft-delete toggle or recovery options</li>
	 *   <li>Require multi-factor confirmation for sensitive deletions</li>
	 * </ul>
	 *
	 * @param partyId ID of the party entity to be deleted
	 * @param reasonsDto session-scoped DTO containing the user's deletion rationale
	 * @param model Spring MVC model used to pass attributes to the confirmation view
	 * @param redirectAttributes used to pass feedback messages across redirects
	 * @return name of the view template that displays the final deletion confirmation form
	 */
	@GetMapping("delete/party-{partyId}")
	public String renderDeletePolicyOwnersConfirmationForm(@PathVariable long partyId, @ModelAttribute("reasonsDTO") PartiesReasonsFormDTO reasonsDto, Model model, RedirectAttributes redirectAttributes) {
		return assigningServices.addDeleteConfirmationAttributes(partyId, reasonsDto, model);
	}
	
	
	/**
	 * Executes the confirmed deletion of a specific policy owner.
	 * <p>
	 * Triggered via POST at "/parties/delete/party-{partyId}/confirmed", this method finalizes
	 * the deletion of the party entity identified by {@code partyId}. It uses the session-scoped
	 * {@code reasonsDTO} to log the rationale for deletion, clears the session state, and redirects
	 * to a feedback or summary view. This is the final step in a multi-phase deletion workflow.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Invoke service logic to delete the specified party</li>
	 *   <li>Log the deletion reason for audit and traceability</li>
	 *   <li>Clear session attributes to complete the workflow</li>
	 *   <li>Pass confirmation feedback via flash attributes</li>
	 * </ul>
	 *
	 * <h2>Session Management:</h2>
	 * <ul>
	 *   <li>{@code SessionStatus.setComplete()} – clears session-scoped {@code reasonsDTO}</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On success → redirect to "/parties/deleted-parties-list" with confirmation message</li>
	 *   <li>On failure → optionally redirect to error page or retry flow</li>
	 * </ul>
	 *
	 * <h2>Security Considerations:</h2>
	 * <ul>
	 *   <li>Ensure user is authorized to delete the specified party</li>
	 *   <li>Log deletion action and rationale for compliance</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Support soft-delete with recovery options</li>
	 *   <li>Trigger notifications or alerts upon deletion</li>
	 *   <li>Include deletion impact summary (e.g., linked policies or claims)</li>
	 * </ul>
	 *
	 * @param partyId ID of the party entity to be deleted
	 * @param reasonsDto session-scoped DTO containing the user's deletion rationale
	 * @param sessionStatus used to clear session attributes after completion
	 * @param redirectAttributes used to pass feedback messages across redirects
	 * @return redirect path to the deleted party list or confirmation view
	 */
	@PostMapping("delete/party-{partyId}/confirmed")
	public String handleDeletePolicyOwnerConfirmation(@PathVariable long partyId, @ModelAttribute("reasonsDTO") PartiesReasonsFormDTO reasonsDto, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		return procesingServices.processConfirmedRemoval(reasonsDto, partyId, sessionStatus, redirectAttributes);
	}

}