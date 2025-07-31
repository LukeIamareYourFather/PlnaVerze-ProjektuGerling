package com.danger.insurance.parties.models.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.data.repositories.PartyContractsRepository;
import com.danger.insurance.insurances.contracts.models.dto.PartyContractsProfileDTO;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesFoundSendDTO;

@Service
public class PartiesSupportServices {
	
	@Autowired
	private PartyContractsRepository partyContractsRepository;

	/**
	 * Attaches a list of found parties to the redirect using a flash attribute.
	 * This enables the target page (after redirect) to access and display the results.
	 *
	 * @param foundParties retrieved list containing found parties.
	 * @param redirectAttributes the redirect context used to carry temporary attributes across the redirect.
	 */
	public void attachFoundPartiesToRedirect(List<PartiesEntity> foundParties, RedirectAttributes redirectAttributes) {
	    PartiesFoundSendDTO sendDto = new PartiesFoundSendDTO();								// Create a DTO to carry search results
	    sendDto.setFoundParties(foundParties);													// Assign the found parties to the created DTO
	    redirectAttributes.addFlashAttribute("findingsDto", sendDto);							// Pass the populated DTO as attribute for the redirected page to display found parties
	}	
	
	public boolean validateFormSubmit(BindingResult bindingResult, RedirectAttributes redirectAttributes, PartiesCreateDTO partiesCreateDTO, PartiesDetailsDTO partiesDetailsDTO) {
		
		//
		if (bindingResult.hasErrors()) {	
			assignFailedValidationAttributes(FlashMessages.INVALID_INPUT, redirectAttributes, partiesCreateDTO, partiesDetailsDTO, bindingResult);
			
			return false;
	    }
		
		//
		if (((partiesCreateDTO != null) && (partiesCreateDTO.getBirthDay().isAfter(LocalDate.now()))) || ((partiesDetailsDTO != null) && (partiesDetailsDTO.getBirthDay().isAfter(LocalDate.now())))) {
			assignFailedValidationAttributes(FlashMessages.FUTURE_BIRTHDAY, redirectAttributes, partiesCreateDTO, partiesDetailsDTO, bindingResult);
			
			return false;
		}
		
		return true;
	}

	public void assignCorrespondingRedirectDTO(RedirectAttributes redirectAttributes, PartiesCreateDTO partiesCreateDTO, PartiesDetailsDTO partiesDetailsDTO) {
		
		//
		if (partiesCreateDTO != null) {
			redirectAttributes.addFlashAttribute("formDTO", partiesCreateDTO);
		} else {
			redirectAttributes.addFlashAttribute("formDTO", partiesDetailsDTO);
		}
		
	}
	
	public void assignFailedValidationAttributes(FlashMessages flashMessage, RedirectAttributes redirectAttributes, PartiesCreateDTO partiesCreateDTO, PartiesDetailsDTO partiesDetailsDTO, BindingResult bindingResult) {
		assignCorrespondingRedirectDTO(redirectAttributes, partiesCreateDTO, partiesDetailsDTO);
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.formDTO", bindingResult);
		redirectAttributes.addFlashAttribute("error", flashMessage.getDisplayName());
	}

	public List<PartyContractsProfileDTO> addPartyStatusesToContracts(long partyId) {
		List<PartyContractsProfileDTO> dtoList = new ArrayList<PartyContractsProfileDTO>();
		List<ContractsEntity> contracts = partyContractsRepository.findContractsByPartyId(partyId);
		
		for (ContractsEntity contract : contracts) {
			PartyContractsProfileDTO partyContractsProfileDTO = new PartyContractsProfileDTO();
			partyContractsProfileDTO.setContractId(contract.getContractId());
			partyContractsProfileDTO.setContractNumber(contract.getContractNumber());
			partyContractsProfileDTO.setContractRole(partyContractsRepository.findPartyStatus(contract.getContractId(), partyId));
			partyContractsProfileDTO.setInsuranceType(contract.getInsuranceType());
			partyContractsProfileDTO.setInsuranceName(contract.getInsurancesEntity().getName());
			dtoList.add(partyContractsProfileDTO);
		}
		
		return dtoList;
	}
	
}