package com.danger.insurance.parties.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;
import com.danger.insurance.validation.groups.OnCreateInsured;
import com.danger.insurance.validation.groups.OnCreatePolicyOwner;

@Controller
@RequestMapping("/parties")
public class CreatePartiesController {

	// Object initialization
	
	@Autowired
	private PartiesServiceImplementation partiesService;								// Handles logic related to parties
	
	// Start of code
	
	/**
	 * Renders the create Policy Owner tool.
	 * 
	 * @param dto a DTO to store the search criteria submitted by the user; includes personal details such as name, surname, birth date, birth number, email, street, or phone number.
	 * @return the name of the Thymeleaf template for the create Policy Owner page.
	 */
	@GetMapping("create/policy-owner")
	public String renderCreatePolicyOwner(Model model) {
		addCreateFormAttributes("/parties/create/policy-owner", model);
		
		return "pages/parties/policy-owners/create";									// Redirect to the create Policy Owner page
	}
	
	/**
	 * Renders the create Insured tool.
	 * 
	 * @param dto a DTO to store the search criteria submitted by the user; includes personal details such as name, surname, birth date, birth number, email, street, or phone number.
	 * @return the name of the Thymeleaf template for the create Insured page.
	 */
	@GetMapping("create/insured")
	public String renderCreateInsured(Model model) {
		addCreateFormAttributes("/parties/create/insured", model);
		
		return "pages/parties/insured/create";											// Redirect to the create Insured page
	}
	
	@PostMapping("create/policy-owner")
	public String handleCreatePolicyOwnerFormSubmit(@Validated(OnCreatePolicyOwner.class) @ModelAttribute("formDTO") PartiesCreateDTO dto, BindingResult bindingResult, Model model) {
		return processCreatePartyFormSubmit(PartyStatus.POLICY_OWNER, dto, bindingResult, model);
	}
	
	@PostMapping("create/insured")
	public String handleCreateInsuredFormSubmit(@Validated(OnCreateInsured.class) @ModelAttribute("formDTO") PartiesCreateDTO dto, BindingResult bindingResult, Model model) {
		return processCreatePartyFormSubmit(PartyStatus.INSURED, dto, bindingResult, model);
	}
	
	// Helper methods
	
	//
	private void addCreateFormAttributes(String formAction, Model model) {
		model.addAttribute("formDTO", new PartiesCreateDTO());
		model.addAttribute("formAction", formAction);
	}
	
	//
	private String processCreatePartyFormSubmit(PartyStatus partyStatus, PartiesCreateDTO dto, BindingResult bindingResult, Model model) {
		String wrongIpnutRedirectLink = "pages/parties/create";
		
		//
		if (bindingResult.hasErrors()) {
			model.addAttribute("formDTO", dto);
			
			//
			if(partyStatus == PartyStatus.INSURED) {
				return wrongIpnutRedirectLink + "insured";
			}
			
			return wrongIpnutRedirectLink + "policy-owner";
	    }
		
		dto.setPartyStatus(partyStatus);
		long partyId = partiesService.create(dto);
		
		return "redirect:/parties/profile-" + partyId;
	}
	
}