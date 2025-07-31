package com.danger.insurance.insurances.contracts.models.services.support;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.data.repositories.PartyContractsRepository;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.services.ContractsServiceImplementation;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesFoundSendDTO;
import com.danger.insurance.parties.models.service.PartiesServiceImplementation;

@Service
public class ContractsAssigningServices {

	@Autowired
	private PartiesServiceImplementation partiesService;
	
	@Autowired
	private InsurancesServiceImplementation insurancesService;
	
	@Autowired 
	private ContractsServiceImplementation contractsService;
	
	@Autowired
	private ContractsSupportServices supportServices;
	
	@Autowired
	private PartyContractsRepository partyContractsRepository;
	
	public String addInsuredSearchFromAttributes(Model model) {
		String formName = FormNames.CONTRACTS_ADD_INSURED.getDisplayName() + " - vyhledání osoby";
		String formAction = "add/validate";
		assignVisualFormAttributes(formName, formAction, ButtonLabels.FIND, model);

		//
		if (!model.containsAttribute("formDTO")) {
			model.addAttribute("formDTO", new PartiesDetailsDTO());
		}
		
		return "pages/parties/find";
	}
	
	public String addSelectListAttributes(String referenceLink, String returnedPage, Model model) {
		model.addAttribute("referenceLink", referenceLink);
		
		return returnedPage;
	}
	
	public String addContractSearchFormAttributes(long partyId, Model model) {
		String formName = FormNames.CONTRACTS_ADD_INSURED.getDisplayName() + " - vyhledání smlouvy";
		String formAction = "party-" + partyId + "/validate";
		assignVisualFormAttributes(formName, formAction, ButtonLabels.FIND, model);
		
		//
		if (!model.containsAttribute("contractDTO")) {
			model.addAttribute("contractDTO", new ContractsDTO());
		}
		
		return "pages/insurances/contracts/find";
	}
	
	public String addConfirmOverviewAttributes(long partyId, long contractId, Model model) {
		model.addAttribute("party", partiesService.getById(partyId));
		model.addAttribute("insurance", insurancesService.getById(contractsService.getById(contractId).getInsurancesEntity().getInsurancesId()));
		model.addAttribute("contract", contractsService.getById(contractId));
		model.addAttribute("buttonLabel", "Potvrdit");
		model.addAttribute("referenceLink", "contract-" + contractId + "/confirmed");
		
		return "pages/insurances/assign-confirm";
	}
	
	public String addFindContractAttributes(Model model) {
		String formAction = "find/validate";
		assignVisualFormAttributes(FormNames.CONTRACTS_FIND.getDisplayName(), formAction, ButtonLabels.FIND, model);
		
		if (!model.containsAttribute("contractDTO")) {
			model.addAttribute("contractDTO", new ContractsDTO());
		}
		
		return "pages/insurances/contracts/find";
	}
	
	public String addInsuranceAssignFormSelectAttributes(ContractsDTO contractDTO, Model model) {
		String formAction =  "create/validate";
		assignVisualFormAttributes(FormNames.CONTRACTS_CREATE.getDisplayName(), formAction, ButtonLabels.CREATE, model);
		model.addAttribute("ifShowCreateForm", true);
		
		if (!model.containsAttribute("contractDTO")) {
			model.addAttribute(contractDTO);
		}
		
		return "pages/insurances/assign";
	}
	
	public String addInsuranceSelectAttributes(ContractsDTO contractDTO, Model model) {
		List<InsurancesDTO> foundInsurances = insurancesService.getAllInsurancesByType(contractDTO.getInsuranceType());
		
		if (foundInsurances.isEmpty()) {
			return "pages/insurances/non-applicable";
		}
		
		model.addAttribute("insurancesList", foundInsurances);
		model.addAttribute("referenceLink", "selected-");
		
		return "pages/insurances/list";
	}
	
	
	public String addFoundPartiesToAssignContractListAttributes(PartiesFoundSendDTO partiesFoundDTO, Model model) {	
		model.addAttribute("referenceLink",  "party-");
		List<PartiesEntity> foundParties = partiesFoundDTO.getFoundParties();
		boolean isPartiesListEmpty = foundParties.isEmpty();
				
		//
		if (!isPartiesListEmpty) {
			model.addAttribute("foundParties", foundParties);	
		}
		
		model.addAttribute("isPartiesListEmpty", isPartiesListEmpty);
		
		return "pages/parties/found-parties";
	}
	
	public String addAssignContractToPartyConfirmationAttributes(ContractsDTO contractDTO, long partyId, long insurancesId, Model model) {
		model.addAttribute("insurance", insurancesService.getById(insurancesId));
		model.addAttribute("contract", contractDTO);
		model.addAttribute("party", partiesService.getById(partyId));
		model.addAttribute("referenceLink", "party-" + partyId + "/confirmed");
		model.addAttribute("buttonLabel", "Potvrdit");
		
		return "pages/insurances/assign-confirm";
	}
	
	public String addAppropriateFormDTOWithAttributes(RemoveContractReasonsDTO removeContractReasonsDTO, String formName, String formAction, ButtonLabels buttonLabel, String returnedPage, Model model) {
		assignVisualFormAttributes(formName, formAction, buttonLabel, model);
		
		//
		if (((removeContractReasonsDTO != null) && !model.containsAttribute("removalReasonsDTO"))) {
			model.addAttribute("removalReasonsDTO", removeContractReasonsDTO);
		}else if (((removeContractReasonsDTO == null) && !model.containsAttribute("formDTO"))) {
			model.addAttribute("formDTO", new PartiesDetailsDTO());
		}
		
		return returnedPage;
	}
	
	public String addSelectContractToRemoveListAttributes(long partyId, Model model) {
		model.addAttribute("party", partiesService.getById(partyId));
		model.addAttribute("partyContracts", partyContractsRepository.findContractsByPartyId(partyId));
		model.addAttribute("contractReferenceLink", "selected-" + partyId + "/confirm-");
		
		return "pages/insurances/contracts/remove-select";
	}
	
	public String addRemoveInsuredFromContractConfirmationAttributes(long partyId, long contractId, Model model) {
		model.addAttribute("pageTitle",FormNames.CONTRACTS_REMOVE_INSURED.getDisplayName() + " - potvrzení odebrání");
		model.addAttribute("party", partiesService.getById(partyId));
		model.addAttribute("contract", contractsService.getById(contractId));
		model.addAttribute("referenceLink", "confirmed-" + contractId);
		model.addAttribute("buttonLabel", ButtonLabels.CONFIRM.getDisplayName());
		
		return "pages/insurances/contracts/remove-confirm";
	}
	
	public String addContractDetailsAttributes(long contractId, Model model) {
		model.addAttribute("contractDetails", contractsService.getById(contractId));
		model.addAttribute("pageTitle", "Přehled smlouvy");
		model.addAttribute("insuredPartiesWithRoles", supportServices.addContractRolesToParties(contractId));
		model.addAttribute("referenceLink", "/parties/profile-");
		model.addAttribute("insurance", insurancesService.getById(contractsService.getById(contractId).getInsurancesEntity().getInsurancesId()));
		model.addAttribute("insuranceReferenceLink", "/insurances/insurance-");
		
		return "pages/insurances/contracts/detail";
	}
	
	public String addEditContractFormAttributes(long contractId, Model model) {
		String formAction = "edit/confirm";
		model.addAttribute("ifShowCreateForm", true);	
		assignVisualFormAttributes(FormNames.CONTRACTS_UPDATE.getDisplayName(), formAction, ButtonLabels.CHANGE, model);
		
		if (!model.containsAttribute("contractDTO")) {
			model.addAttribute("contractDTO", contractsService.getById(contractId));
		}
		
		return "pages/insurances/assign";
	}
	
	public String addRemoveInsurancesFormAttributes(RemoveContractReasonsDTO removeContractReasonsDTO, Model model) {
		String formAction = "terminate/validate";
		model.addAttribute("isConfirmForm", false);
		assignVisualFormAttributes(FormNames.CONTRACTS_CANCELL.getDisplayName(), formAction, ButtonLabels.CONFIRM, model);

		//
		if (!model.containsAttribute("removalReasonsDTO")) {
			model.addAttribute("removalReasonsDTO", removeContractReasonsDTO);
		}
		
		return "pages/insurances/contracts/remove-form";
	}
	
	public String addFindContractToRemoveForm(Model model) {
		String formName = FormNames.CONTRACTS_CANCELL.getDisplayName() + " - Výběr kontraktu";
		String formAction = "find/validate";
		assignVisualFormAttributes(formName, formAction, ButtonLabels.FIND, model);
		
		if (!model.containsAttribute("contractDTO")) {
			model.addAttribute("contractDTO", new ContractsDTO());
		}
		
		return "pages/insurances/contracts/find";
	}
	
	public String addSelectContractListAttributes(ContractsDTO contractsDTO, Model model) {
		List<ContractsEntity> foundContracts = contractsService.findContractId(contractsDTO);
		model.addAttribute("foundContractsList", foundContracts);
		model.addAttribute("referenceLink", "selected-");
		
		return "pages/insurances/contracts/list";
	}
	
	public String addDeleteContractConfirmationAttributes(long contractId, Model model) {
		model.addAttribute("contract", contractsService.getById(contractId));
		model.addAttribute("foundParties", partyContractsRepository.findPartiesByContractId(contractId));
		model.addAttribute("referenceLink", "selected-" + contractId + "/confirmed");
		model.addAttribute("partyReferenceLink", "/parties/profile-");
		model.addAttribute("buttonLabel", ButtonLabels.END.getDisplayName());
		model.addAttribute("isConfirmForm", true);
		
		return "pages/insurances/contracts/delete-confirm";
	}
	
	private void assignVisualFormAttributes(String formName, String formAction, ButtonLabels buttonLabel, Model model) {
		model.addAttribute("formName", formName);
		model.addAttribute("formAction", formAction);
		model.addAttribute("buttonLabel", buttonLabel.getDisplayName());
	}
	
}