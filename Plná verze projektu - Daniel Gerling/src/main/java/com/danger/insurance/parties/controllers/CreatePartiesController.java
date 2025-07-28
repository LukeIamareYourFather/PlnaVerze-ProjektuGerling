package com.danger.insurance.parties.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.service.CommonSupportServiceParties;
import com.danger.insurance.validation.groups.OnCreatePolicyOwner;

@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("parties")
public class CreatePartiesController {

	// Object initialization
	
	@Autowired
	private CommonSupportServiceParties commonSupportService;
	// Start of code
	
	/**
	 * Renders the create Policy Owner tool.
	 * 
	 * @param dto a DTO to store the search criteria submitted by the user; includes personal details such as name, surname, birth date, birth number, email, street, or phone number.
	 * @return the name of the Thymeleaf template for the create Policy Owner page.
	 */
	@GetMapping("create")
	public String renderCreatePolicyOwner(Model model) {
		addCreateFormAttributes("/parties/create", model);
		
		return "pages/parties/create";									// Redirect to the create Policy Owner page
	}
	
	
	@PostMapping("create")
	public String handleCreatePolicyOwnerFormSubmit(@Validated(OnCreatePolicyOwner.class) @ModelAttribute("formDTO") PartiesCreateDTO dto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		return processCreatePartyFormSubmit(dto, bindingResult, model, redirectAttributes);
	}
	
	// Helper methods
	
	//
	private void addCreateFormAttributes(String formAction, Model model) {
		model.addAttribute("formName", FormNames.PARTY_CREATE.getDisplayName());
		model.addAttribute("buttonLabel", ButtonLabels.CREATE.getDisplayName());
		model.addAttribute("formAction", formAction);
		
		//
		if (!model.containsAttribute("formDTO")) {
			model.addAttribute("formDTO", new PartiesCreateDTO());
		}
		
	}
	
	//
	private String processCreatePartyFormSubmit(PartiesCreateDTO dto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		return commonSupportService.processPartyValuesFormSubmit(bindingResult, redirectAttributes, dto, null, "parties/profile-", "parties/create");
	}
	
}