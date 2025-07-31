package com.danger.insurance.incidents.models.service.support;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.RemoveIncidentReasonsDTO;
import com.danger.insurance.incidents.data.entities.IncidentsEntity;
import com.danger.insurance.incidents.models.dto.get.IncidentDetailsGetDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentCommentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsClosePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.incidents.models.service.IncidentsServiceImplementation;
import com.danger.insurance.infopages.data.enums.FlashMessages;

@Service
public class IncidentsVerifyingServices {

	@Autowired
	private IncidentsServiceImplementation incidentsService;
	
	@Autowired
	private IncidentsSupportServices supportServices;
	
	public String verifyFindIncidentsFormPost(Model model, IncidentsFindPostDTO incidentsFindPostDTO, String successRedirectLink, String FailRedirectLink, RedirectAttributes redirectAttributes) {
		
		//
		if (!supportServices.isSearchRequestValid(incidentsFindPostDTO)) {
			redirectAttributes.addFlashAttribute("error", FlashMessages.INSUFFICIENT_INPUT.getDisplayName());
			
			return "redirect:/" + FailRedirectLink;
	    }
		
		List<IncidentsEntity> foundIncidents = incidentsService.findIncidentId(incidentsFindPostDTO); 	// no tak kurde, mappovat na DTO! Ne?
		redirectAttributes.addFlashAttribute("incidentsList", foundIncidents);
		
		return successRedirectLink;
	}
	
	public String verifyProcessIncidentFormPost(IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, IncidentsClosePostDTO incidentsClosePostDTO, String failRedirect, String successRedirect, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		//
		if (bindingResult.hasErrors()) {
			supportServices.addIncidentProcessValidationFailAttributes(incidentCommentsCreatePostDTO, incidentsClosePostDTO, redirectAttributes, bindingResult);
			
			return "redirect:/" + failRedirect;
	    }
	
		return "redirect:/" + successRedirect;
	}
	
	
	public String verifyAccidentFormPost(IncidentsCreatePostDTO incidentCreateDTO, IncidentDetailsGetDTO incidentDetailsDTO, String failRedirect, String successRedirect, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			supportServices.addIncidentValidationFailAttributes(incidentCreateDTO, incidentDetailsDTO, FlashMessages.INVALID_INPUT, bindingResult, redirectAttributes);
			
			return "redirect:/" + failRedirect;
	    }
		
		LocalDate todaysDate = LocalDate.now();
		
		if ((incidentCreateDTO != null) && (incidentCreateDTO.getAccidentDate().isAfter(todaysDate) || incidentCreateDTO.getReportDate().isAfter(todaysDate)) || 
		    (incidentDetailsDTO != null) && (incidentDetailsDTO.getAccidentDate().isAfter(todaysDate) || incidentDetailsDTO.getReportDate().isAfter(todaysDate))) {
			supportServices.addIncidentValidationFailAttributes(incidentCreateDTO, incidentDetailsDTO, FlashMessages.FUTURE_DATES, bindingResult, redirectAttributes);
			
			return "redirect:/" + failRedirect;
		}
		
		supportServices.addCorrectIncidentValidationDTO(incidentCreateDTO, incidentDetailsDTO, redirectAttributes);
		
		return "redirect:/" + successRedirect;
	}
	
	public String verifyIncidentToRemoveFormPost(RemoveIncidentReasonsDTO removeIncidentReasonsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("incidentRemovalReasonsDTO", removeIncidentReasonsDTO);
			redirectAttributes.addFlashAttribute("error", FlashMessages.INVALID_INPUT.getDisplayName());
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.incidentRemovalReasonsDTO", bindingResult);
			
			return "redirect:/incidents/delete";
		}
		
		return "redirect:/incidents/delete/find";
	}
	
}