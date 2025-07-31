package com.danger.insurance.insurances.contracts.models.services.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.insurances.contracts.data.repositories.PartyContractsRepository;
import com.danger.insurance.insurances.contracts.models.dto.ContractPartyRoleDTO;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

@Service
public class ContractsSupportServices {
	
	@Autowired
	private PartyContractsRepository partyContractsRepository;

	public List<ContractPartyRoleDTO> addContractRolesToParties(long contractId) {
		List<PartiesEntity> parties = partyContractsRepository.findPartiesByContractId(contractId);
		List<ContractPartyRoleDTO> contractPartyRoleDTOlist = new ArrayList<ContractPartyRoleDTO>();
		
		for (PartiesEntity party : parties) {
			ContractPartyRoleDTO contractPartyRoleDTO = new ContractPartyRoleDTO();
			contractPartyRoleDTO.setPartyEntity(party);
			contractPartyRoleDTO.setContractRole(partyContractsRepository.findPartyStatus(contractId, party.getPartyId()));
			contractPartyRoleDTOlist.add(contractPartyRoleDTO);
		}
		
		return contractPartyRoleDTOlist;
	}
	
	public void addRemovalFailRedirectAttributes(FlashMessages flashMessage, RemoveContractReasonsDTO removeContractReasonsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("removalReasonsDTO", removeContractReasonsDTO);
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.removalReasonsDTO", bindingResult);
		redirectAttributes.addFlashAttribute("error", flashMessage.getDisplayName());
	}
	
	public void addFailRedirectAttributes(ContractsDTO contractDTO, PartiesDetailsDTO partiesDetailsDTO, FlashMessages errorMessage, RedirectAttributes redirectAttributes, String dtoName, BindingResult bindingResult) {
		redirectAttributes.addFlashAttribute("error", errorMessage.getDisplayName());
		
		if (contractDTO != null) {
			redirectAttributes.addFlashAttribute(dtoName, contractDTO);
		} else if(partiesDetailsDTO != null) {
			redirectAttributes.addFlashAttribute(dtoName, partiesDetailsDTO);
		}
		
		if (bindingResult != null) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + dtoName, bindingResult);
		}
		
	}
	
	public boolean validateContractFormSubmit(ContractsDTO contractsDTO) {
		int secondaryValueChecksPassed = 0;
		
		if (!contractsDTO.getContractNumber().equals("")) {
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
		
		
		if (secondaryValueChecksPassed >= 3) {
			return true;
		}
		
		return false;
	}
	
}