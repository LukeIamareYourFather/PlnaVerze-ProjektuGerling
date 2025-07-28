package com.danger.insurance.incidents.models.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.incidents.data.entities.IncidentsEntity;
import com.danger.insurance.incidents.models.dto.get.IncidentDetailsGetDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentCommentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsClosePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;

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
	
	public String addProcessIncidentFormAttributes(IncidentCommentsCreatePostDTO incidentCommentCreateDTO, IncidentsClosePostDTO incidentCloseDTO, Model model, FormNames formName, ButtonLabels buttonLabel, String formAction, Boolean isCommentCreateForm, Boolean isClosureForm, Boolean isClosureDetail) {
		model.addAttribute("formName", formName.getDisplayName());
		model.addAttribute("buttonLabel", buttonLabel.getDisplayName());
		model.addAttribute("formAction", formAction);
		model.addAttribute("isCommentCreateForm", isCommentCreateForm);
		model.addAttribute("isClosureForm", isClosureForm);
		model.addAttribute("isClosureDetail", isClosureDetail);
				
		if (incidentCommentCreateDTO != null) {
			
			if (!model.containsAttribute("incidentCommentDTO")) {
				model.addAttribute("incidentCommentDTO", incidentCommentCreateDTO);
			}
			
		} else {
			
			if (!model.containsAttribute("incidentCommentDTO")) {
				model.addAttribute("incidentCommentDTO", incidentCloseDTO);
			}
		}
		
		return "pages/incidents/process";
	}
	
	public String validateFindIncidentsFormPost(Model model, IncidentsFindPostDTO incidentsFindPostDTO, String successRedirectLink, String FailRedirectLink, RedirectAttributes redirectAttributes) {
		
		//
		if (!isSearchRequestValid(incidentsFindPostDTO)) {
			redirectAttributes.addFlashAttribute("error", FlashMessages.INSUFFICIENT_INPUT.getDisplayName());
			
			return "redirect:/" + FailRedirectLink;
	    }
		
		List<IncidentsEntity> foundIncidents = incidentsService.findIncidentId(incidentsFindPostDTO);
		redirectAttributes.addFlashAttribute("incidentsList", foundIncidents);
		
		return successRedirectLink;
	}
	
	public String validateProcessIncidentFormPost(IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, IncidentsClosePostDTO incidentsClosePostDTO, String failRedirect, String successRedirect, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		//
		if (bindingResult.hasErrors()) {
			addIncidentProcessValidationFailAttributes(incidentCommentsCreatePostDTO, incidentsClosePostDTO, redirectAttributes, bindingResult);
			
			return "redirect:/" + failRedirect;
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
	
	public String validateAccidentFormPost(IncidentsCreatePostDTO incidentCreateDTO, IncidentDetailsGetDTO incidentDetailsDTO, String failRedirect, String successRedirect, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			addIncidentValidationFailAttributes(incidentCreateDTO, incidentDetailsDTO, FlashMessages.INVALID_INPUT, bindingResult, redirectAttributes);
			
			return "redirect:/" + failRedirect;
	    }
		
		LocalDate todaysDate = LocalDate.now();
		
		if ((incidentCreateDTO != null) && (incidentCreateDTO.getAccidentDate().isAfter(todaysDate) || incidentCreateDTO.getReportDate().isAfter(todaysDate)) || 
		    (incidentDetailsDTO != null) && (incidentDetailsDTO.getAccidentDate().isAfter(todaysDate) || incidentDetailsDTO.getReportDate().isAfter(todaysDate))) {
			addIncidentValidationFailAttributes(incidentCreateDTO, incidentDetailsDTO, FlashMessages.FUTURE_DATES, bindingResult, redirectAttributes);
			
			return "redirect:/" + failRedirect;
		}
		
		addCorrectIncidentValidationDTO(incidentCreateDTO, incidentDetailsDTO, redirectAttributes);
		
		return "redirect:/" + successRedirect;
	}
	
	private void addIncidentProcessValidationFailAttributes(IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, IncidentsClosePostDTO incidentsClosePostDTO, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
		redirectAttributes.addFlashAttribute("error", FlashMessages.INVALID_INPUT.getDisplayName());
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.incidentCommentDTO", bindingResult);
		addCorrectIncidentProcessValidationDTO(incidentCommentsCreatePostDTO, incidentsClosePostDTO, redirectAttributes);
	}
	
	private void addCorrectIncidentProcessValidationDTO(IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, IncidentsClosePostDTO incidentsClosePostDTO, RedirectAttributes redirectAttributes) {
		
		if (incidentCommentsCreatePostDTO != null) {
			redirectAttributes.addFlashAttribute("incidentCommentDTO", incidentCommentsCreatePostDTO);
		} else {
			redirectAttributes.addFlashAttribute("incidentCommentDTO", incidentsClosePostDTO);
		}
		
	}
	
	private void addIncidentValidationFailAttributes(IncidentsCreatePostDTO incidentCreateDTO, IncidentDetailsGetDTO incidentDetailsDTO, FlashMessages flashMessage, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("error", flashMessage.getDisplayName());
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.formDTO", bindingResult);
		addCorrectIncidentValidationDTO(incidentCreateDTO, incidentDetailsDTO, redirectAttributes);
	}
	
	private void addCorrectIncidentValidationDTO(IncidentsCreatePostDTO incidentCreateDTO, IncidentDetailsGetDTO incidentDetailsDTO, RedirectAttributes redirectAttributes) {
		
		if (incidentCreateDTO != null) {
			redirectAttributes.addFlashAttribute("formDTO", incidentCreateDTO);
		} else {
			redirectAttributes.addFlashAttribute("formDTO", incidentDetailsDTO);
		}
		
	}
	
	private final boolean isSearchRequestValid(IncidentsFindPostDTO dto) {
    	
    	// Should the birth number be provided
    	if(!dto.getBirthNumber().equals("") || !dto.getCaseNumber().equals("")) {
    		return true;										// Return evaluation as passed
    	} else {		// Or should the birth number be missing
			int checksPassed = 0;								// Initialize counter of passed checks
    		
			// Should the name be provided...
			if (!dto.getTitle().equals("")) {
				checksPassed++;									// Increase the number of passed checks...
			}
    		
			if (dto.getIncidentType() != null) {
				checksPassed++;							
			}
    		
			if (dto.getIncidentSubject() != null) {
				checksPassed++;					
			}
    		
			if (dto.getCurrentStatus() != null) {
				checksPassed++;
			}
    		
			if (dto.getAccidentDate() != null) {
    			checksPassed++;
    		}
			
			// Should the desired amount of checks be passed
			if (checksPassed >= 3) {							
				return true;									// Return evaluation as passed
			}
			
		}
    	
    	return false;											// Since no requirement was met, return evaluation as failed
    }
	
}