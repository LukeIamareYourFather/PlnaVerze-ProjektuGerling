package com.danger.insurance.insurances.models.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.DeleteInsurancesReasonsDTO;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;

@Service
public class InsurancesSupportServices {

	public void assignFailRedirectAttributes(DeleteInsurancesReasonsDTO deleteInsurancesReasonsDTO, InsurancesDTO insurancesDTO, FlashMessages errorMessage, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
		String bindingResultFlag = "org.springframework.validation.BindingResult.";
		String deleteReasonsDtoFlag = "insurancesReasonsDTO";
		String insurancesDtoFlag = "insurancesDTO";
		
		if (deleteInsurancesReasonsDTO != null) {
			redirectAttributes.addFlashAttribute(bindingResultFlag + deleteReasonsDtoFlag, bindingResult);
			redirectAttributes.addFlashAttribute(deleteReasonsDtoFlag, deleteInsurancesReasonsDTO);
		} else {
			redirectAttributes.addFlashAttribute(bindingResultFlag + insurancesDtoFlag, bindingResult);
			redirectAttributes.addFlashAttribute(insurancesDtoFlag, insurancesDTO);
		}
		
		redirectAttributes.addFlashAttribute("error", errorMessage.getDisplayName());
	}
	
}