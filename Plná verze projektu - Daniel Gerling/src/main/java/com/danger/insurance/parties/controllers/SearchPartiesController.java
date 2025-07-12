//  (•_•)
// <)   )╯ Clean
// /    \   Code
package com.danger.insurance.parties.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesFoundSendDTO;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;

@Controller
@RequestMapping("/parties")
public class SearchPartiesController {

	// Object initialization
	
	@Autowired
	private PartiesServiceImplementation partiesService;										// Handles logic related to parties
	
	// Start of code
	
	/**
	 * Renders the Policy Owner search tool.
	 * 
	 * @param dto a DTO to store the search criteria submitted by the user; includes personal details such as name, surname, birth date, birth number, email, street, or phone number.
	 * @return the name of the Thymeleaf template for the Policy Owner search page.
	 */
	@GetMapping("find/policy-owner")
	public String renderFindPolicyOwner(Model model) {
		addFindPartyAttributes("policy-owners", model);
		
		return "pages/parties/policy-owners/find";												// Redirect to the find Policy Owner page
	}
	
	/**
	 * Renders the Insured search tool.
	 * 
	 * @param dto a DTO to store the search criteria submitted by the user; includes personal details such as name, surname, birth date, birth number, email, street, or phone number.
	 * @return the name of the Thymeleaf template for the Insured search page.
	 */
	@GetMapping("find/insured")
	public String renderFindInsured(Model model) {
		addFindPartyAttributes("insured", model);
			
		return "pages/parties/insured/find";													// Redirect to the find Insured page
	}
	
	/**
	 * Handles the submission of the Policy Owner search form.
	 *
	 * @param dto a DTO to store the search criteria submitted by the user; includes personal details such as name, surname, birth date, birth number, email, street, or phone number.
	 * @param redirectAttributes used to pass temporary attributes (e.g., search results or error flags) between redirect pages.
	 * @return a redirect to the appropriate result page: either a list of matches, a not-found page, or the same page if the input is invalid.
	 */
	@PostMapping("find/policy-owners")
	public String handleSearchPolicyOwnerFormSubmit(PartiesDetailsDTO dto, RedirectAttributes redirectAttributes) {
		return validateSearchParty(dto, PartyStatus.POLICY_OWNER, redirectAttributes);
	}
	
	/**
	 * Handles the submission of the Insured search form.
	 * 
	 * @param dto a DTO to store the search criteria submitted by the user; includes personal details such as name, surname, birth date, birth number, email, street, or phone number.
	 * @param redirectAttributes used to pass temporary attributes (e.g., search results or error flags) between redirect pages.
	 * @return a redirect to the appropriate result page: either a list of matches, a not-found page, or the same page if the input is invalid.
	 */
	@PostMapping("find/insured")
	public String handleSearchInsuredFormSubmit(PartiesDetailsDTO dto, RedirectAttributes redirectAttributes) {
		return validateSearchParty(dto, PartyStatus.INSURED, redirectAttributes);
	}
	
	// Helper methods
	
	//
	private void addFindPartyAttributes(String partyToFind, Model model) {
		model.addAttribute("formDTO", new PartiesDetailsDTO());
		model.addAttribute("formAction", partyToFind);
		model.addAttribute("showInsuredStatusColumn", true);
	}
	
	private String validateSearchParty(PartiesDetailsDTO dto, PartyStatus partyStatus, RedirectAttributes redirectAttributes) {
		String redirectResult = "redirect:/parties/";
		List<PartiesEntity> foundParties = partiesService.findUserId(dto, partyStatus);				// Find all insured matching the search criteria from the submitted DTO

		if(foundParties.isEmpty()) {
			redirectResult += "not-found";												// Redirect to "not found" page if no matches occur
		} else if(!partiesService.isSearchRequestValid(dto)) {		// Or should the validation check fail
			redirectResult += "find/insured";											// Reload the form page if the request is invalid
		}
		
		attachFoundPartiesToRedirect(foundParties, redirectAttributes);							// Attach found Insured parties to the redirect attributes for display
		
		return redirectResult + "found/profiles";
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
	
}