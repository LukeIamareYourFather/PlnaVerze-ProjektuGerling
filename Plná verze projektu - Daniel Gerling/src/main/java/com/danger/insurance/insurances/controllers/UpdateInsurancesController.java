package com.danger.insurance.insurances.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;

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
		model.addAttribute("insurancesDTO", insurancesService.getById(insurancesId));
		model.addAttribute("insurancesId", insurancesId);
		
		return "pages/insurances/create";
	}
	
	@PostMapping("insurance-{insuranceId}/edit/confirmed")
	public String handeEditInsuranceFormSubmit(@PathVariable("insuranceId") long insurancesId, @ModelAttribute("insurancesDTO") InsurancesDTO insurancesDTO) {
		insurancesDTO.setInsurancesId(insurancesId);
		insurancesService.edit(insurancesDTO);
		
		return "redirect:/insurances/insurance-" + insurancesId;
	}
	
}