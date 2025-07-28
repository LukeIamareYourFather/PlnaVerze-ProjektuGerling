package com.danger.insurance.insurances.contracts.models.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.infopages.data.enums.FlashMessages;

@Service
public class CommonSupportContractsService {

	public String validateRemoveInsurancesFormPost(RemoveContractReasonsDTO removeContractReasonsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, String successRedirect, String failRedirect) {
		
		//
		if (bindingResult.hasErrors()) {
			assignFailRedirectAttributes(FlashMessages.INVALID_INPUT, removeContractReasonsDTO, bindingResult, redirectAttributes);
			
			return "redirect:/" + failRedirect;
		}
		
		//
		if (removeContractReasonsDTO.getDateOfRequest().isAfter(LocalDate.now()) || removeContractReasonsDTO.getDateOfCancellation().isAfter(LocalDate.now())) {
			assignFailRedirectAttributes(FlashMessages.FUTURE_DATES, removeContractReasonsDTO, bindingResult, redirectAttributes);
			
			return "redirect:/" + failRedirect;	
		}
		
		return "redirect:/" + successRedirect;
	}
	
	private void assignFailRedirectAttributes(FlashMessages flashMessage, RemoveContractReasonsDTO removeContractReasonsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("removalReasonsDTO", removeContractReasonsDTO);
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.removalReasonsDTO", bindingResult);
		redirectAttributes.addFlashAttribute("error", flashMessage.getDisplayName());
	}
	
}