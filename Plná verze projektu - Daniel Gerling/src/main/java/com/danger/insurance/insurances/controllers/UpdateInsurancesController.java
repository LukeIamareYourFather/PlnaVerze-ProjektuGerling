package com.danger.insurance.insurances.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;
import com.danger.insurance.validation.groups.OnUpdateInsurance;

@Controller
@RequestMapping("insurances")
public class UpdateInsurancesController {
	
	@Autowired
	private InsurancesServiceImplementation insurancesService;

	@GetMapping("insurance-{insuranceId}/edit")
	public String renderEditInsuranceForm(@PathVariable("insuranceId") long insurancesId, Model model) {
		model.addAttribute("formName", FormNames.INSURANCES_UPDATE.getDisplayName());
		model.addAttribute("buttonLabel", ButtonLabels.CHANGE.getDisplayName());
		model.addAttribute("formAction", "edit/confirmed");
		model.addAttribute("insurancesId", insurancesId);
		
		if (!model.containsAttribute("insurancesDTO")) {
			model.addAttribute("insurancesDTO", insurancesService.getById(insurancesId));
		}
		
		return "pages/insurances/create";
	}
	
	@PostMapping("insurance-{insuranceId}/edit/confirmed")
	public String handleEditInsuranceFormSubmit(@PathVariable("insuranceId") long insurancesId, @Validated(OnUpdateInsurance.class) @ModelAttribute("insurancesDTO") InsurancesDTO insurancesDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("insurancesDTO", insurancesDTO);
			redirectAttributes.addFlashAttribute("error", FlashMessages.INVALID_INPUT.getDisplayName());
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.insurancesDTO", bindingResult);
			
			return "redirect:/insurances/insurance-" + insurancesId + "/edit";
		}
		
		insurancesDTO.setInsurancesId(insurancesId);
		insurancesService.edit(insurancesDTO);
		
		return "redirect:/insurances/insurance-" + insurancesId;
	}
	
}