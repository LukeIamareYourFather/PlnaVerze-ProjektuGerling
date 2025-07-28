package com.danger.insurance.insurances.contracts.controllers;

import java.time.LocalDate;

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
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;
import com.danger.insurance.validation.groups.OnUpdateContract;

@Controller
@RequestMapping("insurances")
public class UpdateContractsController {

	@Autowired
	private ContractsServiceImplementation contractsService;
	
	@GetMapping("contract-{contractId}/edit")
	public String renderEditContractForm(@PathVariable("contractId") long contractId, Model model) {
		model.addAttribute("formName", FormNames.CONTRACTS_UPDATE.getDisplayName());
		model.addAttribute("buttonLabel", ButtonLabels.CHANGE.getDisplayName());
		model.addAttribute("formAction", "edit/confirm");
		model.addAttribute("ifShowCreateForm", true);
		
		if (!model.containsAttribute("contractDTO")) {
			model.addAttribute("contractDTO", contractsService.getById(contractId));
		}
		
		return "pages/insurances/assign";
	}
	
	@PostMapping("contract-{contractId}/edit/confirm")
	public String handleEditContractFormSubmit(@PathVariable("contractId") long contractId,@Validated(OnUpdateContract.class) @ModelAttribute("contractDTO") ContractsDTO contractsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("contractDTO", contractsDTO);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contractDTO", bindingResult);
			redirectAttributes.addFlashAttribute("error", FlashMessages.INVALID_INPUT.getDisplayName());

			return "redirect:/insurances/contract-" + contractId + "/edit";
		}
		
		if (contractsDTO.getBeginDate().isAfter(LocalDate.now()) || contractsDTO.getSignatureDate().isAfter(LocalDate.now())) {
			redirectAttributes.addFlashAttribute("contractDTO", contractsDTO);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contractDTO", bindingResult);
			redirectAttributes.addFlashAttribute("error", FlashMessages.FUTURE_DATES.getDisplayName());
			
			return "redirect:/insurances/contract-" + contractId + "/edit";
		}
		
		contractsDTO.setContractId(contractId);
		contractsDTO.setInsurancesEntity(contractsService.getById(contractId).getInsurancesEntity());
		contractsService.edit(contractsDTO);
		redirectAttributes.addFlashAttribute("success", FlashMessages.CONTRACT_UPDATED.getDisplayName());
		
		return "redirect:/insurances/contract-" + contractId;
	}
	
}