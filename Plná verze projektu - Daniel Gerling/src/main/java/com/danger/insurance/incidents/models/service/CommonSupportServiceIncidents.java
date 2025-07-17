package com.danger.insurance.incidents.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.incidents.data.entities.IncidentsEntity;
import com.danger.insurance.incidents.models.dto.post.IncidentCommentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsClosePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;

@Service
public class CommonSupportServiceIncidents {

	@Autowired
	private IncidentsServiceImplementation incidentsService;
	
	public String addFindIncidentsAttributes(Model model, String formName, String buttonLabel, Boolean isSearchForm, Boolean isCreateForm, String formAction) {
		model.addAttribute("formName", formName);
		model.addAttribute("formDTO", new IncidentsFindPostDTO());
		model.addAttribute("buttonLabel", buttonLabel);
		model.addAttribute("isSearchForm", isSearchForm);
		model.addAttribute("isCreateForm", isCreateForm);
		model.addAttribute("formAction", formAction);
		
		return "pages/incidents/find";
	}
	
	public String addProcessIncidentFormAttributes(Model model, String formName, String buttonLabel, String formAction, Boolean isCommentCreateForm, Boolean isClosureForm, Boolean isClosureDetail) {
		model.addAttribute("formName", formName);
		model.addAttribute("buttonLabel", buttonLabel);
		model.addAttribute("formAction", formAction);
		model.addAttribute("isCommentCreateForm", isCommentCreateForm);
		model.addAttribute("isClosureForm", isClosureForm);
		model.addAttribute("isClosureDetail", isClosureDetail);
		
		return "pages/incidents/process";
	}
	
	public String validateFindIncidentsFormPost(Model model, IncidentsFindPostDTO incidentsFindPostDTO, String successRedirectLink, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//
		if (bindingResult.hasErrors()) {
			model.addAttribute("formDTO", incidentsFindPostDTO);
			
			return "pages/incidents/find";
	    }
		
		List<IncidentsEntity> foundIncidents = incidentsService.findIncidentId(incidentsFindPostDTO);
		redirectAttributes.addFlashAttribute("incidentsList", foundIncidents);
		
		return successRedirectLink;
	}
	
	public String validateProcessIncidentFormPost(Model model, BindingResult bindingResult, IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, IncidentsClosePostDTO incidentsClosePostDTO, String successRedirect) {

		//
		if (bindingResult.hasErrors()) {
			
			//
			if (incidentCommentsCreatePostDTO != null) {
				model.addAttribute("incidentCommentDTO", incidentCommentsCreatePostDTO);
			} else {		//
				model.addAttribute("incidentCommentDTO", incidentsClosePostDTO);
			}
			
			return "pages/incidents/process";
	    }
		
		return "redirect:/" + successRedirect;
	}
	
	public String addSelectIncidentListAttributes(Model model, String referenceLink, String returnedPage) {
		model.addAttribute("referenceLink", referenceLink);
		
		return returnedPage;
	}
	
	public String addConfirmIncidentCommentFormAttributes(@PathVariable("incidentId") long incidentId, IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, IncidentsClosePostDTO incidentsClosePostDTO,
			Model model, String pageTitle, Boolean isSearchForm, Boolean isCreateForm, Boolean isCommentCreateForm, Boolean isConfirmationForm, Boolean isClosureForm, Boolean isClosureDetail) {
		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("formAction", "selected-" + incidentId + "/confirmed");
		model.addAttribute("buttonLabel", "Potvrdit");
		model.addAttribute("formDTO", incidentsService.getDetailsById(incidentId));
		model.addAttribute("isSearchForm", isSearchForm);
		model.addAttribute("isCreateForm", isCreateForm);
		model.addAttribute("isCommentCreateForm", isCommentCreateForm);
		model.addAttribute("isConfirmationForm", isConfirmationForm);
		model.addAttribute("isClosureForm", isClosureForm);
		model.addAttribute("isClosureDetail", isClosureDetail);
		
		//
		if (incidentCommentsCreatePostDTO != null) {
			model.addAttribute("incidentCommentDTO", incidentCommentsCreatePostDTO);			
		} else {
			model.addAttribute("incidentCommentDTO", incidentsClosePostDTO);
		}
		
		return "pages/incidents/confirm-comment";
	}
}