package com.danger.insurance.insurances.contracts.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.archive.models.services.RemovedContractsServiceImplementation;
import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.data.repositories.PartyContractsRepository;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.dto.mappers.RemovedContractsMapper;
import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;

@Controller
@RequestMapping("insurances")
@SessionAttributes("removalReasonsDTO")
public class RemoveContractsContoller {
	
	@Autowired
	private ContractsServiceImplementation contractsService;

	@Autowired
	private RemovedContractsServiceImplementation removedContractsService;
	
	@Autowired
	private PartiesServiceImplementation partiesService;
	
	@Autowired
	private InsurancesServiceImplementation insurancesService;
	
	@Autowired
	private PartyContractsRepository partyContractsRepository;
	
	@Autowired
	private RemovedContractsMapper removedContractsMapper;


	@ModelAttribute("removalReasonsDTO")
	public RemoveContractReasonsDTO reasonsDto() {
	    return new RemoveContractReasonsDTO(); 											// Only called once, when session starts
	}
	
	@GetMapping("terminate")
	public String renderRemoveInsurances(@ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		model.addAttribute("formName", "Ukončení smlouvy");
		model.addAttribute("buttonLabel", "Potvrdit");
		model.addAttribute("formAction", "terminate/process");
		
		return "pages/insurances/contracts/remove-form";
	}
		
	@PostMapping("terminate/process")
	public String renderFindContractForm(@ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		model.addAttribute("contractDTO", new ContractsDTO());
		model.addAttribute("formAction", "select");
		model.addAttribute("formName", "Ukončení smlouvy - Výběr kontraktu");
		model.addAttribute("buttonLabel", "Vyhledej");
		
		return "pages/insurances/contracts/find";
	}
	
	@PostMapping("terminate/select")
	public String renderSelectContractList(@ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		List<ContractsEntity> foundContracts = contractsService.findContractId(contractsDTO);
		model.addAttribute("foundContractsList", foundContracts);
		model.addAttribute("referenceLink", "selected-");
		
		return "pages/insurances/contracts/list";
	}
	
	@GetMapping("terminate/selected-{contractId}") 
	public String renderDeleteContractConfirmation(@PathVariable("contractId") long contractId, @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		model.addAttribute("contract", contractsService.getById(contractId));
		model.addAttribute("foundParties", partyContractsRepository.findPartiesByContractId(contractId));
		model.addAttribute("referenceLink", "selected-" + contractId + "/confirmed");
		model.addAttribute("partyReferenceLink", "/parties/profile-");
		model.addAttribute("buttonLabel", "Ukončit");
		
		return "pages/insurances/contracts/delete-confirm";
	}
	
	@PostMapping("terminate/selected-{contractId}/confirmed")
	public String handleDeleteConfirmation(@PathVariable("contractId") long contractId, @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO) {
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
		
		return "redirect:/insurances";
	}
	
}