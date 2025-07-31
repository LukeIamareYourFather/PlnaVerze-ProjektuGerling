package com.danger.insurance.parties.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.danger.insurance.archive.models.dto.DeletedPartiesDTO;
import com.danger.insurance.archive.models.services.DeletedPartiesServiceImplementation;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesFoundSendDTO;
import com.danger.insurance.parties.models.dto.PartiesReasonsFormDTO;

@Service
public class PartiesAssigningServices {

	@Autowired
	private PartiesServiceImplementation partiesService;
	
	@Autowired
	private DeletedPartiesServiceImplementation deleterService;

	@Autowired
	private PartiesSupportServices supportServices;
	
	public String addFindPartyAttributes(String formAction, FormNames formName, Model model) {
		model.addAttribute("formDTO", new PartiesDetailsDTO());
		assignFormVisualAttributes(formName, formAction, ButtonLabels.FIND, model);
		
		return "pages/parties/find";
	}
	
	//
	public String addCreateFormAttributes(String formAction, Model model) {
		assignFormVisualAttributes(FormNames.PARTY_CREATE, formAction, ButtonLabels.CREATE, model);
		
		//
		if (!model.containsAttribute("formDTO")) {
			model.addAttribute("formDTO", new PartiesCreateDTO());
		}
		
		return "pages/parties/create";
	}
	
	public String addFoundProfilesAttributes(PartiesFoundSendDTO partiesFoundDTO, Model model) {
		model.addAttribute("referenceLink", "/parties/profile-");
		List<PartiesEntity> foundParties = partiesFoundDTO.getFoundParties();							// Retrieve the list of found Parties from DTO
		boolean isPartiesListEmpty = foundParties.isEmpty();
		
		//
		if (!isPartiesListEmpty) {
			model.addAttribute("foundParties", foundParties);	
		}
		
		model.addAttribute("isPartiesListEmpty", isPartiesListEmpty);
		
		return "pages/parties/found-parties";
	}
	
	public String addProfilesListAttributes(long partyId, Model model) {
		PartiesDetailsDTO fetchedParty = partiesService.getById(partyId);						// Retrieve the selected party by ID
		model.addAttribute("party", fetchedParty);												// Add party details to the model for display
		model.addAttribute("activeContractsWithRoles", supportServices.addPartyStatusesToContracts(partyId));
		model.addAttribute("referenceLink", "/insurances/contract-");
		model.addAttribute("editLink", "/parties/profile-" + partyId + "/update");
		model.addAttribute("ifShowDeleteForm", false);
		
		return "pages/parties/profile";	
	}
	
	public String addAllProfilesListAttributes(Model model) {
		List<PartiesDetailsDTO> allParties = partiesService.getAll();
		model.addAttribute("foundParties", allParties);
		model.addAttribute("referenceLink", "profile-");
		
		return "pages/parties/found-parties";
	}
	
	public String addDeletedProfilesListAttributes(Model model) {
		List<DeletedPartiesDTO> allDeletedParties = deleterService.getAll();
		model.addAttribute("foundParties", allDeletedParties);
		
		return "pages/parties/deleted-list";
	}
	
	public String addDeletePolicyOwnerFormAttributes(PartiesReasonsFormDTO partiesReasonsFormDTO, Model model) {
		model.addAttribute("formAction", "/parties/delete/validate");
		
		//
		if (!model.containsAttribute("reasonsDTO")) {
			model.addAttribute("reasonsDTO", partiesReasonsFormDTO);
		}
		
		return "pages/parties/delete-reasons";	
	}
	
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
	public String addSearchPolicyOwnersToDeleteAttributes(String firstKeyName, String secondKeyName, String referenceLink, PartiesDetailsDTO partyDto, PartyStatus partyStatus, Model model, boolean ifFindAssignmentRequested, String formName, String returnedPage) {
		
		//
		if (ifFindAssignmentRequested) {
			model.addAttribute(firstKeyName, new PartiesDetailsDTO());
			model.addAttribute("formName", formName);
		} else {		//
			List<PartiesEntity> foundParties = partiesService.findUserId(partyDto, partyStatus);
			model.addAttribute(firstKeyName, foundParties);
		}
		
		model.addAttribute(secondKeyName, referenceLink);
		model.addAttribute("buttonLabel", ButtonLabels.FIND.getDisplayName());
		
		return returnedPage;
	}
	
	//
	public String addDeleteConfirmationAttributes(long partyId, PartiesReasonsFormDTO reasonsDto, Model model) {
		PartiesDetailsDTO partyDto = partiesService.getById(partyId);
		model.addAttribute("pageTitle", FormNames.PARTY_DELETE.getDisplayName() + " - potvrzení vyžádaného smazání");
		model.addAttribute("ifShowDeleteForm", true);
		model.addAttribute("reasonsDTO", reasonsDto);
		model.addAttribute("party", partyDto);
		model.addAttribute("confirmationLink", "party-" + partyId + "/confirmed");
	
		return "pages/parties/profile";
	}
	
	public String addUpdatePartyFormAttributes(long partyId, Model model) {
		String formAction = "update/confirmed";
		assignFormVisualAttributes(FormNames.PARTY_UPDATE, formAction, ButtonLabels.CHANGE, model);
		
		if (!model.containsAttribute("formDTO")) {
	        model.addAttribute("formDTO", partiesService.getById(partyId));
	    }
		
		return "pages/parties/create";
	}
	
	private void assignFormVisualAttributes(FormNames formName, String formAction, ButtonLabels buttonLabel, Model model) {
		model.addAttribute("formName", formName.getDisplayName());
		model.addAttribute("formAction", formAction);
		model.addAttribute("buttonLabel", buttonLabel.getDisplayName());
	}
	
}