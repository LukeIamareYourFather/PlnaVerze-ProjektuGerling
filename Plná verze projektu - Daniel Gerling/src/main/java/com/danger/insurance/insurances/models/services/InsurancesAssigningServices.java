package com.danger.insurance.insurances.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.danger.insurance.archive.models.dto.DeleteInsurancesReasonsDTO;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;

@Service
public class InsurancesAssigningServices {

	@Autowired
	private InsurancesServiceImplementation insurancesService;
		
	public String addCreateInsurancesFromAttributes(Model model) {
		String formAction = "make/validate";
		assignVisualFormAttributes(FormNames.INSURANCES_CREATE, formAction, ButtonLabels.CREATE, model);
		
		//
		if (!model.containsAttribute("insurancesDTO")) {
			model.addAttribute("insurancesDTO", new InsurancesDTO());
		}
		
		return "pages/insurances/create";
	}
	
	public String addDeleteInsurancesFormAttributes(DeleteInsurancesReasonsDTO deleteInsurancesReasonsDTO, Model model) {
		String formAction = "delete/validate";
		assignVisualFormAttributes(FormNames.INSURANCES_DELETE, formAction, ButtonLabels.CONFIRM, model);
		
		if (!model.containsAttribute("insurancesResonsDTO")) {
			model.addAttribute("insurancesResonsDTO", deleteInsurancesReasonsDTO);
		}
		
		return "pages/insurances/delete";
	}
	
	public String addInsurancesListWithAttributes(String referenceLink, Model model) {
		model.addAttribute("referenceLink", referenceLink);
		model.addAttribute("insurancesList", insurancesService.getAll());
		
		return "pages/insurances/list";
	}
	
	public String addConfirmInsuranceDeletionFormAttributes(DeleteInsurancesReasonsDTO deleteInsurancesReasonsDTO, long insurancesId, Model model) {
		model.addAttribute("ifShowDeleteForm", true);
		model.addAttribute("insurancesReasonsDTO", deleteInsurancesReasonsDTO);
		model.addAttribute("insurance", insurancesService.getById(insurancesId));
		model.addAttribute("confirmationLink", "/insurances/delete/insurance-" + insurancesId + "/confirmed");
		
		return "pages/insurances/detail";
	}
	
	public String addInsuranceDetailsAttributes(long insurancesId, Model model) {
		model.addAttribute("insurance", insurancesService.getById(insurancesId));
		model.addAttribute("pageTitle", "Přehled pojištění");
		
		return "pages/insurances/detail";
	}
	
	public String addEditInsuranceFormAttributes(long insurancesId, Model model) {
		String formAction = "edit/confirmed";
		assignVisualFormAttributes(FormNames.INSURANCES_UPDATE, formAction, ButtonLabels.CHANGE, model);
		model.addAttribute("insurancesId", insurancesId);
		
		if (!model.containsAttribute("insurancesDTO")) {
			model.addAttribute("insurancesDTO", insurancesService.getById(insurancesId));
		}
		
		return "pages/insurances/create";
	}
	
	private void assignVisualFormAttributes(FormNames formName, String formAction, ButtonLabels buttonLabel, Model model) {
		model.addAttribute("formName", formName.getDisplayName());
		model.addAttribute("formAction", formAction);
		model.addAttribute("buttonLabel", buttonLabel.getDisplayName());
	}
	
}