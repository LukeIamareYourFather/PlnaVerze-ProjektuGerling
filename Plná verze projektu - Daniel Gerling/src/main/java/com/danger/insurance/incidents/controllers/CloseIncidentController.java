package com.danger.insurance.incidents.controllers;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.incidents.models.dto.post.IncidentCommentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsClosePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.incidents.models.service.support.IncidentsAssigningServices;
import com.danger.insurance.incidents.models.service.support.IncidentsProcesingServices;
import com.danger.insurance.incidents.models.service.support.IncidentsVerifyingServices;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.validation.groups.OnCloseIncident;

/**
 * Controller class for managing incident closure workflows.
 * Annotated with @SessionAttributes to maintain the "incidentCommentDTO" across multiple
 * requests in a user's session — typically used to persist user-entered comments
 * while navigating between confirmation and submission views.
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("incidents")
@SessionAttributes("incidentCommentDTO")
public class CloseIncidentController {
	
	@Autowired
	private IncidentsAssigningServices assigningServices;
	
	@Autowired
	private IncidentsProcesingServices procesingServices;
	
	@Autowired
	private IncidentsVerifyingServices incidentsVerifyingServices;
		
	/**
     * Creates and returns a default instance of {@link IncidentsClosePostDTO}
     * to be used as the model attribute named "incidentCommentDTO".
     *
     * This method ensures that a new DTO object is available for binding
     * user input from the incident closure form. It supports multi-step workflows
     * and enables session persistence when paired with @SessionAttributes.
     *
     * Invoked automatically by Spring when the "incidentCommentDTO" is
     * required in a request and hasn't yet been set in the session.
     *
     * @return a new instance of IncidentsClosePostDTO used for incident closure form input
     */
	@ModelAttribute("incidentCommentDTO")
	public IncidentsClosePostDTO incidentsClosePostDTO() {
	    return new IncidentsClosePostDTO(); 											// Only called once, when session starts
	}

	/**
     * Handles HTTP GET requests to the /incidents/close endpoint.
     * Renders the form view for initiating an incident closure process.
     * This form typically allows the user to enter comments, confirm resolution,
     * and review relevant incident details before submitting for closure.
     *
     * It may also prepare the model with default or session-bound attributes,
     * such as a comment DTO or incident metadata, to support the form interaction.
     *
     * URL Mapping:
     * GET /incidents/close
     *
     * @param model the Spring Model used to pass data to the view layer,
     *              such as pre-filled form inputs or session DTOs
     * @return the name of the view template that displays the close incident form
     */
	@GetMapping("close")
	public String renderCloseIncidentForm(Model model) {
		String formAction ="close/validate";
		boolean isCommentCreateForm = true;
		boolean isClosureForm = true;
		boolean isClosureDetailForm = false;
		IncidentCommentsCreatePostDTO incidentCommentCreateDTO = null;
		
		return assigningServices.addProcessIncidentFormAttributes(incidentCommentCreateDTO, incidentsClosePostDTO(), model, FormNames.INCIDENTS_CLOSE, ButtonLabels.CONFIRM, formAction, isCommentCreateForm, isClosureForm, isClosureDetailForm);
	}
	
	/**
     * Handles HTTP POST requests to /incidents/close/validate.
     * Validates the incident closure form submitted by the user.
     * Uses the OnCloseIncident validation group to enforce specific rules,
     * checks for binding errors, and either proceeds to the confirmation step
     * or redirects back to the form with validation feedback.
     *
     * Responsibilities:
     * - Perform server-side validation of incident comments and resolution input
     * - Handle validation failures by displaying error messages
     * - On success, prepare confirmation view or redirect with session updates
     * - Use RedirectAttributes to pass user-facing messages across redirects
     *
     * URL Mapping:
     * POST /incidents/close/validate
     *
     * @param incidentsClosePostDTO DTO holding user-submitted incident closure data
     * @param bindingResult container for validation and binding errors
     * @param redirectAttributes used for passing feedback messages across redirects
     * @return the view name or redirect route based on validation outcome
     */
	@PostMapping("close/validate")
	public String validateCloseIncidentFormPost(@Validated(OnCloseIncident.class) @ModelAttribute("incidentCommentDTO") IncidentsClosePostDTO incidentsClosePostDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String failRedirect = "incidents/close";
		String sucessRedirect = failRedirect + "/find";
		IncidentCommentsCreatePostDTO incidentCommentCreateDTO = null;
		
		return incidentsVerifyingServices.verifyProcessIncidentFormPost(incidentCommentCreateDTO, incidentsClosePostDTO, failRedirect, sucessRedirect, bindingResult, redirectAttributes);
	}
	
	
	 /**
     * Handles HTTP GET requests to /incidents/close/find.
     * Renders the search form view that allows users to locate an incident
     * they intend to close. This method accesses the session-scoped
     * "incidentCommentDTO" to preserve input across the closure workflow.
     *
     * It's often used in multi-step forms where incident identification,
     * commentary, and confirmation are handled in separate phases.
     *
     * URL Mapping:
     * GET /incidents/close/find
     *
     * @param incidentsClosePostDTO the session-bound DTO containing user comments and metadata
     * @param model the Spring Model object used to pass data to the search form view
     * @return the name of the view template that displays the incident search interface
     */
	@GetMapping("close/find")
	public String renderSearchIncidentToCloseForm(@SessionAttribute("incidentCommentDTO") IncidentsClosePostDTO incidentsClosePostDTO, Model model) {
		String formName = FormNames.INCIDENTS_CLOSE.getDisplayName() + " - dohledání pojistné událostí";
		String formAction = "find/validate";
		boolean isSearchForm = true;
		boolean isCreateForm = true;
		
		return assigningServices.addFindIncidentsAttributes(model, formName, ButtonLabels.FIND, isSearchForm, isCreateForm, formAction);
	}	
	
    /**
     * Handles HTTP POST requests to /incidents/close/find/validate.
     * Validates the incident search form input and proceeds to locate matching
     * incidents based on the criteria provided by the user. Utilizes session-bound
     * "incidentCommentDTO" to preserve continuity across the closure process.
     *
     * Responsibilities:
     * - Interpret search input from IncidentsFindPostDTO
     * - Invoke service layer to retrieve matching incidents
     * - On success, add results to the model or session for display
     * - On failure, provide validation feedback via flash messages
     *
     * URL Mapping:
     * POST /incidents/close/find/validate
     *
     * @param incidentsClosePostDTO the session-bound DTO for ongoing incident closure
     * @param incidentsFindPostDTO DTO holding search criteria submitted by the user
     * @param model Spring Model for passing search results or error flags
     * @param redirectAttributes used for flash messages in case of redirect or failure
     * @return the name of the view showing results or redirect route on validation error
     */
	@PostMapping("close/find/validate")
	public String validateSearchIncidentToCloseFormPost(@SessionAttribute("incidentCommentDTO") IncidentsClosePostDTO incidentsClosePostDTO,
														@ModelAttribute("formDTO") IncidentsFindPostDTO incidentsFindPostDTO, Model model, RedirectAttributes redirectAttributes) {
		String successRedirect = "redirect:/incidents/close/find/select";
		String failRedirect = "incidents/close/find";
		
		return incidentsVerifyingServices.verifyFindIncidentsFormPost(model, incidentsFindPostDTO, successRedirect, failRedirect, redirectAttributes);
	}
	
    /**
     * Handles HTTP GET requests to /incidents/close/find/select.
     * Renders the view that allows the user to select an incident to close
     * from a previously filtered or matched list. It continues the closure flow
     * by presenting relevant incident options stored in session or model state.
     *
     * This method typically supports multi-step incident workflows, leveraging
     * session-persisted "incidentCommentDTO" to maintain continuity and context.
     *
     * URL Mapping:
     * GET /incidents/close/find/select
     *
     * @param incidentsClosePostDTO the model-bound DTO carrying ongoing incident closure data
     * @param model Spring Model to pass incident selection options to the view layer
     * @return the name of the view template that displays selectable incident entries
     */
	@GetMapping("close/find/select") 
	public String renderSelectIncidentToClosetList(@ModelAttribute("incidentCommentDTO") IncidentsClosePostDTO incidentsClosePostDTO, Model model) {
		String referenceLink = "selected-";
		String returnedPage = "pages/incidents/list";
		
		return assigningServices.addSelectIncidentListAttributes(model, referenceLink, returnedPage);
	}
	
	/**
     * Handles HTTP GET requests to /incidents/close/find/selected-{incidentId}.
     * Renders the confirmation form for closing a specific incident identified by its ID.
     * This method displays incident details along with user-provided closure comments
     * (persisted in "incidentCommentDTO") for final review before submission.
     *
     * Responsibilities:
     * - Load incident data based on the provided incidentId
     * - Merge it with session-scoped closure metadata (e.g., comments)
     * - Populate the model for review, allowing users to confirm closure intent
     *
     * URL Mapping:
     * GET /incidents/close/find/selected-{incidentId}
     *
     * @param incidentId the unique identifier of the incident to be closed
     * @param incidentsClosePostDTO the session-bound DTO containing user-entered comments and metadata
     * @param model Spring Model used to pass incident and comment data to the confirmation view
     * @return the name of the view template that displays the closure confirmation form
     */
	@GetMapping("close/find/selected-{incidentId}")
	public String renderConfirmIncidentClosureForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentCommentDTO") IncidentsClosePostDTO incidentsClosePostDTO, Model model) {
		IncidentCommentsCreatePostDTO incidentCommentsCreateDTO = null;
		String formName = FormNames.INCIDENTS_CLOSE.getDisplayName() + " - potvrzení kroku zpracování";
		boolean isSearchForm = false;
		boolean isCreateForm = false;
		boolean isCommentCreateForm = false;
		boolean isConfirmationForm = true;
		boolean isClosureForm = true;
		boolean isClosureDetail = true;
		
		return assigningServices.addConfirmIncidentCommentFormAttributes(incidentId, incidentCommentsCreateDTO, incidentsClosePostDTO, model, formName,
				isSearchForm, isCreateForm, isCommentCreateForm, isConfirmationForm, isClosureForm, isClosureDetail);
	}
	
	/**
     * Handles HTTP POST requests to /incidents/close/find/selected-{incidentId}/confirmed.
     * Finalizes the incident closure process by persisting the user's comments and resolution input
     * associated with the given incident ID. This marks the end of the multi-step workflow
     * and may trigger additional logic such as audit logging, notification dispatch, or
     * status updates within the incident tracking system.
     *
     * Responsibilities:
     * - Retrieve and confirm incident ID association
     * - Apply closure comments from the session-scoped incidentCommentDTO
     * - Call service layer to perform the incident finalization
     * - Clear session attributes to conclude the workflow
     *
     * URL Mapping:
     * POST /incidents/close/find/selected-{incidentId}/confirmed
     *
     * @param incidentId the unique identifier of the incident to be closed
     * @param incidentsClosePostDTO DTO containing final comments and metadata for closure
     * @param sessionStatus used to clear session-bound attributes post-operation
     * @return the redirect route or final view name confirming successful incident closure
     */
	@PostMapping("close/find/selected-{incidentId}/confirmed")
	public String handleConfirmIncidentCloseForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentCommentDTO") IncidentsClosePostDTO incidentsClosePostDTO, SessionStatus sessionStatus) {
		return procesingServices.processConfirmIncidentCloseForm(incidentId, incidentsClosePostDTO, sessionStatus);
	}
	
}