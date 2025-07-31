package com.danger.insurance.incidents.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.incidents.models.dto.get.IncidentDetailsGetDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsCreatePostDTO;
import com.danger.insurance.incidents.models.service.support.IncidentsAssigningServices;
import com.danger.insurance.incidents.models.service.support.IncidentsProcesingServices;
import com.danger.insurance.incidents.models.service.support.IncidentsVerifyingServices;
import com.danger.insurance.validation.groups.OnCreateIncidentAsEmployee;

/**
 * Controller responsible for creating new incidents.
 * This class is mapped to "/incidents" and acts as the entry point for workflows
 * related to initiating and submitting incident reports. It could be used in conjunction
 * with other controllers that manage updates, resolution, or tracking.
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("incidents")
public class CreateIncidentsController {
		
	@Autowired
	private IncidentsAssigningServices assigningServices;
	
	@Autowired
	private IncidentsProcesingServices procesingServices;
	
	@Autowired
	private IncidentsVerifyingServices verifyingServices;
	
	/**
	 * Renders the form view for creating a new incident.
	 * This method is triggered via GET request to "/create" and is responsible
	 * for preparing the model with any default attributes needed to initialize
	 * the incident creation form. Useful for setting dropdown values, priorities, or categories.
	 *
	 * @param model Spring’s Model object for passing attributes to the view
	 * @return the name of the view template for incident creation
	 */
	@GetMapping("create")
	public String renderCreateIncidentForm(Model model) {
		return assigningServices.addCreateIncidentFromAttributes(model);
	}
	
	/**
	 * Validates the submitted incident creation form.
	 * This POST handler at "/create/validate" applies validation rules defined under 
	 * the OnCreateIncidentAsEmployee group, allowing employee-specific field checks.
	 * On errors, it redirects back to the form with validation feedback; on success, 
	 * it carries forward the DTO for confirmation or persistence steps.
	 *
	 * @param createDTO the DTO populated from form input, validated in employee context
	 * @param bindingResult contains results of validation and binding process
	 * @param redirectAttributes used to pass error messages or form data during redirect
	 * @return redirect URL based on validation outcome
	 */
	@PostMapping("create/validate")
	public String validateCreateIncidentFormPost(@Validated(OnCreateIncidentAsEmployee.class) @ModelAttribute("formDTO") IncidentsCreatePostDTO createDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		IncidentDetailsGetDTO incidentDetailsDTO = null;
		String failRedirect = "incidents/create";
		String successRedirect = failRedirect + "/process";
		
		return verifyingServices.verifyAccidentFormPost(createDTO, incidentDetailsDTO, failRedirect, successRedirect, bindingResult, redirectAttributes);
	}
	
	/**
	 * Handles processing of the submitted incident creation form.
	 * This GET endpoint at "/create/process" finalizes the incident creation workflow
	 * by using the data from IncidentsCreatePostDTO and coordinating with services
	 * to persist the incident. It then sets success flags or messages and redirects
	 * to the appropriate summary or confirmation view.
	 *
	 * @param createDTO the DTO containing details of the newly created incident
	 * @param model the model for passing attributes to the view
	 * @param redirectAttributes used for flash messages across redirects
	 * @return redirect URL to confirmation or incident summary page
	 */
	@GetMapping("create/process")
	public String handleCreateIncidentFormPost(@ModelAttribute("formDTO") IncidentsCreatePostDTO createDTO, Model model, RedirectAttributes redirectAttributes) {
		return procesingServices.processCreateIncidentFormPost(createDTO, redirectAttributes);
	}
	
}