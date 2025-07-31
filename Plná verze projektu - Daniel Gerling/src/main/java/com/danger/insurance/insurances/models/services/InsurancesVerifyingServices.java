package com.danger.insurance.insurances.models.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.DeleteInsurancesReasonsDTO;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;

@Service
public class InsurancesVerifyingServices {
	
	@Autowired
	private InsurancesSupportServices supportServices;

	public String verifyDeleteInsuranceFormPost(DeleteInsurancesReasonsDTO deleteInsurancesReasonsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//
		if (bindingResult.hasErrors()) {
			InsurancesDTO wrongDTO = null;
			supportServices.assignFailRedirectAttributes(deleteInsurancesReasonsDTO, wrongDTO, FlashMessages.INVALID_INPUT, redirectAttributes, bindingResult);
			
			return "redirect:/insurances/delete";
		}
		
		//
		if (deleteInsurancesReasonsDTO.getRequestDate().isAfter(LocalDate.now())) {
			InsurancesDTO wrongDTO = null;
			supportServices.assignFailRedirectAttributes(deleteInsurancesReasonsDTO, wrongDTO, FlashMessages.FUTURE_DATE, redirectAttributes, bindingResult);
			
			return "redirect:/insurances/delete";
			
		}
		
		return "redirect:/insurances/delete/find";
	}
	
}