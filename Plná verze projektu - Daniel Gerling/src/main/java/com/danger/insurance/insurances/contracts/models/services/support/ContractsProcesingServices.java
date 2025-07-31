package com.danger.insurance.insurances.contracts.models.services.support;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.archive.models.dto.mappers.RemovedContractsMapper;
import com.danger.insurance.archive.models.services.RemovedContractsServiceImplementation;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.data.repositories.PartyContractsRepository;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.dto.PartyContractsDTO;
import com.danger.insurance.insurances.contracts.models.dto.mappers.ContractsMapper;
import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;
import com.danger.insurance.insurances.contracts.models.services.PartyContractsServiceImplementation;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.dto.mappers.InsurancesMapper;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.mappers.PartiesMapper;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;
import com.danger.insurance.parties.models.service.PartiesVerifyingServices;

@Service
public class ContractsProcesingServices {
	
	@Autowired
	private PartiesServiceImplementation partiesService;
	
	@Autowired
	private InsurancesServiceImplementation insurancesService;
	
	@Autowired 
	private ContractsServiceImplementation contractsService;
	
	@Autowired
	private PartyContractsServiceImplementation partyContractsService;
	
	@Autowired
	private RemovedContractsServiceImplementation removedContractsService;
	
	@Autowired
	private PartiesVerifyingServices partiesVerifyingServices;
	
	@Autowired
	private PartiesMapper partiesMapper;
	
	@Autowired
	private InsurancesMapper insurancesMapper;
	
	@Autowired 
	private ContractsMapper contractsMapper;
	
	@Autowired
	private RemovedContractsMapper removedContractsMapper;
	
	@Autowired
	private PartyContractsRepository partyContractsRepository;	
	
	@Autowired
	private ContractsSupportServices supportServices;
	
	private String contractDtoName = "contractDTO";
	
	public String processAddInsuredToContractConfirmation(long partyId, long contractId, RedirectAttributes redirectAttributes) {
		PartyContractsDTO newEntry = new PartyContractsDTO();
		newEntry.setContractEntity(contractsMapper.toEntity(contractsService.getById(contractId)));
		newEntry.setPartyEntity(partiesMapper.toEntity(partiesService.getById(partyId)));
		newEntry.setContractRole(PartyStatus.INSURED);
		newEntry.setTodaysDate(LocalDate.now());
		partyContractsService.create(newEntry);
		redirectAttributes.addFlashAttribute("success", FlashMessages.CONTRACT_INSURED_ADDED.getDisplayName());
		
		return "redirect:/insurances";
	}	
	
	public String processFindContractsFormPost(ContractsDTO contractDTO, RedirectAttributes redirectAttributes) {
		
		//
		if (!supportServices.validateContractFormSubmit(contractDTO)) {
			PartiesDetailsDTO wrongDTO = null;
			BindingResult noResult = null;
			supportServices.addFailRedirectAttributes(contractDTO, wrongDTO, FlashMessages.INSUFFICIENT_INPUT, redirectAttributes, contractDtoName, noResult);
			
			return "redirect:/insurances/find";
		}
		
		List<ContractsEntity> foundContracts = contractsService.findContractId(contractDTO);	// prepsat na DTO! Ne kurde?
		redirectAttributes.addFlashAttribute("foundContractsList", foundContracts);
		
		return "redirect:/insurances/find/validated";
	}
	
	public String processConfirmedContractAssignmentToPartyPost(ContractsDTO contractDTO, long insurancesId, long partyId, RedirectAttributes redirectAttributes) {
		ContractsDTO populatedDTO = contractDTO;
		populatedDTO.setInsurancesEntity(insurancesMapper.toEntity(insurancesService.getById(insurancesId)));
		long newContractId = contractsService.create(populatedDTO);
		
		if (newContractId == 0) {
			redirectAttributes.addFlashAttribute("error", FlashMessages.INSUREE_ALREADY_INSURED.getDisplayName());
			
			return "redirect:/parties/profile-" + partyId;
		}
		
		PartyContractsDTO partyContractEntry = new PartyContractsDTO();
		partyContractEntry.setContractEntity(contractsMapper.toEntity(contractsService.getById(newContractId)));
		partyContractEntry.setPartyEntity(partiesMapper.toEntity(partiesService.getById(partyId)));
		partyContractEntry.setContractRole(PartyStatus.POLICY_OWNER);
		partyContractEntry.setTodaysDate(LocalDate.now());
		partyContractsService.create(partyContractEntry);	
		redirectAttributes.addFlashAttribute("success", FlashMessages.CONTRACT_CREATED.getDisplayName());
			
		return "redirect:/insurances";
	}
	
	public String processSelectInsuredList(PartiesDetailsDTO partiesDetailsDTO, RedirectAttributes redirectAttributes) {
		
		//
		if (!partiesVerifyingServices.verifyPartySearchFormPost(partiesDetailsDTO)) {		
			ContractsDTO wrongDTO = null;
			String dtoName = "formDTO";
			BindingResult noResult = null;
			supportServices.addFailRedirectAttributes(wrongDTO, partiesDetailsDTO, FlashMessages.INSUFFICIENT_INPUT, redirectAttributes, dtoName, noResult);
			return "redirect:/insurances/remove/find";
		}
		
		redirectAttributes.addFlashAttribute("foundParties", partiesService.findUserId(partiesDetailsDTO, PartyStatus.INSURED));
		
		return "redirect:/insurances/remove/select";
	}
	
	public String processRemoveContractInsuredConfirmation(RemoveContractReasonsDTO removeContractReasonsDTO, long partyId, long contractId, RedirectAttributes redirectAttributes) {
		InsurancesDTO contractInsuranceDTO = insurancesService.getById(contractsService.getById(contractId).getInsurancesEntity().getInsurancesId());
		removeContractReasonsDTO.setBirthNumber(partiesService.getById(partyId).getBirthNumber());
		removeContractReasonsDTO.setTodaysDate(LocalDate.now());
		removeContractReasonsDTO.setInsuranceName(contractInsuranceDTO.getName());
		removedContractsMapper.mergeToRemoveContractReasonsDTO(contractsService.getById(contractId), contractInsuranceDTO, removeContractReasonsDTO);
		partyContractsRepository.deleteById(partyContractsRepository.findContractPartyIdOfPartyContract(contractId, partyId));
		removedContractsService.create(removeContractReasonsDTO);
		redirectAttributes.addFlashAttribute("success", FlashMessages.CONTRACT_INSURED_REMOVED.getDisplayName());		
		
		return "redirect:/insurances";
	}
	
	public String processEditContractFormSubmit(ContractsDTO contractsDTO, long contractId, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//
		if (bindingResult.hasErrors()) {
			PartiesDetailsDTO wrongDTO = null;
			supportServices.addFailRedirectAttributes(contractsDTO, wrongDTO, FlashMessages.INVALID_INPUT, redirectAttributes, contractDtoName, bindingResult);

			return "redirect:/insurances/contract-" + contractId + "/edit";
		}
		
		if (contractsDTO.getBeginDate().isAfter(LocalDate.now()) || contractsDTO.getSignatureDate().isAfter(LocalDate.now())) {
			PartiesDetailsDTO wrongDTO = null;
			supportServices.addFailRedirectAttributes(contractsDTO, wrongDTO, FlashMessages.FUTURE_DATES, redirectAttributes, contractDtoName, bindingResult);
	
			return "redirect:/insurances/contract-" + contractId + "/edit";
		}
		
		contractsDTO.setContractId(contractId);
		contractsDTO.setInsurancesEntity(contractsService.getById(contractId).getInsurancesEntity());
		contractsService.edit(contractsDTO);
		redirectAttributes.addFlashAttribute("success", FlashMessages.CONTRACT_UPDATED.getDisplayName());
		
		return "redirect:/insurances/contract-" + contractId;
	}
	
	public String processDeleteConfirmationPost(RemoveContractReasonsDTO removeContractReasonsDTO, long contractId, RedirectAttributes redirectAttributes) {
		List<PartiesEntity> partiesToTerminate = partyContractsRepository.findPartiesByContractId(contractId);
		InsurancesDTO contractInsuranceDTO = insurancesService.getById(contractsService.getById(contractId).getInsurancesEntity().getInsurancesId());
		removeContractReasonsDTO.setTodaysDate(LocalDate.now());
		removeContractReasonsDTO.setInsuranceName(contractInsuranceDTO.getName());
		removedContractsMapper.mergeToRemoveContractReasonsDTO(contractsService.getById(contractId), contractInsuranceDTO, removeContractReasonsDTO);

		//
		for (int i = 0; i < partiesToTerminate.size(); i++) {
			long partyIdToDelete = partiesToTerminate.get(i).getPartyId();
			removeContractReasonsDTO.setBirthNumber(partiesService.getById(partyIdToDelete).getBirthNumber());
			partyContractsRepository.deleteById(partyIdToDelete);
			removedContractsService.create(removeContractReasonsDTO);
		}
		
		contractsService.delete(contractId);
		redirectAttributes.addFlashAttribute("success", FlashMessages.CONTRACT_REMOVED.getDisplayName());
		
		return "redirect:/insurances";
	}
	
}