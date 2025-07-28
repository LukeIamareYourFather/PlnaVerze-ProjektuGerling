package com.danger.insurance.incidents.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.danger.insurance.incidents.models.dto.IncidentsDTO;
import com.danger.insurance.incidents.models.dto.get.IncidentDetailsGetDTO;
import com.danger.insurance.incidents.models.dto.mappers.IncidentsMapper;
import com.danger.insurance.incidents.models.service.CommonSupportServiceIncidents;
import com.danger.insurance.incidents.models.service.IncidentsServiceImplementation;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.validation.groups.OnUpdateIncident;

@Controller
@RequestMapping("incidents")
public class UpdateIncidentsController {

	@Autowired
	private IncidentsServiceImplementation incidentsService;
	
	@Autowired
	private IncidentsMapper incidentsMapper;
	
	@Autowired
	private CommonSupportServiceIncidents supportServiceIncidents;
	
	@GetMapping("{incidentId}/edit")
	public String renderEditIncidentForm(@PathVariable("incidentId") long incidentId, Model model) {
		model.addAttribute("formName", FormNames.INCIDENTS_UPDATE.getDisplayName());
		model.addAttribute("buttonLabel", ButtonLabels.CHANGE.getDisplayName());
		model.addAttribute("formAction", "edit/validate");
		model.addAttribute("incidentId", incidentId);
		model.addAttribute("isCreateForm", true);
		model.addAttribute("isSearchForm", false);
		
		//
		if (!model.containsAttribute("formDTO")) {
			model.addAttribute("formDTO", incidentsService.getDetailsById(incidentId));		// I really should use incidentCreatePostDTO here, but i liked the extra challenge of having to juggle two different DTO in one support method in the proceeding validation step
		}
		
		return "pages/incidents/create";
	}
	
	@PostMapping("{incidentId}/edit/validate")
	public String validateEditIncidentFormPost(@PathVariable("incidentId") long incidentId, @Validated(OnUpdateIncident.class) @ModelAttribute("formDTO") IncidentDetailsGetDTO incidentDetailsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String failRedirect = "incidents/" + incidentId + "/edit";
		String successRedirect = failRedirect + "/confirmed";
		
		return supportServiceIncidents.validateAccidentFormPost(null, incidentDetailsDTO, failRedirect, successRedirect, bindingResult, redirectAttributes) ;
	}
	
	@GetMapping("{incidentId}/edit/confirmed")
	public String handleEditIncidentFormSubmit(@PathVariable("incidentId") long incidentId, @ModelAttribute("formDTO") IncidentDetailsGetDTO incidentDetailsGetDTO, RedirectAttributes redirectAttributes) {
		IncidentsDTO originalIncidentDTO = incidentsService.getIncidentById(incidentId);
		IncidentsDTO updatedIncidentDTO = incidentsMapper.mergeDetailsDTOToIncidentsDTO(new IncidentsDTO(), incidentDetailsGetDTO);
		updatedIncidentDTO.setIncidentId(incidentId);
		updatedIncidentDTO.setCurrentStatus(originalIncidentDTO.getCurrentStatus());
		updatedIncidentDTO.setTodaysDate(originalIncidentDTO.getTodaysDate());
		incidentsService.edit(updatedIncidentDTO);
		redirectAttributes.addFlashAttribute("success", FlashMessages.INCIDENT_UPDATED.getDisplayName());
		
		return "redirect:/incidents/" + incidentId;
	}
	
}