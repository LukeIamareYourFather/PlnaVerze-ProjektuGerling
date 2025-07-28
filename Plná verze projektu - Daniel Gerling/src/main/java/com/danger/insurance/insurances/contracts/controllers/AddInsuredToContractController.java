package com.danger.insurance.insurances.contracts.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;
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
import com.danger.insurance.parties.models.service.CommonSupportServiceParties;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;

@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
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
	
	@Autowired
	private CommonSupportServiceParties commonSupportParties;
	
	@GetMapping("add")
	public String renderInsuredSearchForm(Model model) {
		model.addAttribute("formAction", "add/validate");
		model.addAttribute("formName", FormNames.CONTRACTS_ADD_INSURED.getDisplayName() + " - vyhledání pojistníka");
		model.addAttribute("buttonLabel", ButtonLabels.FIND.getDisplayName());
		
		//
		if (!model.containsAttribute("formDTO")) {
			model.addAttribute("formDTO", new PartiesDetailsDTO());
		}
		
		return "pages/parties/find";
	}
	
	@PostMapping("add/validate")
	public String validatePartySearchSubmit(@ModelAttribute("formDTO") PartiesDetailsDTO partyDetails, RedirectAttributes redirectAttributes) {
		
		//
		if (!commonSupportParties.validatePartySearchFormPost(partyDetails)) {
			redirectAttributes.addFlashAttribute("formDTO", partyDetails);
			redirectAttributes.addFlashAttribute("error", FlashMessages.INSUFFICIENT_INPUT.getDisplayName());
			
			return "redirect:/insurances/add";
		}
		
		List<PartiesEntity> foundInsured = partiesService.findUserId(partyDetails, PartyStatus.REGISTERED);
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
		model.addAttribute("formAction", "party-" + partyId + "/validate");
		model.addAttribute("buttonLabel", ButtonLabels.FIND.getDisplayName());
		model.addAttribute("formName", FormNames.CONTRACTS_ADD_INSURED.getDisplayName() + " - vyhledání smlouvy");
		
		//
		if (!model.containsAttribute("contractDTO")) {
			model.addAttribute("contractDTO", new ContractsDTO());
		}
		
		return "pages/insurances/contracts/find";
	}
	
	@PostMapping("add/select/party-{partyId}/validate")
	public String validateContractSearchForm(@PathVariable("partyId") long partyId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, RedirectAttributes redirectAttributes) {
		
		//
		if (!isContractSearchRequestValid(contractsDTO)) {
			redirectAttributes.addFlashAttribute("contractDTO", contractsDTO);
			redirectAttributes.addFlashAttribute("error", FlashMessages.INSUFFICIENT_INPUT.getDisplayName());
			
			return "redirect:/insurances/add/select/party-" + partyId;
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
	public String handleConfirmation(@PathVariable("partyId") long partyId, @PathVariable("contractId") long contractId, Model model, RedirectAttributes redirectAttributes) {
		PartyContractsDTO newEntry = new PartyContractsDTO();
		newEntry.setContractEntity(contractsMapper.toEntity(contractsService.getById(contractId)));
		newEntry.setPartyEntity(partiesMapper.toEntity(partiesService.getById(partyId)));
		newEntry.setContractRole(PartyStatus.INSURED);
		newEntry.setTodaysDate(LocalDate.now());
		partyContractsService.create(newEntry);
		redirectAttributes.addFlashAttribute("success", FlashMessages.INSURANCE_CREATED.getDisplayName());
		
		return "redirect:/insurances";
	}
	
	private final boolean isContractSearchRequestValid(ContractsDTO contractsDTO) {
	
	// Should the birth number be provided
	if(!contractsDTO.getContractNumber().equals("")) {
		return true;										// Return evaluation as passed
	} else {		// Or should the birth number be missing
		int checksPassed = 0;								// Initialize counter of passed checks
		
		// Should the name be provided...
		if (contractsDTO.getInsuredSubject() != null) {
			checksPassed++;									// Increase the number of passed checks...
		}
		
		if (contractsDTO.getInsuranceType() != null) {
			checksPassed++;							
		}
		
		if (contractsDTO.getBeginDate() != null) {
			checksPassed++;					
		}
		
		if (contractsDTO.getSignatureDate() != null) {
			checksPassed++;
		}
		
		// Should the desired amount of checks be passed
		if (checksPassed >= 3) {							
			return true;									// Return evaluation as passed
		}
	}
	
	return false;											// Since no requirement was met, return evaluation as failed
}
}