package com.danger.insurance.insurances.contracts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;

@Controller
@RequestMapping("insurances")
public class SearchContractsController {
	
	@Autowired
	private  ContractsServiceImplementation contractsService;

	@GetMapping("find")
	public String renderFindContractsForm(Model model) {
		model.addAttribute("contractDTO", new ContractsDTO());
		model.addAttribute("formAction", "find/validate");
		model.addAttribute("buttonLabel", "Vyhledej");
		
		return "pages/insurances/contracts/find";
	}
	
	@PostMapping("find/validate")
	public String handleFindContractsFormSubmit(@ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model, RedirectAttributes redirectAttributes) {
		
		//
		if (validateFormSubmit(contractsDTO)) {
			System.out.println(contractsDTO.getContractNumber());
			List<ContractsEntity> foundContracts = contractsService.findContractId(contractsDTO);
			redirectAttributes.addFlashAttribute("foundContractsList", foundContracts);
			
			return "redirect:/insurances/find/validated";
		}
		
		return "redirect:/insurances/find";
	}
	
	@GetMapping("find/validated")
	public String renderFoundContractsList(Model model) {
		model.addAttribute("referenceLink", "/insurances/contract-");
		
		return "pages/insurances/contracts/list";
	}

	private boolean validateFormSubmit(ContractsDTO contractsDTO) {
		int secondaryValueChecksPassed = 0;
		
		if (contractsDTO.getContractNumber() != null) {
			return true;
		}
		
		if (contractsDTO.getInsuranceType() != null) {
			secondaryValueChecksPassed++;
		}
		
		if (contractsDTO.getInsuredSubject() != null) {
			secondaryValueChecksPassed++;
		}
		
		if (contractsDTO.getBeginDate() != null) {
			secondaryValueChecksPassed++;
		}
				
		if (contractsDTO.getSignatureDate() != null) {
			secondaryValueChecksPassed++;
		}
		
		if (contractsDTO.getPricePerPeriod() != null) {
			secondaryValueChecksPassed++;
		}
		
		
		if (secondaryValueChecksPassed >= 3) {
			return true;
		}
		
		return false;
	}
}

