package com.danger.insurance.incidents.controllers.crud;

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

import com.danger.insurance.archive.models.dto.RemoveIncidentReasonsDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.incidents.models.service.support.IncidentsAssigningServices;
import com.danger.insurance.incidents.models.service.support.IncidentsProcesingServices;
import com.danger.insurance.incidents.models.service.support.IncidentsVerifyingServices;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.validation.groups.OnRemoveIncident;

/**
 * Controller responsible for managing the incident removal workflow.
 * The class is session-scoped for "incidentRemovalReasonsDTO", allowing
 * user-provided removal reasons or related metadata to persist across multiple steps
 * in the incident removal process.
 */
@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("incidents")
@SessionAttributes("incidentRemovalReasonsDTO")
public class RemoveIncidentsController {
	
	@Autowired
	private IncidentsAssigningServices assigningServices;
	
	@Autowired
	private IncidentsProcesingServices procesingServices;
	
	@Autowired
	private IncidentsVerifyingServices verifyingServices;
		
	/**
	 * Provides a fresh RemoveIncidentReasonsDTO to the model under the name "incidentRemovalReasonsDTO".
	 * This method ensures the removal workflow begins with a clean slate, enabling
	 * consistent form binding and validation across controller methods tied to incident removal.
	 *
	 * Invoked automatically by Spring MVC before handling requests that require
	 * "incidentRemovalReasonsDTO" in the model scope.
	 *
	 * @return a newly instantiated DTO containing default removal reason fields
	 */
	@ModelAttribute("incidentRemovalReasonsDTO")
	public RemoveIncidentReasonsDTO removeIncidentReasonsDTO() {
	    return new RemoveIncidentReasonsDTO(); 											// Only called once, when session starts
	}
	
	/**
	 * Renders the form for selecting an incident to remove.
	 * Accessed via GET request to "/delete", this method serves as the entry point
	 * for the removal workflow. It prepares the model with necessary data like
	 * removable incident options, user context, and session-bound removal DTO.
	 *
	 * Ideal for displaying:
	 * - A list of eligible incidents for removal
	 * - Dropdowns or justification fields pre-filled by policy
	 * - Dynamic flags (e.g. removal restriction based on incident status)
	 *
	 * @param model the Spring MVC model used to populate view attributes
	 * @return the name of the view template for the removal form
	 */
	@GetMapping("delete")
	public String renderIncidentToRemoveForm(Model model) {
		return assigningServices.addIncidentToRemoveAttributes(removeIncidentReasonsDTO(), model);
	}
	
	/**
	 * Validates the submitted incident removal form.
	 * Handles POST requests to "/delete/validate", applying validation rules under
	 * the OnRemoveIncident group. If validation fails, the user is redirected back
	 * with feedback; if successful, the workflow proceeds to confirmation.
	 *
	 * @param removeIncidentReasonsDTO DTO capturing reason(s) for removal, validated against custom rules
	 * @param bindingResult contains outcome of validation and binding
	 * @param redirectAttributes carries messages or form state across redirects
	 * @return redirect path based on validation result
	 */
	@PostMapping("delete/validate")
	public String validateIncidentToRemoveFormPost(@Validated(OnRemoveIncident.class) @ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return verifyingServices.verifyIncidentToRemoveFormPost(removeIncidentReasonsDTO, bindingResult, redirectAttributes);
	}
	
	/**
	 * Renders the interface for searching and selecting incidents to remove.
	 * This GET endpoint at "/delete/find" provides a view to refine removal targets
	 * using user-defined criteria from RemoveIncidentReasonsDTO. It's a midpoint
	 * in the incident removal workflow, allowing the user to locate incidents
	 * matching selected rationale or filtering parameters.
	 *
	 * Typical Use Cases:
	 * - Filter incidents by age, severity, resolution status, or tags
	 * - Preload justification info into the form for contextual removal
	 * - Display a list of candidates for confirmation and final action
	 *
	 * @param removeIncidentReasonsDTO DTO containing removal context or filter hints
	 * @param model Spring's model to populate the search form and results
	 * @return the view name to render incident search and selection UI
	 */
	@GetMapping("delete/find")
	public String renderFindIncidentToRemoveForm(@ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO, Model model) {
		String formName = FormNames.INCIDENTS_REMOVE.getDisplayName() + " - dohledání události";
		String formAction = "find/validate";
		boolean isSearchForm = true;
		boolean isCreateForm = true;
		
		return assigningServices.addFindIncidentsAttributes(model, formName, ButtonLabels.FIND, isSearchForm, isCreateForm, formAction);
	}
	
	/**
	 * Validates the search form used to identify incidents for removal.
	 * This POST handler receives user-provided search criteria via IncidentsFindPostDTO 
	 * and cross-checks it with session-scoped RemoveIncidentReasonsDTO for contextual alignment.
	 * It validates search intent, applies filters, and prepares the model with matching results.
	 * If no valid selection or result is found, the user is prompted with an error to refine input.
	 *
	 * @param removeIncidentReasonsDTO session-bound DTO capturing removal rationale and context
	 * @param incidentsFindPostDTO DTO containing user-specified search criteria
	 * @param model Spring model for injecting attributes into the resulting view
	 * @param redirectAttributes used to pass data or messages during redirects
	 * @return view name or redirect path based on outcome of validation and filtering
	 */
	@PostMapping("delete/find/validate")
	public String validateFindIncidentToRemoveFormPost(@ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO, 
													   @ModelAttribute("formDTO") IncidentsFindPostDTO incidentsFindPostDTO , Model model, RedirectAttributes redirectAttributes) {
		String successRedirect = "redirect:/incidents/delete/find/select";
		String failRedirect = "incidents/delete/find";
		
		return verifyingServices.verifyFindIncidentsFormPost(model, incidentsFindPostDTO, successRedirect, failRedirect, redirectAttributes);
	}
	
	/**
	 * Renders the list view for selecting incidents to remove.
	 * This GET endpoint at "/delete/find/select" uses the provided session-scoped
	 * RemoveIncidentReasonsDTO to maintain context (e.g., removal rationale or filters),
	 * and populates the model with a list of eligible incidents that match the criteria.
	 *
	 * Ideal for:
	 * - Displaying filtered, selectable incidents (e.g. resolved, outdated, low severity)
	 * - Presenting contextual hints based on removal reasons
	 * - Guiding users toward confirming one or more incidents for deletion
	 *
	 * @param removeIncidentReasonsDTO DTO containing context for removal workflow
	 * @param model the Spring MVC model for passing view attributes
	 * @return the name of the view displaying selectable incident candidates
	 */
	@GetMapping("delete/find/select") 
	public String renderSelectIncidentToRemoveList(@ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO, Model model) {
		String referenceLink = "selected-";
		String returnedPage = "pages/incidents/list";
		
		return assigningServices.addSelectIncidentListAttributes(model, referenceLink, returnedPage);
	}
	
	/**
	 * Renders the confirmation view for removing a specific incident.
	 * Triggered via GET request to "/delete/find/selected-{incidentId}", this method
	 * uses the path variable to retrieve the targeted incident for review before removal.
	 * It ensures the incident exists, binds it alongside the removal rationale DTO,
	 * and presents the user with a final review opportunity.
	 *
	 * Useful for:
	 * - Displaying incident details and context before deletion
	 * - Showcasing user-entered removal reasons or justification
	 * - Presenting warnings if incident is tied to active workflows or constraints
	 *
	 * @param incidentId ID of the incident selected for removal
	 * @param removeIncidentReasonsDTO session-scoped DTO containing removal rationale
	 * @param model Spring's model to pass attributes to the view
	 * @return the name of the view template for removal confirmation
	 */
	@GetMapping("delete/find/selected-{incidentId}")
	public String renderConfirmIncidentToRemoveForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO, Model model) {
		return assigningServices.addConfirmIncidentToRemoveAttributes(incidentId, model);
	}
	
	/**
	 * Finalizes the removal of a specific incident.
	 * This POST handler at "/delete/find/selected-{incidentId}/confirmed" confirms the user's
	 * intent to remove the selected incident, performs deletion via the service layer,
	 * logs the rationale provided in RemoveIncidentReasonsDTO, and clears session attributes.
	 * Redirects to a summary or acknowledgment view upon completion.
	 *
	 * Key Operations:
	 * - Validate the incident ID and its removability
	 * - Persist removal action and justification
	 * - Reset session scope via SessionStatus
	 * - Provide feedback for user confirmation via RedirectAttributes
	 *
	 * @param incidentId the ID of the incident to be removed
	 * @param removeIncidentReasonsDTO DTO containing user-provided removal rationale
	 * @param sessionStatus Spring’s handle to clear @SessionAttributes post-processing
	 * @param redirectAttributes carrier for post-action messages or status indicators
	 * @return redirect URL to final confirmation or dashboard
	 */
	@PostMapping("delete/find/selected-{incidentId}/confirmed")
	public String handleConfirmIncidentToRemoveFormPost(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		return procesingServices.processConfirmIncidentToRemoveFormPost(incidentId, removeIncidentReasonsDTO, redirectAttributes, sessionStatus);
	}
	
}