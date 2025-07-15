package com.danger.insurance.incidents.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.incidents.data.entities.IncidentsEntity;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;

@Service
public class CommonSupportServiceIncidents {

	@Autowired
	private IncidentsServiceImplementation incidentsService;
	
	public String addFindIncidentsAttributes(Model model, String formName, String buttonLabel, Boolean isSearchForm, Boolean isCreateForm, String formAction, String returnedPage) {
		model.addAttribute("formName", formName);
		model.addAttribute("formDTO", new IncidentsFindPostDTO());
		model.addAttribute("buttonLabel", buttonLabel);
		model.addAttribute("isSearchForm", isSearchForm);
		model.addAttribute("isCreateForm", isCreateForm);
		model.addAttribute("formAction", formAction);
		
		return returnedPage;
	}
	
	public String validateFindIncidentsFormPost(Model model, IncidentsFindPostDTO incidentsFindPostDTO, String failReturnPage, String successRedirectLink, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//
		if (bindingResult.hasErrors()) {
			model.addAttribute("formDTO", incidentsFindPostDTO);
			
			return "pages/incidents/find";
	    }
		
		List<IncidentsEntity> foundIncidents = incidentsService.findIncidentId(incidentsFindPostDTO);
		redirectAttributes.addFlashAttribute("incidentsList", foundIncidents);
		
		return successRedirectLink;
	}
	
	public String addSelectIncidentListAttributes(Model model, String referenceLink, String returnedPage) {
		model.addAttribute("referenceLink", referenceLink);
		
		return returnedPage;
	}
	
}