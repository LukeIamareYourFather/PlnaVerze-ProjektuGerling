package com.danger.insurance.insurances.contracts.controllers;

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
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;

@Controller
@RequestMapping("insurances")
public class UpdateContractsController {

	@Autowired
	private ContractsServiceImplementation contractsService;
	
	@GetMapping("contract-{contractId}/edit")
	public String renderEditContractForm(@PathVariable("contractId") long contractId, Model model) {
		model.addAttribute("formName", FormNames.CONTRACTS_UPDATE.getDisplayName());
		model.addAttribute("buttonLabel", ButtonLabels.CHANGE.getDisplayName());
		model.addAttribute("contractDTO", contractsService.getById(contractId));
		model.addAttribute("formAction", "edit/confirm");
		model.addAttribute("ifShowCreateForm", true);
		
		return "pages/insurances/assign";
	}
	
	@PostMapping("contract-{contractId}/edit/confirm")
	public String handleEditContractFormSubmit(@PathVariable("contractId") long contractId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO) {
		contractsDTO.setContractId(contractId);
		contractsDTO.setInsurancesEntity(contractsService.getById(contractId).getInsurancesEntity());
		contractsService.edit(contractsDTO);
		
		return "redirect:/insurances/contract-" + contractId;
	}
	
}