package com.danger.insurance.incidents.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.incidents.models.service.support.IncidentsAssigningServices;
import com.danger.insurance.incidents.models.service.support.IncidentsVerifyingServices;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;

/**
 * Controller class responsible for handling incident search and discovery operations.
 * Mapped to the /incidents path, this class typically exposes endpoints for querying
 * incident records based on user-defined criteria such as date, category, status, or reporter.
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("incidents")
public class FindIncidentsController {
	
	@Autowired
	private IncidentsAssigningServices assigningController;
	
	@Autowired
	private IncidentsVerifyingServices verifyingServices;
	
	/**
     * Handles HTTP GET requests to /incidents/find.
     * Renders the search form for locating incidents based on criteria such as
     * date, status, category, or reporter information.
     *
     * This method typically prepares form-bound DTOs or filter presets,
     * adds them to the model, and delivers a structured interface to initiate
     * search queries. It's the entry point for incident discovery workflows.
     *
     * URL Mapping:
     * GET /incidents/find
     *
     * @param model the Spring Model used to pass search parameters and options to the view
     * @return the name of the view template that presents the incident search form
     */
	@GetMapping("find")
	public String renderSearchIncidentForm(Model model) {
		String formAction = "find/validate";
		boolean isSearchForm = true;
		boolean isCreateForm = true;
		
		return assigningController.addFindIncidentsAttributes(model, FormNames.INCIDENTS_FIND.getDisplayName(), ButtonLabels.FIND, isSearchForm, isCreateForm, formAction);
	}
	
	/**
     * Handles HTTP POST requests to /incidents/find/validate.
     * Processes the submitted search form input for locating relevant incidents.
     * Applies user-provided criteria encapsulated in IncidentsFindPostDTO and
     * retrieves matching incidents to be passed to the view.
     *
     * Typical flow includes:
     * - Interpreting filter fields like status, date range, type, or reporter
     * - Delegating the search logic to a service or repository layer
     * - Handling empty results or search errors gracefully
     * - Populating the model with results or passing messages via RedirectAttributes
     *
     * URL Mapping:
     * POST /incidents/find/validate
     *
     * @param incidentsFindPostDTO DTO carrying the user's incident search criteria
     * @param model Spring Model for passing results or search metadata to the view
     * @param redirectAttributes used to convey messages across redirects (e.g. no results found)
     * @return the view name for rendering results, or redirect route on failure
     */
	@PostMapping("find/validate")
	public String validateSearchIncidentForm(@ModelAttribute("formDTO") IncidentsFindPostDTO incidentsFindPostDTO, Model model, RedirectAttributes redirectAttributes) {
		String successRedirect = "redirect:/incidents/find/select";
		String failRedirect = "incidents/find";
		
		return verifyingServices.verifyFindIncidentsFormPost(model, incidentsFindPostDTO, successRedirect, failRedirect, redirectAttributes);
	}
	
	/**
     * Handles HTTP GET requests to /incidents/find/select.
     * Renders the view that displays a list of incidents from a prior search result,
     * allowing the user to browse and select one for further actions such as viewing details,
     * updating, or initiating a closure process.
     *
     * This method may assume that the model has already been populated with a list
     * of incidents via a previous search or filtering operation.
     *
     * Responsibilities:
     * - Present selectable incidents (e.g., in a table or list layout)
     * - Support user interaction for choosing a specific incident
     * - Maintain context for follow-up operations such as edit or close
     *
     * URL Mapping:
     * GET /incidents/find/select
     *
     * @param model the Spring Model object used to pass incident list and view data to the frontend
     * @return the name of the view template displaying the list of selectable incidents
     */
	@GetMapping("find/select")
	public String renderSelectIncidentList(Model model) {
		String referenceLink = "/incidents/";
		String returnedPage = "pages/incidents/list";
		
		return assigningController.addSelectIncidentListAttributes(model, referenceLink, returnedPage);
	}
	
}