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
import com.danger.insurance.validation.groups.OnProcessIncident;

/**
 * Controller class responsible for handling the final processing of incidents.
 * Uses @SessionAttributes to maintain the "incidentCommentDTO" across multiple requests,
 * allowing for continuity in workflows that involve incident resolution, confirmation,
 * or comment submission.
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("incidents")
@SessionAttributes("incidentCommentDTO")
public class ProcessIncidentsController {
		
	@Autowired
	private IncidentsAssigningServices assigningServices;
	
	@Autowired
	private IncidentsProcesingServices procesingServices;
	
	@Autowired
	private IncidentsVerifyingServices verifyingServices;
	
	/**
	 * Supplies a fresh IncidentCommentsCreatePostDTO object to the model
	 * under the key "incidentCommentDTO". This enables form binding for 
	 * comment submission workflows and ensures that controllers and views
	 * downstream have consistent access to this DTO.
	 *
	 * Invoked automatically by Spring MVC prior to relevant request mappings.
	 */
	@ModelAttribute("incidentCommentDTO")
	public IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO() {
	    return new IncidentCommentsCreatePostDTO(); 											// Only called once, when session starts
	}

	/**
	 * Renders the form view for processing an incident.
	 * This method is triggered via a GET request to "/process" and is
	 * responsible for presenting the incident handling interface to the user.
	 *
	 * The provided Model can be enriched with necessary attributes to support
	 * form rendering, such as user context, default form data, or workflow hints.
	 *
	 * @param model the model object used to populate the view with required data
	 * @return the name of the view to be rendered for incident processing
	 */
	@GetMapping("process")
	public String renderProcessIncidentForm(Model model) {
		String formAction ="process/validate";
		boolean isCommentCreateForm = true;
		boolean isClosureForm = false;
		boolean isClosureDetailForm = false;
		IncidentsClosePostDTO incidentCloseDTO = null;
		
		return assigningServices.addProcessIncidentFormAttributes(incidentCommentsCreatePostDTO(), incidentCloseDTO, model, FormNames.INCIDENTS_PROCESS,
				ButtonLabels.CONFIRM, formAction, isCommentCreateForm, isClosureForm, isClosureDetailForm);
	}

	/**
	 * Validates the submitted incident processing form.
	 * This method handles POST requests to "/process/validate" and applies
	 * conditional validation rules based on the OnProcessIncident group.
	 * If validation errors are present, the user is redirected back to the form view
	 * with relevant error messages. Otherwise, the incidentCommentDTO is stored
	 * for subsequent processing steps.
	 *
	 * @param incidentCommentDTO the DTO populated from form submission, validated for process context
	 * @param bindingResult holds the results of validation and binding
	 * @param redirectAttributes used to pass messages or data across a redirect
	 * @return the redirect URL depending on validation outcome
	 */
	@PostMapping("process/validate")
	public String validateProcessIncidentFormPost(@Validated(OnProcessIncident.class) @ModelAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String failRedirect = "incidents/process";
		String sucessRedirect = failRedirect + "/find";
		IncidentsClosePostDTO incidentCloseDTO = null;
		
		return verifyingServices.verifyProcessIncidentFormPost(incidentCommentsCreatePostDTO, incidentCloseDTO, failRedirect,  sucessRedirect,
				bindingResult, redirectAttributes);
	}
	
	/**
	 * Displays the interface for searching and selecting incidents to comment on.
	 * This GET handler is mapped to "/process/find" and relies on a session-persisted
	 * IncidentCommentsCreatePostDTO to carry forward user context or partial form data.
	 * The method prepares the view by populating the model with required attributes,
	 * such as the existing comment object, user hints, or filtering criteria.
	 *
	 * @param incidentCommentDTO session-scoped DTO containing any prior comment input
	 * @param model the model used to render the search form view
	 * @return the name of the view template that presents the search form
	 */
	@GetMapping("process/find")
	public String renderSearchIncidentToCommentForm(@SessionAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model) {
		String formName = FormNames.INCIDENTS_PROCESS.getDisplayName() + " - dohledání pojistné událostí";
		String formAction = "find/validate";
		boolean isSearchForm = true;
		boolean isCreateForm = true;
		
		return assigningServices.addFindIncidentsAttributes(model, formName, ButtonLabels.FIND, isSearchForm, isCreateForm, formAction);
	}
	
	/**
	 * Validates the submitted search form for selecting incidents to comment on.
	 * Triggered by a POST request to "/process/find/validate", this method checks the search
	 * criteria provided in IncidentsFindPostDTO, updates the session-stored incidentCommentDTO 
	 * if applicable, and determines the next view flow.
	 *
	 * @param incidentCommentDTO the existing comment DTO persisted in session
	 * @param incidentsFindPostDTO DTO containing incident search criteria from form input
	 * @param model used to store attributes for rendering views or passing data forward
	 * @param redirectAttributes used to pass flash attributes during redirect scenarios
	 * @return view name or redirect URL based on form validation and selection logic
	 */
	@PostMapping("process/find/validate")
	public String validateSearchIncidentToCommentFormPost(@SessionAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO,
														  @ModelAttribute("formDTO") IncidentsFindPostDTO incidentsFindPostDTO, Model model, RedirectAttributes redirectAttributes) {
		String successRedirect = "redirect:/incidents/process/find/select";
		String failRedirect = "incidents/process/find";
		
		return verifyingServices.verifyFindIncidentsFormPost(model, incidentsFindPostDTO, successRedirect, failRedirect, redirectAttributes);
	}
	
	/**
	 * Renders the view that lists selectable incidents for commenting.
	 * This GET endpoint, mapped to "/process/find/select", uses the provided 
	 * IncidentCommentsCreatePostDTO from the model to retain user input context 
	 * (e.g., filters or partially filled comment data). The model is enriched with 
	 * incident list data which the user can choose from.
	 *
	 * @param incidentCommentDTO DTO containing any previously entered comment details
	 * @param model the model to pass incident list and form context to the view
	 * @return the name of the view template displaying incident selection options
	 */
	@GetMapping("process/find/select") 
	public String renderSelectIncidentToCommentList(@ModelAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model) {
		String referenceLink = "selected-";
		String returnedPage = "pages/incidents/list";
		
		return assigningServices.addSelectIncidentListAttributes(model, referenceLink, returnedPage);
	}
	
	/**
	 * Renders the confirmation form for commenting on a selected incident.
	 * Triggered by GET request to "/process/find/selected-{incidentId}", this method
	 * uses the path variable to fetch or verify the incident being commented on.
	 * It supplements the view with incident details and the existing comment DTO 
	 * (retrieved via model binding), allowing users to review and finalize their input.
	 *
	 * @param incidentId ID of the incident selected for commenting
	 * @param incidentCommentDTO DTO containing user's comment input
	 * @param model the model used to supply data to the view
	 * @return the name of the confirmation form view template
	 */
	@GetMapping("process/find/selected-{incidentId}")
	public String renderConfirmIncidentCommentForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model) {
		IncidentsClosePostDTO incidentCloseDTO = null;
		String formName = FormNames.INCIDENTS_PROCESS.getDisplayName() + " - potvrzení kroku zpracování";
		boolean isSearchForm = false;
		boolean isCreateForm = false;
		boolean isCommentCreateForm = false;
		boolean isConfirmationForm = true;
		boolean isClosureForm = false;
		boolean isClosureDetail = false;
		
		return assigningServices.addConfirmIncidentCommentFormAttributes(incidentId, incidentCommentsCreatePostDTO, incidentCloseDTO, model, formName,
				isSearchForm, isCreateForm, isCommentCreateForm, isConfirmationForm, isClosureForm, isClosureDetail);
	}
	
	/**
	 * Handles the final submission of a user's comment on a selected incident.
	 * This POST method, mapped to "/process/find/selected-{incidentId}/confirmed", receives the 
	 * finalized IncidentCommentsCreatePostDTO and performs actions such as saving the comment, 
	 * clearing the session, and redirecting to a confirmation or summary view.
	 *
	 * @param incidentId ID of the incident being commented on (extracted from path variable)
	 * @param incidentCommentsCreatePostDTO DTO containing finalized comment data
	 * @param sessionStatus used to mark the session complete and clear session-scoped attributes
	 * @param redirectAttributes used to pass messages or flags during redirection
	 * @return the redirect URL to the post-confirmation or result view
	 */
	@PostMapping("process/find/selected-{incidentId}/confirmed")
	public String handleConfirmIncidentCommentForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentCommentsCreateDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		return procesingServices.processConfirmIncidentCommentForm(incidentId, incidentCommentsCreatePostDTO, redirectAttributes, sessionStatus);
	}
	
}