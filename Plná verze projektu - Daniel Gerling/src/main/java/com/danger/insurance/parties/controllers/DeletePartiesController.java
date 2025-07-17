//  (•_•)
// <)   )╯ Clean
// /    \   Code
package com.danger.insurance.parties.controllers;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.DeletedPartiesDTO;
import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.archive.models.dto.mappers.RemovedContractsMapper;
import com.danger.insurance.archive.models.services.DeletedPartiesServiceImplementation;
import com.danger.insurance.archive.models.services.RemovedContractsServiceImplementation;
import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.data.enums.ContractsRemovalReason;
import com.danger.insurance.insurances.contracts.data.repositories.PartyContractsRepository;
import com.danger.insurance.insurances.contracts.models.dto.mappers.ContractsMapper;
import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesReasonsFormDTO;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;

@Controller
@RequestMapping("/parties")
@SessionAttributes("reasonsDTO")
public class DeletePartiesController {

	// Object initialization
	
	@Autowired
	private PartiesServiceImplementation partiesService;								// Handles logic related to parties
	
	@Autowired
	private DeletedPartiesServiceImplementation deleterService;
	
	@Autowired 
	private ContractsServiceImplementation contractsService;
	
	@Autowired
	private PartyContractsRepository partyContractsRepository;	
	
	@Autowired
	private RemovedContractsServiceImplementation removedContractsService;
	
	@Autowired
	private RemovedContractsMapper removedContractsMapper;
	
	@Autowired
	private InsurancesServiceImplementation insurancesService;
	
	@Autowired
	private ContractsMapper contractsMapper;
	
	@ModelAttribute("reasonsDTO")
	public PartiesReasonsFormDTO reasonsDto() {
	    return new PartiesReasonsFormDTO(); 											// Only called once, when session starts
	}
	
	// Start of code
	
	/**
	 * Renders the delete Policy Owner tool.
	 * 
	 * @param dto a DTO to store the search criteria submitted by the user; includes personal details such as name, surname, birth date, birth number, email, street, or phone number.
	 * @return the name of the Thymeleaf template for the create Policy Owner page.
	 */
	@GetMapping("delete")
	public String renderDeletePolicyOwner(@ModelAttribute("reasonsDTO") PartiesReasonsFormDTO reasonsDto, Model model) {
		model.addAttribute("formAction", "/parties/delete/find");
		
		return "pages/parties/delete-reasons";									// Redirect to the create Policy Owner page
	}
	
	@PostMapping("delete/find")
	public String renderFindPolicyOwnerToDeleteForm(@ModelAttribute("reasonsDTO") PartiesReasonsFormDTO reasonsDto, Model model, RedirectAttributes redirectAttributes) {
		assignSearchAttributes("formDTO", "formAction", "/parties/delete/found", null, null, model, true);
		
		return "pages/parties/policy-owners/find";
	}
	
	@PostMapping("delete/found")
	public String handleFoundPolicyOwnersToDelete(@ModelAttribute("reasonsDTO") PartiesReasonsFormDTO reasonsDto, PartiesDetailsDTO partyDto, Model model, RedirectAttributes redirectAttributes) {
		assignSearchAttributes("foundParties", "referenceLink", "/parties/delete/party-", partyDto, PartyStatus.REGISTERED, model, false);
		
		return "pages/parties/found-parties";
	}
	
	@GetMapping("delete/party-{partyId}")
	public String renderDeletePolicyOwnersConfirmationForm(@PathVariable long partyId, @ModelAttribute("reasonsDTO") PartiesReasonsFormDTO reasonsDto, Model model, RedirectAttributes redirectAttributes) {
		assignDeleteConfirmationAttributes(partyId, reasonsDto, model);
		
		return "pages/parties/profile";
	}
	
	
	@PostMapping("delete/party-{partyId}/confirmed")
	public String handleDeletePolicyOwnerConfirmation(@PathVariable long partyId, @ModelAttribute("reasonsDTO") PartiesReasonsFormDTO reasonsDto, SessionStatus sessionStatus) {
		processConfirmedRemoval(reasonsDto, partyId, sessionStatus);
		
		return "redirect:/parties";
	}

	
	// Helper methods
	
	/**
	 * Assigns search-related attributes to the model, conditionally based on request type.
	 * Used to avoid code repetition when setting up form attributes for search pages.
	 *
	 * @param firstAttributeName the name of the first model attribute (typically the form DTO) !rename to firstObjectKey or somtuin and add code annotaion for possibilites also bellow!
	 * @param secondAttributeName the name of the second model attribute (e.g., form action endpoint)
	 * @param referenceLink the action URL that the form will submit to
	 * @param partyDto the DTO object containing party details to populate the form
	 * @param partyStatus the status of the party (e.g., POLICY_OWNER, INSURED)
	 * @param model the Spring model used to pass data to the view
	 * @param ifFindAssignmentRequested whether to perform the
	 */
	private void assignSearchAttributes(String firstKeyName, String secondKeyName, String referenceLink, PartiesDetailsDTO partyDto, PartyStatus partyStatus, Model model, boolean ifFindAssignmentRequested) {
		
		//
		if(ifFindAssignmentRequested) {
			model.addAttribute(firstKeyName, new PartiesDetailsDTO());
		} else {		//
			List<PartiesEntity> foundParties = partiesService.findUserId(partyDto, partyStatus);
			model.addAttribute(firstKeyName, foundParties);
			
		}
		
		model.addAttribute(secondKeyName, referenceLink);
	}
	
	//
	private void assignDeleteConfirmationAttributes(long partyId, PartiesReasonsFormDTO reasonsDto, Model model) {
		PartiesDetailsDTO partyDto = partiesService.getById(partyId);
		model.addAttribute("pageTitle", "Potvrzení odebrání pojistníka");
		model.addAttribute("ifShowDeleteForm", true);
		model.addAttribute("reasonsDTO", reasonsDto);
		model.addAttribute("party", partyDto);
		model.addAttribute("confirmationLink", "party-" + partyId + "/confirmed");
	}
	
	//
	private void processConfirmedRemoval(PartiesReasonsFormDTO reasonsDto, long partyId, SessionStatus sessionStatus) {
		List<ContractsEntity> partyOwnedContracts = partyContractsRepository.findContractsByPartyId(partyId);
		
		//
		for (ContractsEntity partyOwnedContract : partyOwnedContracts) {
			LocalDate todaysDate = LocalDate.now();
			
			//
			if (partyContractsRepository.findPartyStatus(partyOwnedContract.getContractId(), partyId) == PartyStatus.POLICY_OWNER) {
				RemoveContractReasonsDTO removalReasonsDTO = new RemoveContractReasonsDTO(); 
				InsurancesDTO insuranceDTO = insurancesService.getById(partyOwnedContract.getInsurancesEntity().getInsurancesId());
				removalReasonsDTO = removedContractsMapper.mergeToRemoveContractReasonsDTO(contractsMapper.toDTO(partyOwnedContract), insuranceDTO, removalReasonsDTO);
				removalReasonsDTO.setDateOfCancellation(todaysDate);
				removalReasonsDTO.setDateOfRequest(todaysDate);
				removalReasonsDTO.setDeleteReason(ContractsRemovalReason.AUTOMATED);
				removalReasonsDTO.setDescription("Smlouva uzavřena, jelikož byla osoba odstraněna ze systému");
				removalReasonsDTO.setTodaysDate(todaysDate);
				removalReasonsDTO.setBirthNumber(partiesService.getById(partyId).getBirthNumber());
				removalReasonsDTO.setInsuranceName(insuranceDTO.getName());
				removedContractsService.create(removalReasonsDTO);
				contractsService.delete(partyOwnedContract.getContractId());
			}
			
		}
		
		reasonsDto.setTodaysDate(LocalDate.now());
		PartiesDetailsDTO partyDTO = partiesService.getById(partyId);
		DeletedPartiesDTO deleteDTO = deleterService.createDeleteDTO(reasonsDto, partyDTO);
		deleterService.create(deleteDTO);
		partiesService.delete(partyId);
		sessionStatus.setComplete();
	}

}