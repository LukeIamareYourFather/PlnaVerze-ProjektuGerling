package com.danger.insurance.insurances.contracts.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.archive.models.dto.mappers.RemovedContractsMapper;
import com.danger.insurance.archive.models.services.RemovedContractsServiceImplementation;
import com.danger.insurance.insurances.contracts.data.repositories.PartyContractsRepository;
import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;

@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
@SessionAttributes("removalReasonsDTO")
public class RemoveContractInsuredController {

	@Autowired
	private PartiesServiceImplementation partiesService;
	
	@Autowired
	private ContractsServiceImplementation contractsService;
	
	@Autowired
	private RemovedContractsServiceImplementation removedContractsService;
	
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
	
	@GetMapping("remove")
	public String renderRemoveInsuredFromContract(@ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		model.addAttribute("formName", "Odebrání pojistníka ze smlouvy");
		model.addAttribute("buttonLabel", "Potvrdit");
		model.addAttribute("formAction", "remove/find");
		
		return "pages/insurances/contracts/remove-form";
	}
	
	@PostMapping("remove/find")
	public String renderSelectInsuredForm(@ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		model.addAttribute("formDTO", new PartiesDetailsDTO());
		model.addAttribute("formAction", "select");
		model.addAttribute("formName", "Odebrání pojistníka ze smlouvy - vyhledání pojistníka");
		model.addAttribute("buttonLabel", "Hledat");
		
		return "pages/parties/insured/find";
	}
	
	@PostMapping("remove/select")
	public String renderSelectInsuredList(@ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, @ModelAttribute("formDTO") PartiesDetailsDTO partiesDetailsDTO, Model model) {
		model.addAttribute("foundParties", partiesService.findUserId(partiesDetailsDTO, PartyStatus.INSURED));
		model.addAttribute("referenceLink", "selected-");
		
		return "pages/parties/list";
	}
	
	@GetMapping("remove/selected-{partyId}")
	public String renderSelectContractToRemoveList(@PathVariable("partyId") long partyId, @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		model.addAttribute("party", partiesService.getById(partyId));
		model.addAttribute("partyContracts", partyContractsRepository.findContractsByPartyId(partyId));
		model.addAttribute("contractReferenceLink", "selected-" + partyId + "/confirm-");
		
		return "pages/insurances/contracts/remove-select";
	}
	
	@GetMapping("remove/selected-{partyId}/confirm-{contractId}")
	public String renderRemoveInsuredFromContractConfirmation(@PathVariable("partyId") long partyId, @PathVariable("contractId") long contractId, @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		model.addAttribute("pageTitle","Odebrání pojistníka ze smlouvy - potvrzení odebrání");
		model.addAttribute("party", partiesService.getById(partyId));
		model.addAttribute("contract", contractsService.getById(contractId));
		model.addAttribute("referenceLink", "confirmed-" + contractId);
		model.addAttribute("buttonLabel", "Povtrdit");
		
		return "pages/insurances/contracts/remove-confirm";
	}
	
	@PostMapping("remove/selected-{partyId}/confirmed-{contractId}")
	public String handlRemoveConfirmation(@PathVariable("partyId") long partyId, @PathVariable("contractId") long contractId, @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		InsurancesDTO contractInsuranceDTO = insurancesService.getById(contractsService.getById(contractId).getInsurancesEntity().getInsurancesId());
		removeContractReasonsDTO.setBirthNumber(partiesService.getById(partyId).getBirthNumber());
		removeContractReasonsDTO.setTodaysDate(LocalDate.now());
		removeContractReasonsDTO.setInsuranceName(contractInsuranceDTO.getName());
		removedContractsMapper.mergeToRemoveContractReasonsDTO(contractsService.getById(contractId), contractInsuranceDTO, removeContractReasonsDTO);
		partyContractsRepository.deleteById(partyContractsRepository.findContractPartyIdOfPartyContract(contractId, partyId));
		removedContractsService.create(removeContractReasonsDTO);
		
		return "redirect:/insurances";
	}
	
}