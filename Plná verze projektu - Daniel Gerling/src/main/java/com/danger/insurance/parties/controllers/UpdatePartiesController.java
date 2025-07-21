package com.danger.insurance.parties.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;

@PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("parties")
public class UpdatePartiesController {
	
	@Autowired
	private PartiesServiceImplementation partiesService;
	
	@GetMapping("profile-{partyId}/update")
	public String renderUpdatePartyForm(@PathVariable("partyId") long partyId, Model model) {
		model.addAttribute("buttonLabel", ButtonLabels.CHANGE.getDisplayName());
		model.addAttribute("formName", FormNames.PARTY_UPDATE.getDisplayName());
		model.addAttribute("formAction", "update/confirmed");
		model.addAttribute("formDTO", partiesService.getById(partyId));
		
		return "pages/parties/create";
	}
	
	@PostMapping("profile-{partyId}/update/confirmed")
	public String handleUpdatePartyFormPost(@PathVariable("partyId") long partyId, Model model, @ModelAttribute("formDTO") PartiesDetailsDTO partiesDetailsDTO) {
		partiesService.edit(partiesDetailsDTO);
		
		return "redirect:/parties/profile-" + partyId;
	}
	
}