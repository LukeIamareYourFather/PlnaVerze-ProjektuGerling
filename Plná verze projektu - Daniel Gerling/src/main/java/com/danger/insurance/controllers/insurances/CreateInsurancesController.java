package com.danger.insurance.controllers.insurances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.models.dto.insurances.InsurancesDTO;
import com.danger.insurance.models.services.insurances.InsurancesServiceImplementation;

@Controller
@RequestMapping("/insurances")
public class CreateInsurancesController {

	@Autowired
	private InsurancesServiceImplementation insuracesService;
	
	@RequestMapping("create")
	public String renderCreateInsurancesForm(Model model) {
		model.addAttribute("insurancesDTO", new InsurancesDTO());
		model.addAttribute("formAction", "/insurances/create/new");
		
		return "pages/insurances/create";
	}
	
	@PostMapping("create/new") 
	public String handleCreateInsurancesSubmit(InsurancesDTO dto) {
		long insuranceId = insuracesService.create(dto);
		
		return "redirect:/insurances/insurance-" + insuranceId;
	}
	
}