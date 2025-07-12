package com.danger.insurance.insurances.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;
import com.danger.insurance.parties.data.repositories.PartyContractsRepository;

@Controller
@RequestMapping("insurances")
public class DisplayInsurancesController {

	@Autowired
	private InsurancesServiceImplementation insurancesService;
	
	@Autowired
	private ContractsServiceImplementation contractsService;
	
	@Autowired
	private PartyContractsRepository partyContractsRepository;
	
	@GetMapping
	public String renderIndex() {
		return "pages/insurances/index";
	}
	
	@GetMapping("insurance-{insurancesId}")
	public String renderInsuranceDetails(@PathVariable("insurancesId") long insurancesId, Model model) {
		model.addAttribute("insurance", insurancesService.getById(insurancesId));
		model.addAttribute("pageTitle", "Přehled pojištění");
		
		return "pages/insurances/detail";
	}
	
	@GetMapping("list")
	public String renderInsuranceList(Model model) {
		model.addAttribute("insurancesList", insurancesService.getAll());
		model.addAttribute("referenceLink", "insurance-");
		
		return "pages/insurances/list";
	}
	
	@GetMapping("contract-{contractId}")
	public String renderContractDetails(@PathVariable("contractId") long contractId, Model model) {
		model.addAttribute("contractDetails", contractsService.getById(contractId));
		model.addAttribute("pageTitle", "Přehled smlouvy");
		model.addAttribute("insuredParties", partyContractsRepository.findPartiesByContractId(contractId));
		model.addAttribute("referenceLink", "/parties/profile-");
		model.addAttribute("insurance", insurancesService.getById(contractsService.getById(contractId).getInsurancesEntity().getInsurancesId()));
		model.addAttribute("insuranceReferenceLink", "/insurances/insurance-");
		
		return "pages/insurances/contracts/detail";
	}
}