package com.danger.insurance.controllers.insurances;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.danger.insurance.models.dto.insurances.RemoveContractReasonsDTO;

@Controller
@RequestMapping("insurances")
@SessionAttributes("remomvalReasonsDTO")
public class RemoveContractsContoller {

	@ModelAttribute("remomvalReasonsDTO")
	public RemoveContractReasonsDTO reasonsDto() {
	    return new RemoveContractReasonsDTO(); 											// Only called once, when session starts
	}
	
	@GetMapping("terminate")
	public String renderRemoveInsurances(@ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		model.addAttribute("formName", "Odebrání aktivní smlouvy smlouvy");
		model.addAttribute("buttonLabel", "Potvrdit");
		model.addAttribute("formAction", "terminate/process");
		
		return "pages/insurances/contracts/remove-form";
	}
		
	@PostMapping("terminate/process")
	public String handleRemoveFormPost(@ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		
		
		return "redirect:/remove/select/";
	}
	
}