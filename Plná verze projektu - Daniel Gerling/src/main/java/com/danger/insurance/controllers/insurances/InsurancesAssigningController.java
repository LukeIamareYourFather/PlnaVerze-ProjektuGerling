package com.danger.insurance.controllers.insurances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.danger.insurance.models.dto.insurances.ContractsDTO;
import com.danger.insurance.models.dto.insurances.InsurancesDTO;
import com.danger.insurance.models.dto.mappers.InsurancesMapper;
import com.danger.insurance.models.dto.mappers.PartiesMapper;
import com.danger.insurance.models.dto.parties.PartiesDetailsDTO;
import com.danger.insurance.models.services.insurances.ContractsService;
import com.danger.insurance.models.services.insurances.ContractsServiceImplementation;
import com.danger.insurance.models.services.insurances.InsurancesService;
import com.danger.insurance.models.services.insurances.InsurancesServiceImplementation;
import com.danger.insurance.models.services.parties.PartiesService;
import com.danger.insurance.models.services.parties.PartiesServiceImplementation;

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
	private InsurancesMapper insurancesMapper;
	
	@Autowired
	private PartiesMapper partiesMapper;
	
	@ModelAttribute("contractDTO")
	public ContractsDTO contractDTO() {
	    return new ContractsDTO(); 											// Only called once, when session starts
	}
	
	@GetMapping("create")
	public String renderInsuranceAssignFormSelect(@ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {		
		model.addAttribute("formAction", "create/select");
		model.addAttribute("buttonLabel", "Potvrdit");
		model.addAttribute("ifShowCreateForm", true);
		
		return "pages/insurances/assign";
	}
	
	@PostMapping("create/select")
	public String renderInsuranceSelect(@ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		model.addAttribute("insurancesList", insuranceService.getAll());
		model.addAttribute("referenceLink", "selected-");
		
		return "pages/insurances/list";
	}
	
	@GetMapping("create/selected-{insurancesId}")
	public String handleRender(@PathVariable("insurancesId") long insurancesId , @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		InsurancesDTO selectedInsurance = insuranceService.getById(insurancesId);
		String redirectPage = "redirect:/insurances/";
		System.out.println(selectedInsurance.getIsAnnualPaymentRequired());
		//
		if(selectedInsurance.getIsAnnualPaymentRequired() && selectedInsurance.getIsAutoRenewalRequired()) {
			return redirectPage + "create/selected-" + insurancesId + "/user-find";
		}
		
		return redirectPage + "assign-final";
	}
	
	@GetMapping("create/selected-{insurancesId}/user-find") 
	public String renderAssignConfirmationForm(@PathVariable("insurancesId") long insurancesId , @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		model.addAttribute("foundParties", partiesService.getAll());
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
	public String renderHandleDandle(@PathVariable("insurancesId") long insurancesId , @PathVariable("partyId") long partyId, @ModelAttribute("contractDTO") ContractsDTO contractsDTO, Model model) {
		ContractsDTO populatedDTO = contractsDTO;
		populatedDTO.setInsurancesEntity(insurancesMapper.toEntity(insuranceService.getById(insurancesId)));
		populatedDTO.setPartiesEntity(partiesMapper.toEntity(partiesService.getById(partyId)));
		System.out.println(partiesService.getById(partyId).getName() + " : " + partiesService.getById(partyId).getPartyId());
		contractsService.create(populatedDTO);

		
		return "redirect:/parties/profile-" + partyId;
	}
}