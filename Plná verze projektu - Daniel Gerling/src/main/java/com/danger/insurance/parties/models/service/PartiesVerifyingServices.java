package com.danger.insurance.parties.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

@Service
public class PartiesVerifyingServices {
	
	@Autowired
	private PartiesServiceImplementation partiesService;

	@Autowired
	private PartiesSupportServices supportServices;
	
	public String verifyFindPartyFormPost(PartiesDetailsDTO dto, PartyStatus partyStatus, String failRedirectLink, String successRedirect, RedirectAttributes redirectAttributes) {
		
		//
		if(!verifyPartySearchFormPost(dto)) {		// Or should the validation check fail
			redirectAttributes.addFlashAttribute("error", FlashMessages.INSUFFICIENT_INPUT.getDisplayName());
			
			return "redirect:/" + failRedirectLink;											// Reload the form page if the request is invalid
		}
	
		List<PartiesEntity> foundParties = partiesService.findUserId(dto, partyStatus);				// Find all insured matching the search criteria from the submitted DTO
		
		//
		if(foundParties.isEmpty()) {
			return "redirect:/parties/not-found";												// Redirect to "not found" page if no matches occur
		}
		
		supportServices.attachFoundPartiesToRedirect(foundParties, redirectAttributes);							// Attach found Insured parties to the redirect attributes for display
		
		return "redirect:/" + successRedirect;
	}


	/**
	 * Validates the submitted search criteria to ensure the request is valid.
	 *
	 * @param dto a DTO containing the search criteria; includes personal details such as name, surname, birth date, birth number, email, street, and phone number.
	 * @return {@code true} if the submitted criteria meet the minimum requirements for a valid search; {@code false} otherwise.
	 */
	public boolean verifyPartySearchFormPost(PartiesDetailsDTO partiesDetailsDTO) {
	
		// Should the birth number be provided
		if(!partiesDetailsDTO.getBirthNumber().equals("")) {
			return true;										// Return evaluation as passed
		} else {		// Or should the birth number be missing
			int checksPassed = 0;								// Initialize counter of passed checks
			
			// Should the name be provided...
			if (!partiesDetailsDTO.getName().equals("")) {
				checksPassed++;									// Increase the number of passed checks...
			}
			
			if (!partiesDetailsDTO.getSurname().equals("")) {
				checksPassed++;							
			}
			
			if (!partiesDetailsDTO.getStreet().equals("")) {
				checksPassed++;					
			}
			
			if (!partiesDetailsDTO.getEmail().equals("")) {
				checksPassed++;
			}
			
			if (!partiesDetailsDTO.getPhoneNumber().equals("")) {
				checksPassed++;
			}
			
			if (partiesDetailsDTO.getBirthDay() != null) {
				checksPassed++;
			}
			
			// Should the desired amount of checks be passed
			if (checksPassed >= 3) {							
				return true;									// Return evaluation as passed
			}
			
		}
		
		return false;								
	}
	
}