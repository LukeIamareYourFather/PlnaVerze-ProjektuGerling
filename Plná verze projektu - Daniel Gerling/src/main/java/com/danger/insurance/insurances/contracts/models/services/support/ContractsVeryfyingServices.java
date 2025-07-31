package com.danger.insurance.insurances.contracts.models.services.support;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;
import com.danger.insurance.parties.models.service.PartiesVerifyingServices;

@Service
public class ContractsVeryfyingServices {
	
	@Autowired
	private PartiesServiceImplementation partiesService;
	
	@Autowired 
	private ContractsServiceImplementation contractsService;
	
	@Autowired
	private ContractsSupportServices supportServices;
	
	@Autowired
	private PartiesVerifyingServices partiesVerifyingServices;
	
	private String contractDtoName = "contractDTO";

	public String verifyRemoveInsurancesFormPost(RemoveContractReasonsDTO removeContractReasonsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, String successRedirect, String failRedirect) {
		
		//
		if (bindingResult.hasErrors()) {
			supportServices.addRemovalFailRedirectAttributes(FlashMessages.INVALID_INPUT, removeContractReasonsDTO, bindingResult, redirectAttributes);
			
			return "redirect:/" + failRedirect;
		}
		
		//
		if (removeContractReasonsDTO.getDateOfRequest().isAfter(LocalDate.now()) || removeContractReasonsDTO.getDateOfCancellation().isAfter(LocalDate.now())) {
			supportServices.addRemovalFailRedirectAttributes(FlashMessages.FUTURE_DATES, removeContractReasonsDTO, bindingResult, redirectAttributes);
			
			return "redirect:/" + failRedirect;	
		}
		
		return "redirect:/" + successRedirect;
	}

	public String verifyPartySearchPost(PartiesDetailsDTO partyDetails, RedirectAttributes redirectAttributes) {
		
		//
		if (!partiesVerifyingServices.verifyPartySearchFormPost(partyDetails)) {
			ContractsDTO wrongDTO = null;
			BindingResult noResult = null;
			String dtoName = "formDTO";
			supportServices.addFailRedirectAttributes(wrongDTO, partyDetails, FlashMessages.INSUFFICIENT_INPUT, redirectAttributes, dtoName, noResult);
			return "redirect:/insurances/add";
		}
		
		List<PartiesEntity> foundInsured = partiesService.findUserId(partyDetails, PartyStatus.REGISTERED); // convert to DTO man...
		redirectAttributes.addFlashAttribute("foundParties", foundInsured);
		
		return "redirect:/insurances/add/select";
	}
	
	
	
	public String verifyContractSearchFormPost(long partyId, ContractsDTO contractDTO, RedirectAttributes redirectAttributes) {
		
		//
		if (!supportServices.validateContractFormSubmit(contractDTO)) {
			PartiesDetailsDTO wrongDTO = null;
			BindingResult noResult = null;
			supportServices.addFailRedirectAttributes(contractDTO, wrongDTO, FlashMessages.INSUFFICIENT_INPUT, redirectAttributes, contractDtoName, noResult);
		
			return "redirect:/insurances/add/select/party-" + partyId;
		}
		
		List<ContractsEntity> foundContracts = contractsService.findContractId(contractDTO);
		redirectAttributes.addFlashAttribute("foundContractsList", foundContracts);
		
		return "redirect:/insurances/add/select/party-" + partyId + "/pick";
	}
	
	public String verifyInsuranceAssignFormPost(ContractsDTO contractDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		//
		if (bindingResult.hasErrors()) {
			PartiesDetailsDTO wrongDTO = null;
			supportServices.addFailRedirectAttributes(contractDTO, wrongDTO, FlashMessages.INVALID_INPUT, redirectAttributes, contractDtoName, bindingResult);
			
			return "redirect:/insurances/create";
		}
		
		//
		if (contractDTO.getBeginDate().isAfter(LocalDate.now()) || contractDTO.getSignatureDate().isAfter(LocalDate.now())) {
			PartiesDetailsDTO wrongDTO = null;	
			supportServices.addFailRedirectAttributes(contractDTO, wrongDTO, FlashMessages.FUTURE_DATES, redirectAttributes, contractDtoName, bindingResult);
			
			return "redirect:/insurances/create";
		}
		
		return "redirect:/insurances/create/select";
	}
	
	public String verifyFindContractToremoveFormPost(ContractsDTO contractsDTO, RedirectAttributes redirectAttributes) {
		
		//
		if (!supportServices.validateContractFormSubmit(contractsDTO)) {
			PartiesDetailsDTO wrongDTO = null;
			BindingResult noResult = null;
			supportServices.addFailRedirectAttributes(contractsDTO, wrongDTO, FlashMessages.INSUFFICIENT_INPUT, redirectAttributes, contractDtoName, noResult);
			
			return "redirect:/insurances/terminate/find";
		}
		
		redirectAttributes.addFlashAttribute("contractDTO", contractsDTO);
		
		return "redirect:/insurances/terminate/select";
	}
	
}