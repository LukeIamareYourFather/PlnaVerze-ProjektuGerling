package com.danger.insurance.incidents.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.incidents.data.repositories.IncidentCommentsRepository;
import com.danger.insurance.incidents.models.service.IncidentsServiceImplementation;

@Controller
@RequestMapping("incidents")
public class DisplayIncidentsController {
	
	@Autowired
	private IncidentsServiceImplementation incidentsService;
	
	@Autowired
	private IncidentCommentsRepository incidentCommentsRepository;

	@GetMapping
	public String renderIndex() {
		return "pages/incidents/index";
	}
	
	@GetMapping("{incidentId}")
	public String renderIncidentDetails(@PathVariable("incidentId") long incidentId, Model model) {
		model.addAttribute("formDTO", incidentsService.getDetailsById(incidentId));
		model.addAttribute("isCreateForm", false);
		model.addAttribute("isSearchForm", false);
		model.addAttribute("formName", "Přehled pojistné události");
		model.addAttribute("incidentCommentsList", incidentCommentsRepository.findByIncidentsEntity_IncidentId(incidentId).reversed());
		
		return "pages/incidents/detail";
	}
	
}