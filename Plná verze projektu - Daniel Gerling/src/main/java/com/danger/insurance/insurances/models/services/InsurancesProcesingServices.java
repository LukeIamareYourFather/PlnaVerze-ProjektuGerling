package com.danger.insurance.insurances.models.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.DeleteInsurancesReasonsDTO;
import com.danger.insurance.archive.models.dto.DeletedInsurancesDTO;
import com.danger.insurance.archive.models.services.DeletedInsurancesServiceImplementation;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;

@Service
public class InsurancesProcesingServices {

	@Autowired
	private InsurancesServiceImplementation insurancesService;
	
	@Autowired
	private DeletedInsurancesServiceImplementation deleterService;
	
	@Autowired
	private InsurancesSupportServices supportServices;
	
	public String processCreateInsuranceFormPost(InsurancesDTO insurancesDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		//
		if (bindingResult.hasErrors()) {
			DeleteInsurancesReasonsDTO wrongDto = null;
			supportServices.assignFailRedirectAttributes(wrongDto, insurancesDTO, FlashMessages.INVALID_INPUT, redirectAttributes, bindingResult);
			
			return "redirect:/insurances/make";
		}
		
		redirectAttributes.addFlashAttribute("insurancesDTO", insurancesDTO);
		
		return "redirect:/insurances/make/new";
	}
	
	public String processCreateInsurancesPost(InsurancesDTO insuranceDTO) {
		long insuranceId = insurancesService.create(insuranceDTO);
		
		return "redirect:/insurances/insurance-" + insuranceId;
	}
	
	public String processDeleteInsuranceConfirmedPost(DeleteInsurancesReasonsDTO deleteInsurancesReasonsDTO, long insurancesId, RedirectAttributes redirectAttributes, SessionStatus sessionStatus) {
		DeletedInsurancesDTO deletedInsuranceDTO = deleterService.createDeleteDTO(deleteInsurancesReasonsDTO, insurancesService.getById(insurancesId));
		deletedInsuranceDTO.setTodaysDate(LocalDate.now());
		deleterService.create(deletedInsuranceDTO);
		insurancesService.delete(insurancesId);
		sessionStatus.setComplete();
		redirectAttributes.addFlashAttribute("success", FlashMessages.INSURANCE_REMOVED.getDisplayName());
		
		return "redirect:/insurances";
	}

	public String processEditInsuranceFormPost(InsurancesDTO insurancesDTO, long insurancesId, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			DeleteInsurancesReasonsDTO wrongDTO = null;
			supportServices.assignFailRedirectAttributes(wrongDTO, insurancesDTO, FlashMessages.INVALID_INPUT, redirectAttributes, bindingResult);
			
			return "redirect:/insurances/insurance-" + insurancesId + "/edit";
		}
		
		insurancesDTO.setInsurancesId(insurancesId);
		insurancesService.edit(insurancesDTO);
		
		return "redirect:/insurances/insurance-" + insurancesId;
	}
	
}