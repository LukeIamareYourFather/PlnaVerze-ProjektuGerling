package com.danger.insurance.incidents.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.incidents.models.dto.IncidentsDTO;
import com.danger.insurance.incidents.models.dto.get.IncidentDetailsGetDTO;
import com.danger.insurance.incidents.models.dto.mappers.IncidentsMapper;
import com.danger.insurance.incidents.models.service.IncidentsServiceImplementation;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;

@Controller
@RequestMapping("incidents")
public class UpdateIncidentsController {

	@Autowired
	private IncidentsServiceImplementation incidentsService;
	
	@Autowired
	private IncidentsMapper incidentsMapper;
	
	@GetMapping("{incidentId}/edit")
	public String renderEditIncidentForm(@PathVariable("incidentId") long incidentId, Model model) {
		model.addAttribute("formName", FormNames.INCIDENTS_UPDATE.getDisplayName());
		model.addAttribute("buttonLabel", ButtonLabels.CHANGE.getDisplayName());
		model.addAttribute("formDTO", incidentsService.getDetailsById(incidentId));
		model.addAttribute("formAction", "edit/confirmed");
		model.addAttribute("incidentId", incidentId);
		model.addAttribute("isCreateForm", true);
		model.addAttribute("isSearchForm", false);
		
		return "pages/incidents/create";
	}
	
	@PostMapping("{incidentId}/edit/confirmed")
	public String handleEditIncidentFormSubmit(@PathVariable("incidentId") long incidentId, @ModelAttribute("formDTO") IncidentDetailsGetDTO incidentDetailsGetDTO) {
		IncidentsDTO originalIncidentDTO = incidentsService.getIncidentById(incidentId);
		IncidentsDTO updatedIncidentDTO = incidentsMapper.mergeDetailsDTOToIncidentsDTO(new IncidentsDTO(), incidentDetailsGetDTO);
		updatedIncidentDTO.setIncidentId(incidentId);
		updatedIncidentDTO.setCurrentStatus(originalIncidentDTO.getCurrentStatus());
		updatedIncidentDTO.setTodaysDate(originalIncidentDTO.getTodaysDate());
		incidentsService.edit(updatedIncidentDTO);
		
		return "redirect:/incidents/" + incidentId;
	}
	
}