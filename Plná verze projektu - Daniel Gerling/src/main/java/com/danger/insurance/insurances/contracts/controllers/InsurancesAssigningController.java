package com.danger.insurance.insurances.contracts.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;
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
import com.danger.insurance.parties.models.dto.PartiesFoundSendDTO;
import com.danger.insurance.parties.models.dto.mappers.PartiesMapper;
import com.danger.insurance.parties.models.service.CommonSupportServiceParties;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;
import com.danger.insurance.validation.groups.OnCreateContract;

@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
@SessionAttributes("contractDTO")
public class InsurancesAssigningController {

	@Autowired
	private InsurancesServiceImplementation insuranceService;
	
	@Autowired
	private PartiesServiceImplementation partiesService;
	
	@Autowired
	private ContractsServiceImplementation contractsService;
	
	@Autowired
	private PartyContractsServiceImplementation partyContractsService;
	
	@Autowired
	private InsurancesMapper insurancesMapper;
	
	@Autowired
	private PartiesMapper partiesMapper;
	
	@Autowired
	private ContractsMapper contractsMapper;
	
	@Autowired
	private CommonSupportServiceParties supportServiceParties;
	
	@ModelAttribute("contractDTO")
	public ContractsDTO contractDTO() {
	    return new ContractsDTO(); 											// Only called once, when session starts
	}
	
	@GetMapping("create")
	public String renderInsuranceAssignFormSelect(Model model) {		
		model.addAttribute("formAction", "create/validate");
		model.addAttribute("buttonLabel", ButtonLabels.CREATE.getDisplayName());
		model.addAttribute("formName", FormNames.CONTRACTS_CREATE.getDisplayName());
		model.addAttribute("ifShowCreateForm", true);
		
		if (!model.containsAttribute("contractDTO")) {
			model.addAttribute(contractDTO());
		}
		
		return "pages/insurances/assign";
	}
	
	@PostMapping("create/validate")
	public String validateInsuranceAssignFormSubmit(@Validated(OnCreateContract.class) @ModelAttribute("contractDTO") ContractsDTO contractsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("contractDTO", contractsDTO);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contractDTO", bindingResult);
			redirectAttributes.addFlashAttribute("error", FlashMessages.INVALID_INPUT.getDisplayName());
			
			return "redirect:/insurances/create";
		}
		
		//
		if (contractsDTO.getBeginDate().isAfter(LocalDate.now()) || contractsDTO.getSignatureDate().isAfter(LocalDate.now())) {
			redirectAttributes.addFlashAttribute("contractDTO", contractsDTO);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contractDTO", bindingResult);
			redirectAttributes.addFlashAttribute("error", FlashMessages.FUTURE_DATES.getDisplayName());
			
			return "redirect:/insurances/create";
		}
		
		return "redirect:/insurances/create/select";
	}
	
	@GetMapping("create/select")
	public String renderInsuranceSelect(@ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		List<InsurancesDTO> foundInsurances = insuranceService.getAllInsurancesByType(contractsDTO.getInsuranceType());
		
		if (foundInsurances.isEmpty()) {
			return "pages/insurances/non-applicable";
		}
		
		model.addAttribute("insurancesList", foundInsurances);
		model.addAttribute("referenceLink", "selected-");
		
		return "pages/insurances/list";
	}
	
	@GetMapping("create/selected-{insurancesId}")
	public String handleRender(@PathVariable("insurancesId") long insurancesId , @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		//InsurancesDTO selectedInsurance = insuranceService.getById(insurancesId);
		String redirectPage = "redirect:/insurances/";
		//System.out.println(selectedInsurance.getIsAnnualPaymentRequired());
		
		return redirectPage + "create/selected-" + insurancesId + "/user-find";
	}

	@GetMapping("create/selected-{insurancesId}/user-find")
	public String renderFindPartyToAssignContractForm(@PathVariable("insurancesId") long insurancesId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		String formAction = "user-find/validate";
		
		return supportServiceParties.addFindPartyAttributes(formAction, FormNames.PARTY_FIND, model);
	}
	
	@PostMapping("create/selected-{insurancesId}/user-find/validate")
	public String validateFindPartyToAssignContractFormPost(@PathVariable("insurancesId") long insurancesId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, @ModelAttribute("formDTO") PartiesDetailsDTO partiesDetailsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String prefixRedirect = "insurances/create/selected-" + insurancesId;
		String failRedirect =  prefixRedirect + "/user-find";
		String successRedirect = prefixRedirect + "/user-select";
		
		return supportServiceParties.validateFindPartyFormPost(partiesDetailsDTO, PartyStatus.REGISTERED, failRedirect, successRedirect, redirectAttributes);
	}
	
	@GetMapping("create/selected-{insurancesId}/user-select") 
	public String renderAssignConfirmationForm(@PathVariable("insurancesId") long insurancesId , @ModelAttribute("contractDTO") ContractsDTO contractsDTO, @ModelAttribute("findingsDto") PartiesFoundSendDTO sendDto, Model model) {
		List<PartiesEntity> foundParties = sendDto.getFoundParties();
		model.addAttribute("foundParties", foundParties);	
		model.addAttribute("referenceLink",  "party-");
		
		return "pages/parties/list";
	}
	
	@GetMapping("create/selected-{insurancesId}/party-{partyId}")
	public String renderHandle(@PathVariable("insurancesId") long insurancesId , @PathVariable("partyId") long partyId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		model.addAttribute("insurance", insuranceService.getById(insurancesId));
		model.addAttribute("contract", contractsDTO);
		model.addAttribute("party", partiesService.getById(partyId));
		model.addAttribute("referenceLink", "party-" + partyId + "/confirmed");
		model.addAttribute("buttonLabel", "Potvrdit");
		
		return "pages/insurances/assign-confirm";
	}
	
	@PostMapping("create/selected-{insurancesId}/party-{partyId}/confirmed")
	public String renderHandleDandle(@PathVariable("insurancesId") long insurancesId , @PathVariable("partyId") long partyId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model, RedirectAttributes redirectAttributes) {
		ContractsDTO populatedDTO = contractsDTO;
		populatedDTO.setInsurancesEntity(insurancesMapper.toEntity(insuranceService.getById(insurancesId)));
		long contractId = contractsService.create(populatedDTO);
		
		PartyContractsDTO partyContractEntry = new PartyContractsDTO();
		partyContractEntry.setContractEntity(contractsMapper.toEntity(contractsService.getById(contractId)));
		partyContractEntry.setPartyEntity(partiesMapper.toEntity(partiesService.getById(partyId)));
		partyContractEntry.setContractRole(PartyStatus.POLICY_OWNER);
		partyContractEntry.setTodaysDate(LocalDate.now());
		partyContractsService.create(partyContractEntry);
		redirectAttributes.addFlashAttribute("success", FlashMessages.CONTRACT_CREATED.getDisplayName());
		
		return "redirect:/parties/profile-" + partyId;
	}
}