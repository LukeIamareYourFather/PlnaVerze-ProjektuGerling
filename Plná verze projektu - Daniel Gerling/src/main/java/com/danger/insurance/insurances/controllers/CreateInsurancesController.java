package com.danger.insurance.insurances.controllers;

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
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;
import com.danger.insurance.validation.groups.OnCreateInsurance;

@PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
public class CreateInsurancesController {

	@Autowired
	private InsurancesServiceImplementation insuracesService;
	
	@GetMapping("make")
	public String renderCreateInsurancesForm(Model model) {
		model.addAttribute("formAction", "make/validate");
		model.addAttribute("formName", FormNames.INSURANCES_CREATE.getDisplayName());
		model.addAttribute("buttonLabel", ButtonLabels.CREATE.getDisplayName());
		
		//
		if (!model.containsAttribute("insurancesDTO")) {
			model.addAttribute("insurancesDTO", new InsurancesDTO());
		}
		
		return "pages/insurances/create";
	}
	
	@PostMapping("make/validate")
	public String processCreateInsuranceFromPost(@Validated(OnCreateInsurance.class) @ModelAttribute("insurancesDTO") InsurancesDTO insurancesDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("insurancesDTO", insurancesDTO);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.insurancesDTO", bindingResult);
			redirectAttributes.addFlashAttribute("error", FlashMessages.INVALID_INPUT.getDisplayName());
			
			return "redirect:/insurances/make";
		}
		
		redirectAttributes.addFlashAttribute("insurancesDTO", insurancesDTO);
		
		return "redirect:/insurances/make/new";
	}
	
	@GetMapping("make/new") 
	public String handleCreateInsurancesSubmit(@ModelAttribute("insurancesDTO") InsurancesDTO dto) {
		long insuranceId = insuracesService.create(dto);
		
		return "redirect:/insurances/insurance-" + insuranceId;
	}
	
}