package com.danger.insurance.incidents.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.danger.insurance.incidents.models.service.support.IncidentsAssigningServices;
import com.danger.insurance.shared.enums.ActivePageTokens;
import com.danger.insurance.shared.services.CommonSupportServiceShared;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller responsible for displaying incidents.
 * Mapped to "/incidents", this class provides endpoints to retrieve and view
 * incident data across various contexts—such as lists, detail views, and filtered results.
 */
@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("incidents")
public class DisplayIncidentsController {
	
	@Autowired
	private CommonSupportServiceShared supportServiceShared;
	
	@Autowired
	private IncidentsAssigningServices assigningServices;
	
	/**
	 * Handles the default landing view for incident-related workflows.
	 * This GET endpoint responds to "/incidents" (via class-level @RequestMapping) 
	 * and serves as the index or dashboard for incident visibility.
	 *
	 * Functionality:
	 * - Clears any lingering session attributes to reset the workflow state
	 * - Optionally logs request metadata or user context for analytics/debugging
	 * - Prepares the model with introductory messages or system status
	 *
	 * @param httpRequest used to access request metadata or headers
	 * @param sessionStatus used to mark the session complete and reset scoped attributes
	 * @param model the model used to inject view-related attributes
	 * @return the name of the index view template
	 */
	@GetMapping
	public String renderIndex(HttpServletRequest httpRequest, SessionStatus sessionStatus, Model model) {
		supportServiceShared.removeAllSessionAttributes(ActivePageTokens.INCIDENTS, httpRequest, sessionStatus, model);
		
		return "pages/incidents/index";
	}
	
	/**
	 * Displays detailed information for a specific incident.
	 * Triggered by a GET request to "/incidents/{incidentId}", this method
	 * retrieves the incident by its unique identifier and populates the view
	 * with relevant details, such as status, description, timestamps, and
	 * associated comments or audit history.
	 *
	 * @param incidentId the unique ID of the incident to display
	 * @param model the model used to pass incident data to the view
	 * @return the name of the view template displaying incident details
	 */
	@GetMapping("{incidentId}")
	public String renderIncidentDetails(@PathVariable("incidentId") long incidentId, Model model) {
		return assigningServices.addIncidentDetailsAttributes(incidentId, model);
	}
	
}