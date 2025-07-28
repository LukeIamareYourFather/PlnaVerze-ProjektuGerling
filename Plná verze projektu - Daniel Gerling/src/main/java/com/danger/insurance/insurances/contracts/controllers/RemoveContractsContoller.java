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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.archive.models.dto.mappers.RemovedContractsMapper;
import com.danger.insurance.archive.models.services.RemovedContractsServiceImplementation;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.data.repositories.PartyContractsRepository;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.services.CommonSupportContractsService;
import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;
import com.danger.insurance.validation.groups.OnRemoveContract;

@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
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
	
	@Autowired
	private CommonSupportContractsService commonSupportContracts;


	@ModelAttribute("removalReasonsDTO")
	public RemoveContractReasonsDTO reasonsDto() {
	    return new RemoveContractReasonsDTO(); 											// Only called once, when session starts
	}
	
	@GetMapping("terminate")
	public String renderRemoveInsurancesForm(Model model) {
		model.addAttribute("formName", "Ukončení smlouvy");
		model.addAttribute("buttonLabel", "Potvrdit");
		model.addAttribute("formAction", "terminate/validate");
		model.addAttribute("isConfirmForm", false);
		
		//
		if (!model.containsAttribute("removalReasonsDTO")) {
			model.addAttribute("removalReasonsDTO", reasonsDto());
		}
		
		return "pages/insurances/contracts/remove-form";
	}
	
	@PostMapping("terminate/validate")
	public String validateRemoveInsurancesFormPost(@Validated(OnRemoveContract.class) @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return commonSupportContracts.validateRemoveInsurancesFormPost(removeContractReasonsDTO, bindingResult, redirectAttributes, "insurances/terminate/find", "insurances/terminate");
	}
		
	@GetMapping("terminate/find")
	public String renderFindContractToRemoveForm(@SessionAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		model.addAttribute("formAction", "find/validate");
		model.addAttribute("formName", "Ukončení smlouvy - Výběr kontraktu");
		model.addAttribute("buttonLabel", "Vyhledej");
		
		if (!model.containsAttribute("contractDTO")) {
			model.addAttribute("contractDTO", new ContractsDTO());
		}
		
		return "pages/insurances/contracts/find";
	}
	
	@PostMapping("terminate/find/validate")
	public String validateFindContractToRemoveForm(@SessionAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, RedirectAttributes redirectAttributes) {
		
		//
		if (!isSearchRequestValid(contractsDTO)) {
			redirectAttributes.addFlashAttribute("error", FlashMessages.INSUFFICIENT_INPUT.getDisplayName());
			redirectAttributes.addFlashAttribute("contractDTO", contractsDTO);
			
			return "redirect:/insurances/terminate/find";
		}
		
		redirectAttributes.addFlashAttribute("contractDTO", contractsDTO);
		
		return "redirect:/insurances/terminate/select";
	}
	
	@GetMapping("terminate/select")
	public String renderSelectContractList(@SessionAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
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
		model.addAttribute("isConfirmForm", true);
		
		return "pages/insurances/contracts/delete-confirm";
	}
	
	@PostMapping("terminate/selected-{contractId}/confirmed")
	public String handleDeleteConfirmation(@PathVariable("contractId") long contractId, @ModelAttribute("removalReasonsDTO") RemoveContractReasonsDTO removeContractReasonsDTO, RedirectAttributes redirectAttributes) {
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
	
	private final boolean isSearchRequestValid(ContractsDTO dto) {
    	
    	// Should the birth number be provided
    	if(!dto.getContractNumber().equals("")) {
    		return true;										// Return evaluation as passed
    	} else {		// Or should the birth number be missing
			int checksPassed = 0;								// Initialize counter of passed checks
    		
			// Should the name be provided...
			if (dto.getInsuredSubject() != null) {
				checksPassed++;									// Increase the number of passed checks...
			}
    		
			if (dto.getInsuranceType() != null) {
				checksPassed++;							
			}
    		
			if (dto.getBeginDate() != null) {
				checksPassed++;					
			}
    		
			if (dto.getSignatureDate() != null) {
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