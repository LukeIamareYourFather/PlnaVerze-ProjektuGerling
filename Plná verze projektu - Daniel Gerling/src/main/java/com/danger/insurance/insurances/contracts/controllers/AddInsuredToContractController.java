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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.dto.PartyContractsDTO;
import com.danger.insurance.insurances.contracts.models.dto.mappers.ContractsMapper;
import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;
import com.danger.insurance.insurances.contracts.models.services.PartyContractsServiceImplementation;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.mappers.PartiesMapper;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;

@Controller
@RequestMapping("insurances")
public class AddInsuredToContractController {

	@Autowired
	private PartiesServiceImplementation partiesService;
	
	@Autowired 
	private ContractsServiceImplementation contractsService;
	
	@Autowired
	private InsurancesServiceImplementation insurancesService;
	
	@Autowired
	private PartyContractsServiceImplementation partyContractsService;
	
	@Autowired 
	private ContractsMapper contractsMapper;
	
	@Autowired
	private PartiesMapper partiesMapper;
	
	@GetMapping("add")
	public String renderInsuredSearchForm(Model model) {
		model.addAttribute("formDTO", new PartiesDetailsDTO());
		model.addAttribute("formAction", "add/validate");
		
		return "pages/parties/insured/find";
	}
	
	@PostMapping("add/validate")
	public String validateInsuredSearchSubmit(@ModelAttribute("formDTO") PartiesDetailsDTO partyDetails, RedirectAttributes redirectAttributes) {
		
		//
		if (!validateSearchSubmit()) {
			return "redirect:/insurances/add";
		}
		
		List<PartiesEntity> foundInsured = partiesService.findUserId(partyDetails, PartyStatus.INSURED);
		redirectAttributes.addFlashAttribute("foundParties", foundInsured);
		
		return "redirect:/insurances/add/select";
	}
	
	@GetMapping("add/select")
	public String renderSelectInsuredList(Model model) {
		model.addAttribute("referenceLink", "select/party-");
		
		return "pages/parties/list";
	}
	
	@GetMapping("add/select/party-{partyId}")
	public String renderContractSearchForm(@PathVariable("partyId") long partyId, Model model) {
		model.addAttribute("contractDTO", new ContractsDTO());
		model.addAttribute("formAction", "party-" + partyId + "/validate");
		
		return "pages/insurances/contracts/find";
	}
	
	@PostMapping("add/select/party-{partyId}/validate")
	public String validateContractSearchForm(@PathVariable("partyId") long partyId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, RedirectAttributes redirectAttributes) {
		
		//
		if (!validateSearchSubmit()) {
			return "redirect:/insurances/add/select/party-" + partyId + "/validate";
		}
		
		List<ContractsEntity> foundContracts = contractsService.findContractId(contractsDTO);
		redirectAttributes.addFlashAttribute("foundContractsList", foundContracts);
		
		return "redirect:/insurances/add/select/party-" + partyId + "/pick";
	}
	
	@GetMapping("add/select/party-{partyId}/pick")
	public String renderSelectContractList(@PathVariable("partyId") long partyId, Model model) {
		model.addAttribute("referenceLink", "contract-");
		
		return "pages/insurances/contracts/list";
	}
	
	@GetMapping("add/select/party-{partyId}/contract-{contractId}")
	public String renderConfirmOverview(@PathVariable("partyId") long partyId, @PathVariable("contractId") long contractId, Model model) {
		model.addAttribute("party", partiesService.getById(partyId));
		model.addAttribute("insurance", insurancesService.getById(contractsService.getById(contractId).getInsurancesEntity().getInsurancesId()));
		model.addAttribute("contract", contractsService.getById(contractId));
		model.addAttribute("buttonLabel", "Potvrdit");
		model.addAttribute("referenceLink", "contract-" + contractId + "/confirmed");
		
		return "pages/insurances/assign-confirm";
	}
	
	@PostMapping("add/select/party-{partyId}/contract-{contractId}/confirmed")
	public String handleConfirmation(@PathVariable("partyId") long partyId, @PathVariable("contractId") long contractId, Model model) {
		PartyContractsDTO newEntry = new PartyContractsDTO();
		newEntry.setContractEntity(contractsMapper.toEntity(contractsService.getById(contractId)));
		newEntry.setPartyEntity(partiesMapper.toEntity(partiesService.getById(partyId)));
		newEntry.setContractRole(PartyStatus.INSURED);
		newEntry.setTodaysDate(LocalDate.now());
		partyContractsService.create(newEntry);
		
		return "redirect:/insurances";
	}
	
	private boolean validateSearchSubmit() {
		
		return true;
	}
}