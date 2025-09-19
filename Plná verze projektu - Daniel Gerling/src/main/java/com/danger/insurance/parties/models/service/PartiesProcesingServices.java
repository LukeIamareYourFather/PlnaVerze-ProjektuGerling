package com.danger.insurance.parties.models.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.DeletedPartiesDTO;
import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.archive.models.dto.mappers.RemovedContractsMapper;
import com.danger.insurance.archive.models.services.DeletedPartiesServiceImplementation;
import com.danger.insurance.archive.models.services.RemovedContractsServiceImplementation;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.data.enums.ContractsRemovalReason;
import com.danger.insurance.insurances.contracts.data.repositories.PartyContractsRepository;
import com.danger.insurance.insurances.contracts.models.dto.mappers.ContractsMapper;
import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesReasonsFormDTO;

@Service
public class PartiesProcesingServices {

	@Autowired
	private PartiesServiceImplementation partiesService;
	
	@Autowired
	private InsurancesServiceImplementation insurancesService;
	
	@Autowired 
	private ContractsServiceImplementation contractsService;
	
	@Autowired
	private RemovedContractsServiceImplementation removedContractsService;
	
	@Autowired
	private DeletedPartiesServiceImplementation deleterService;
	
	@Autowired
	private ContractsMapper contractsMapper;
	
	@Autowired
	private RemovedContractsMapper removedContractsMapper;
	
	@Autowired
	private PartyContractsRepository partyContractsRepository;
	
	@Autowired
	private PartiesSupportServices supportServices;
	
	public String processPartyValuesFormSubmit(BindingResult bindingResult, RedirectAttributes redirectAttributes, PartiesCreateDTO partiesCreateDTO, PartiesDetailsDTO partiesDetailsDTO, String successLink, String failRedirect) {
		
		if ((partiesCreateDTO != null) && (partiesDetailsDTO != null)) {
		    throw new IllegalArgumentException("Only one DTO should be provided at a time.");
		}
		
		if (!supportServices.validateFormSubmit(bindingResult, redirectAttributes, partiesCreateDTO, partiesDetailsDTO)) {
			return "redirect:/" + failRedirect;
		}
		
		long partyId = 0;
		
		//
		if (partiesCreateDTO != null) {
			partyId = partiesService.create(partiesCreateDTO);
			redirectAttributes.addFlashAttribute("success", FlashMessages.PARTY_CREATED.getDisplayName());
		} else {
			partiesService.edit(partiesDetailsDTO);
			partyId = Long.valueOf(partiesDetailsDTO.getPartyId());
			redirectAttributes.addFlashAttribute("success", FlashMessages.PARTY_UPDATED.getDisplayName());
		}
	
		return "redirect:/" + successLink + partyId;
	}

	public String processDeletePolicyOwnerFormSubmit(PartiesReasonsFormDTO partiesReasonsFormDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		//
		if (bindingResult.hasErrors()) {
			assignFailRedirectAttributes(partiesReasonsFormDTO, FlashMessages.INVALID_INPUT, bindingResult, redirectAttributes);
			
			return "redirect:/parties/delete";
		}
		
		//
		if(partiesReasonsFormDTO.getDateOfRequest().isAfter(LocalDate.now())) {
			assignFailRedirectAttributes(partiesReasonsFormDTO, FlashMessages.FUTURE_DATE, bindingResult, redirectAttributes);
			
			return "redirect:/parties/delete";	
		}
		
		return "redirect:/parties/delete/find";
	}
	
		
	
	//
	public String processConfirmedRemoval(PartiesReasonsFormDTO reasonsDto, long partyId, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		List<ContractsEntity> partyOwnedContracts = partyContractsRepository.findContractsByPartyId(partyId);
		
		//
		for (ContractsEntity partyOwnedContract : partyOwnedContracts) {
			LocalDate todaysDate = LocalDate.now();
			
			//
			if (partyContractsRepository.findPartyStatus(partyOwnedContract.getContractId(), partyId) == PartyStatus.POLICY_OWNER) {
				RemoveContractReasonsDTO removalReasonsDTO = new RemoveContractReasonsDTO(); 
				InsurancesDTO insuranceDTO = insurancesService.getById(partyOwnedContract.getInsurancesEntity().getInsurancesId());
				removalReasonsDTO = removedContractsMapper.mergeToRemoveContractReasonsDTO(contractsMapper.toDTO(partyOwnedContract), insuranceDTO, removalReasonsDTO);
				assignAutomationAttributes(removalReasonsDTO, todaysDate, partyId, insuranceDTO);
				removedContractsService.create(removalReasonsDTO);
				contractsService.delete(partyOwnedContract.getContractId());
			}
			
		}
		
		reasonsDto.setTodaysDate(LocalDate.now());
		PartiesDetailsDTO partyDTO = partiesService.getById(partyId);
		DeletedPartiesDTO deleteDTO = deleterService.createDeleteDTO(reasonsDto, partyDTO);
		deleterService.create(deleteDTO);
		partiesService.delete(partyId);
		sessionStatus.setComplete();
		redirectAttributes.addFlashAttribute("success", FlashMessages.PARTY_REMOVED.getDisplayName());
	
		return "redirect:/parties";
	}
	
	private void assignFailRedirectAttributes(PartiesReasonsFormDTO partiesReasonsFormDTO, FlashMessages errorMessage, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("reasonsDTO", partiesReasonsFormDTO);
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reasonsDTO", bindingResult);
		redirectAttributes.addFlashAttribute("error", errorMessage.getDisplayName());
	}
	
	private void assignAutomationAttributes(RemoveContractReasonsDTO removalReasonsDTO, LocalDate todaysDate, Long partyId, InsurancesDTO insuranceDTO) {
		removalReasonsDTO.setDateOfCancellation(todaysDate);
		removalReasonsDTO.setDateOfRequest(todaysDate);
		removalReasonsDTO.setDeleteReason(ContractsRemovalReason.AUTOMATED);
		removalReasonsDTO.setDescription("Smlouva uzavřena, jelikož byla osoba odstraněna ze systému");
		removalReasonsDTO.setTodaysDate(todaysDate);
		removalReasonsDTO.setBirthNumber(partiesService.getById(partyId).getBirthNumber());
		removalReasonsDTO.setInsuranceName(insuranceDTO.getName());
	}
	
}