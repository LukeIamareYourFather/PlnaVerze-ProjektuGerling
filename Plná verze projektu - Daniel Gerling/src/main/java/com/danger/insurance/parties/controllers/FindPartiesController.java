//  (•_•)
// <)   )╯ Clean
// /    \   Code
package com.danger.insurance.parties.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.service.CommonSupportServiceParties;

@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("parties")
public class FindPartiesController {

	// Object initialization
	
	@Autowired
	private CommonSupportServiceParties commonSupportParties;
	// Start of code
	
	/**
	 * Renders the Policy Owner search tool.
	 * 
	 * @param dto a DTO to store the search criteria submitted by the user; includes personal details such as name, surname, birth date, birth number, email, street, or phone number.
	 * @return the name of the Thymeleaf template for the Policy Owner search page.
	 */
	@GetMapping("find/policy-owner")
	public String renderFindPolicyOwner(Model model) {
		String formAction = "policy-owners";
		
		return commonSupportParties.addFindPartyAttributes(formAction, FormNames.POLICY_OWNER_FIND, model);
		
	}
	
	/**
	 * Renders the Insured search tool.
	 * 
	 * @param dto a DTO to store the search criteria submitted by the user; includes personal details such as name, surname, birth date, birth number, email, street, or phone number.
	 * @return the name of the Thymeleaf template for the Insured search page.
	 */
	@GetMapping("find/insured")
	public String renderFindInsured(Model model) {
		String formAction = "insured";
		
		return commonSupportParties.addFindPartyAttributes(formAction, FormNames.INSURED_FIND, model);
	}
	
	/**
	 * Renders the Insured search tool.
	 * 
	 * @param dto a DTO to store the search criteria submitted by the user; includes personal details such as name, surname, birth date, birth number, email, street, or phone number.
	 * @return the name of the Thymeleaf template for the Insured search page.
	 */
	@GetMapping("find/uninsured")
	public String renderFindUninsured(Model model) {
		String formAction = "uninsured";
		
		return commonSupportParties.addFindPartyAttributes(formAction, FormNames.PARTY_FIND, model);

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
		String failRedirect = "parties/find/policy-owner";
		String successRedirect = "parties/found/profiles";
		
		return commonSupportParties.validateFindPartyFormPost(dto, PartyStatus.POLICY_OWNER, failRedirect, successRedirect, redirectAttributes);
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
		String failRedirect = "parties/find/insured";
		String successRedirect = "parties/found/profiles";
		
		return commonSupportParties.validateFindPartyFormPost(dto, PartyStatus.INSURED, failRedirect, successRedirect, redirectAttributes);
	}
	
	/**
	 * Handles the submission of the Insured search form.
	 * 
	 * @param dto a DTO to store the search criteria submitted by the user; includes personal details such as name, surname, birth date, birth number, email, street, or phone number.
	 * @param redirectAttributes used to pass temporary attributes (e.g., search results or error flags) between redirect pages.
	 * @return a redirect to the appropriate result page: either a list of matches, a not-found page, or the same page if the input is invalid.
	 */
	@PostMapping("find/uninsured")
	public String handleSearchUninsuredFormSubmit(PartiesDetailsDTO dto, RedirectAttributes redirectAttributes) {
		String failRedirect = "parties/find/uninsured";
		String successRedirect = "parties/found/profiles";
		
		return commonSupportParties.validateFindPartyFormPost(dto, null, failRedirect, successRedirect, redirectAttributes);
	}

}