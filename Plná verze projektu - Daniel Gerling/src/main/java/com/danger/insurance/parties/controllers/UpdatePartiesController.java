package com.danger.insurance.parties.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.service.CommonSupportServiceParties;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;
import com.danger.insurance.validation.groups.OnUpdateParty;

@PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("parties")
public class UpdatePartiesController {
	
	@Autowired
	private PartiesServiceImplementation partiesService;
	
	@Autowired
	private CommonSupportServiceParties commonSupportService;
	
	@GetMapping("profile-{partyId}/update")
	public String renderUpdatePartyForm(@PathVariable("partyId") long partyId, Model model) {
		model.addAttribute("buttonLabel", ButtonLabels.CHANGE.getDisplayName());
		model.addAttribute("formName", FormNames.PARTY_UPDATE.getDisplayName());
		model.addAttribute("formAction", "update/confirmed");
		
		if (!model.containsAttribute("formDTO")) {
	        model.addAttribute("formDTO", partiesService.getById(partyId));
	    }
		
		return "pages/parties/create";
	}
	
	@PostMapping("profile-{partyId}/update/confirmed")
	public String handleUpdatePartyFormPost(@Validated(OnUpdateParty.class) @ModelAttribute("formDTO") PartiesDetailsDTO partiesDetailsDTO, BindingResult bindingResult, Model model, @PathVariable("partyId") long partyId, RedirectAttributes redirectAttributes) {
		return commonSupportService.processPartyValuesFormSubmit(bindingResult, redirectAttributes, null, partiesDetailsDTO, "parties/profile-", "parties/profile-"  + partyId + "/update");
	}
	
}