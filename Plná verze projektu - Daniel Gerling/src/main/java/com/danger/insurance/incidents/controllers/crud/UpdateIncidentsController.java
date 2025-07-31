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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.incidents.models.dto.get.IncidentDetailsGetDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsCreatePostDTO;
import com.danger.insurance.incidents.models.service.support.IncidentsAssigningServices;
import com.danger.insurance.incidents.models.service.support.IncidentsProcesingServices;
import com.danger.insurance.incidents.models.service.support.IncidentsVerifyingServices;
import com.danger.insurance.validation.groups.OnUpdateIncident;

/**
 * Controller responsible for handling updates to existing incidents.
 * Mapped under "/incidents", this class orchestrates workflows for modifying
 * incident data—such as changing status, updating descriptions, reassigning ownership,
 * or adjusting severity levels.
 */
@PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("incidents")
public class UpdateIncidentsController {
	
	@Autowired
	private IncidentsAssigningServices assigningServices;
	
	@Autowired
	private IncidentsProcesingServices procesingServices;
	
	@Autowired
	private IncidentsVerifyingServices verifyingServices;
	
	/**
	 * Renders the form view for editing an existing incident.
	 * Invoked via GET request to "/incidents/{incidentId}/edit", this method retrieves 
	 * the incident details using the provided ID and prepares the model for rendering 
	 * the edit form. Useful for pre-populating form fields with current incident data.
	 *
	 * Common enhancements include:
	 * - Role-based access checks before edit is allowed
	 * - Flagging incidents that can't be edited due to status or policy
	 * - Including metadata such as timestamps, history, or audit details
	 *
	 * @param incidentId the unique identifier of the incident to edit
	 * @param model the Spring MVC model for passing data to the view
	 * @return the name of the view template displaying the edit form
	 */
	@GetMapping("{incidentId}/edit")
	public String renderEditIncidentForm(@PathVariable("incidentId") long incidentId, Model model) {
		return assigningServices.addEditIncidentFormAttributes(incidentId, model);
	}
	
	/**
	 * Validates the incident edit form submission.
	 * This POST handler at "/incidents/{incidentId}/edit/validate" checks for input correctness 
	 * in the edited incident data. It applies form-level validations (e.g., required fields, 
	 * formatting constraints), preserves the user's input on failure, and prepares the session 
	 * for update execution on success.
	 *
	 * Common considerations:
	 * - Ensure edits comply with incident status (e.g., closed incidents shouldn’t be modifiable)
	 * - Role-based validation paths (admin vs general user)
	 * - Prepare for redirect or preview stage depending on your workflow setup
	 *
	 * @param incidentId the ID of the incident being edited
	 * @param updateDTO DTO holding updated incident details, validated
	 * @param bindingResult container for validation errors
	 * @param redirectAttributes carrier for messages or form state during redirect
	 * @return redirect URL based on validation result
	 */
	@PostMapping("{incidentId}/edit/validate")
	public String validateEditIncidentFormPost(@PathVariable("incidentId") long incidentId, @Validated(OnUpdateIncident.class) @ModelAttribute("formDTO") IncidentDetailsGetDTO incidentDetailsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		IncidentsCreatePostDTO incidentsCreateDTO = null;
		String failRedirect = "incidents/" + incidentId + "/edit";
		String successRedirect = failRedirect + "/confirmed";
		
		return verifyingServices.verifyAccidentFormPost(incidentsCreateDTO, incidentDetailsDTO, failRedirect, successRedirect, bindingResult, redirectAttributes) ;
	}
	
	/**
	 * Finalizes the update of an existing incident after user confirmation.
	 * Triggered via GET request to "/incidents/{incidentId}/edit/confirmed", this method
	 * applies the edits captured in IncidentDetailsGetDTO to the specified incident.
	 * It saves changes through the update service, logs the update for audit trail purposes,
	 * and sets success feedback for the user before redirecting to the incident details page.
	 *
	 * Workflow Highlights:
	 * - Confirms alignment between path ID and DTO
	 * - Applies updates to persisted data via service layer
	 * - Optionally logs change history or metadata (e.g. who made the edit)
	 * - Uses RedirectAttributes for UI success messaging
	 *
	 * @param incidentId unique ID of the incident being updated
	 * @param incidentDetailsGetDTO DTO containing final edited values
	 * @param redirectAttributes used to pass feedback across redirect
	 * @return redirect URL to updated incident detail view
	 */
	@GetMapping("{incidentId}/edit/confirmed")
	public String handleEditIncidentFormPost(@PathVariable("incidentId") long incidentId, @ModelAttribute("formDTO") IncidentDetailsGetDTO incidentDetailsGetDTO, RedirectAttributes redirectAttributes) {
		return procesingServices.processEditIncidentFormPost(incidentId, incidentDetailsGetDTO, redirectAttributes);
	}
	
}