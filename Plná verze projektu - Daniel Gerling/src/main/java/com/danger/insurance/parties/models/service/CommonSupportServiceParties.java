package com.danger.insurance.parties.models.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesFoundSendDTO;

@Service
public class CommonSupportServiceParties {
	
	@Autowired
	private PartiesServiceImplementation partiesService;
	
	public String addFindPartyAttributes(String formAction, FormNames formName, Model model) {
		model.addAttribute("formDTO", new PartiesDetailsDTO());
		model.addAttribute("formAction", formAction);
		model.addAttribute("formName", formName.getDisplayName());
		model.addAttribute("buttonLabel", ButtonLabels.FIND.getDisplayName());
		
		return "pages/parties/find";
	}
	
	public String validateFindPartyFormPost(PartiesDetailsDTO dto, PartyStatus partyStatus, String failRedirectLink, String successRedirect, RedirectAttributes redirectAttributes) {
		
		//
		if(!validatePartySearchFormPost(dto)) {		// Or should the validation check fail
			redirectAttributes.addFlashAttribute("error", FlashMessages.INSUFFICIENT_INPUT.getDisplayName());
			
			return "redirect:/" + failRedirectLink;											// Reload the form page if the request is invalid
		}
	
		List<PartiesEntity> foundParties = partiesService.findUserId(dto, partyStatus);				// Find all insured matching the search criteria from the submitted DTO
		
		//
		if(foundParties.isEmpty()) {
			return "redirect:/parties/not-found";												// Redirect to "not found" page if no matches occur
		}
		
		attachFoundPartiesToRedirect(foundParties, redirectAttributes);							// Attach found Insured parties to the redirect attributes for display
		
		return "redirect:/" + successRedirect;
	}
	
	/**
	 * Attaches a list of found parties to the redirect using a flash attribute.
	 * This enables the target page (after redirect) to access and display the results.
	 *
	 * @param foundParties retrieved list containing found parties.
	 * @param redirectAttributes the redirect context used to carry temporary attributes across the redirect.
	 */
	private void attachFoundPartiesToRedirect(List<PartiesEntity> foundParties, RedirectAttributes redirectAttributes) {
	    PartiesFoundSendDTO sendDto = new PartiesFoundSendDTO();								// Create a DTO to carry search results
	    sendDto.setFoundParties(foundParties);													// Assign the found parties to the created DTO
	    redirectAttributes.addFlashAttribute("findingsDto", sendDto);							// Pass the populated DTO as attribute for the redirected page to display found parties
	}	
	
	
	/**
     * Validates the submitted search criteria to ensure the request is valid.
     *
     * @param dto a DTO containing the search criteria; includes personal details such as name, surname, birth date, birth number, email, street, and phone number.
     * @return {@code true} if the submitted criteria meet the minimum requirements for a valid search; {@code false} otherwise.
     */
	public boolean validatePartySearchFormPost(PartiesDetailsDTO partiesDetailsDTO) {
		
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
	
	public String processPartyValuesFormSubmit(BindingResult bindingResult, RedirectAttributes redirectAttributes, PartiesCreateDTO partiesCreateDTO, PartiesDetailsDTO partiesDetailsDTO, String successLink, String failRedirect) {
		
		if ((partiesCreateDTO != null) && (partiesDetailsDTO != null)) {
		    throw new IllegalArgumentException("Only one DTO should be provided at a time.");
		}
		
		if (!validateFormSubmit(bindingResult, redirectAttributes, partiesCreateDTO, partiesDetailsDTO)) {
			return "redirect:/" + failRedirect;
		}
		
		long partyId = 0;
		
		//
		if (partiesCreateDTO != null) {
			partyId = partiesService.create(partiesCreateDTO);
			redirectAttributes.addFlashAttribute("success", FlashMessages.PARTY_CREATED.getDisplayName());
		} else {
			partiesService.edit(partiesDetailsDTO);
			partyId = Long.valueOf(partiesDetailsDTO.getPartyId());
			redirectAttributes.addFlashAttribute("success", FlashMessages.PARTY_UPDATED.getDisplayName());
		}
	
		return "redirect:/" + successLink + partyId;
	}

	private boolean validateFormSubmit(BindingResult bindingResult, RedirectAttributes redirectAttributes, PartiesCreateDTO partiesCreateDTO, PartiesDetailsDTO partiesDetailsDTO) {
		
		//
		if (bindingResult.hasErrors()) {	
			assignFailedValidationAttributes(FlashMessages.INVALID_INPUT, redirectAttributes, partiesCreateDTO, partiesDetailsDTO, bindingResult);
			
			return false;
	    }
		
		//
		if (((partiesCreateDTO != null) && (partiesCreateDTO.getBirthDay().isAfter(LocalDate.now()))) || ((partiesDetailsDTO != null) && (partiesDetailsDTO.getBirthDay().isAfter(LocalDate.now())))) {
			assignFailedValidationAttributes(FlashMessages.FUTURE_BIRTHDAY, redirectAttributes, partiesCreateDTO, partiesDetailsDTO, bindingResult);
			
			return false;
		}
		
		return true;
	}

	private void assignCorrespondingRedirectDTO(RedirectAttributes redirectAttributes, PartiesCreateDTO partiesCreateDTO, PartiesDetailsDTO partiesDetailsDTO) {
		
		//
		if (partiesCreateDTO != null) {
			redirectAttributes.addFlashAttribute("formDTO", partiesCreateDTO);
		} else {
			redirectAttributes.addFlashAttribute("formDTO", partiesDetailsDTO);
		}
		
	}
	
	private void assignFailedValidationAttributes(FlashMessages flashMessage, RedirectAttributes redirectAttributes, PartiesCreateDTO partiesCreateDTO, PartiesDetailsDTO partiesDetailsDTO, BindingResult bindingResult) {
		assignCorrespondingRedirectDTO(redirectAttributes, partiesCreateDTO, partiesDetailsDTO);
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.formDTO", bindingResult);
		redirectAttributes.addFlashAttribute("error", flashMessage.getDisplayName());
	}
}