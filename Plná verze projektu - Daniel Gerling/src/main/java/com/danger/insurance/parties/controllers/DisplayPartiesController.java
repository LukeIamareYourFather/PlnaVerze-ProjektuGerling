package com.danger.insurance.parties.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.archive.models.dto.DeletedPartiesDTO;
import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.data.repositories.PartyContractsRepository;
import com.danger.insurance.insurances.contracts.models.dto.PartyContractsProfileDTO;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesFoundSendDTO;
import com.danger.insurance.parties.models.service.DeletedPartiesServiceImplementation;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;

/**
 * Spring MVC controller for handling HTTP requests related to party entities.
 * <p>
 * Manages operations such as searching, displaying, and interacting with policy owners and insured individuals.
 * Mapped to the {@code /parties} URL path.
 * </p>
 */
@Controller
@RequestMapping("/parties")
public class DisplayPartiesController {
	
	// Object initialization
	
	@Autowired
	private PartiesServiceImplementation partiesService;										// Handles logic related to parties
	
	@Autowired
	private DeletedPartiesServiceImplementation deleterService;
	
	@Autowired
	private PartyContractsRepository partyContractsRepository;
	
	// Start of code
	
	/**
	 * Renders the main page of the Parties CRUD tool.
	 *
	 * @return the name of the Thymeleaf template for the main page.
	 */
	@GetMapping
	public String renderIndex() {
		return "pages/parties/index";															// Redirect to the main page
	}
	
	/**
	 * Renders the tool for displaying found Parties.
	 * 
	 * @param findingsDto a parsed DTO containing the list of policy owners found in the previous search operation.
	 * @param model the Spring MVC model used to pass attributes (e.g., the list of found parties) to the Thymeleaf view.
	 * @return the name of the Thymeleaf template that renders the found profiles page.
	 */
	@GetMapping("found/profiles")
	public String renderFoundProfiles(@ModelAttribute("findingsDto") PartiesFoundSendDTO sendDto, Model model) {
		List<PartiesEntity> foundParties = sendDto.getFoundParties();							// Retrieve the list of found Parties from DTO
		model.addAttribute("referenceLink", "/parties/profile-");
		model.addAttribute("foundParties", foundParties);										// Add the list to the model to be displayed
		
		return "pages/parties/found-parties";													// Render found profiles page
	}
	
	/**
	 * Renders the profile view for the selected party.
	 *
	 * @param partyId the ID of the party retrieved from the URL path variable.
	 * @param model the Spring MVC model used to pass attributes (e.g., the party's details) to the Thymeleaf view.
	 * @return the name of the Thymeleaf template that renders the party profile page.
	 */
	@GetMapping("profile-{partyId}")
	public String renderProfile(@PathVariable long partyId, Model model) {
		PartiesDetailsDTO fetchedParty = partiesService.getById(partyId);						// Retrieve the selected party by ID
		model.addAttribute("party", fetchedParty);												// Add party details to the model for display
		model.addAttribute("activeContractsWithRoles", addPartyStatusesToContracts(partyId));
		model.addAttribute("referenceLink", "/insurances/contract-");
		
		return "pages/parties/profile";															// Render the profile page
	}
	
	@GetMapping("parties-list")
	public String renderAllProfilesList(Model model) {
		List<PartiesDetailsDTO> allParties = partiesService.getAll();
		model.addAttribute("foundParties", allParties);
		
		return "pages/parties/list";
	}
	
	@GetMapping("deleted-parties-list")
	public String renderDeletedProfilesList(Model model) {
		List<DeletedPartiesDTO> allDeletedParties = deleterService.getAll();
		model.addAttribute("foundParties", allDeletedParties);
		
		return "pages/parties/deleted-list";
	}
	
	
	private List<PartyContractsProfileDTO> addPartyStatusesToContracts(long partyId) {
		List<PartyContractsProfileDTO> dtoList = new ArrayList<PartyContractsProfileDTO>();
		List<ContractsEntity> contracts = partyContractsRepository.findContractsByPartyId(partyId);
		
		for (ContractsEntity contract : contracts) {
			PartyContractsProfileDTO partyContractsProfileDTO = new PartyContractsProfileDTO();
			partyContractsProfileDTO.setContractId(contract.getContractId());
			partyContractsProfileDTO.setContractNumber(contract.getContractNumber());
			partyContractsProfileDTO.setContractRole(partyContractsRepository.findPartyStatus(contract.getContractId(), partyId));
			partyContractsProfileDTO.setInsuranceType(contract.getInsuranceType());
			partyContractsProfileDTO.setInsuranceName(contract.getInsurancesEntity().getName());
			dtoList.add(partyContractsProfileDTO);
		}
		
		return dtoList;
	}
	

}