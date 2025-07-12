package com.danger.insurance.insurances.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;

@Controller
@RequestMapping("/insurances")
public class CreateInsurancesController {

	@Autowired
	private InsurancesServiceImplementation insuracesService;
	
	@RequestMapping("make")
	public String renderCreateInsurancesForm(Model model) {
		model.addAttribute("insurancesDTO", new InsurancesDTO());
		model.addAttribute("formAction", "/insurances/make/new");
		
		return "pages/insurances/create";
	}
	
	@PostMapping("make/new") 
	public String handleCreateInsurancesSubmit(InsurancesDTO dto) {
		long insuranceId = insuracesService.create(dto);
		
		return "redirect:/insurances/insurance-" + insuranceId;
	}
	
}