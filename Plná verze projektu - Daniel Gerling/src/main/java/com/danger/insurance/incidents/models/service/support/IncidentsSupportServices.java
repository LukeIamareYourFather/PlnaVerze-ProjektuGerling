package com.danger.insurance.incidents.models.service.support;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.incidents.models.dto.get.IncidentDetailsGetDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentCommentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsClosePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.infopages.data.enums.FlashMessages;

@Service
public class IncidentsSupportServices {

	public void addIncidentProcessValidationFailAttributes(IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, IncidentsClosePostDTO incidentsClosePostDTO, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
		redirectAttributes.addFlashAttribute("error", FlashMessages.INVALID_INPUT.getDisplayName());
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.incidentCommentDTO", bindingResult);
		addCorrectIncidentProcessValidationDTO(incidentCommentsCreatePostDTO, incidentsClosePostDTO, redirectAttributes);
	}
	
	public void addCorrectIncidentProcessValidationDTO(IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, IncidentsClosePostDTO incidentsClosePostDTO, RedirectAttributes redirectAttributes) {
		
		if (incidentCommentsCreatePostDTO != null) {
			redirectAttributes.addFlashAttribute("incidentCommentDTO", incidentCommentsCreatePostDTO);
		} else {
			redirectAttributes.addFlashAttribute("incidentCommentDTO", incidentsClosePostDTO);
		}
		
	}
	
	public void addIncidentValidationFailAttributes(IncidentsCreatePostDTO incidentCreateDTO, IncidentDetailsGetDTO incidentDetailsDTO, FlashMessages flashMessage, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("error", flashMessage.getDisplayName());
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.formDTO", bindingResult);
		addCorrectIncidentValidationDTO(incidentCreateDTO, incidentDetailsDTO, redirectAttributes);
	}
	
	public void addCorrectIncidentValidationDTO(IncidentsCreatePostDTO incidentCreateDTO, IncidentDetailsGetDTO incidentDetailsDTO, RedirectAttributes redirectAttributes) {
		
		if (incidentCreateDTO != null) {
			redirectAttributes.addFlashAttribute("formDTO", incidentCreateDTO);
		} else {
			redirectAttributes.addFlashAttribute("formDTO", incidentDetailsDTO);
		}
		
	}
	
	public final boolean isSearchRequestValid(IncidentsFindPostDTO dto) {
    	
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