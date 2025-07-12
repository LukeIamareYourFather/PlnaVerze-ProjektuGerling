package com.danger.insurance.controllers.insurances;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.danger.insurance.models.dto.insurances.DeleteInsurancesReasonsDTO;
import com.danger.insurance.models.dto.insurances.DeletedInsurancesDTO;
import com.danger.insurance.models.services.insurances.DeletedInsurancesServiceImplementation;
import com.danger.insurance.models.services.insurances.InsurancesServiceImplementation;

@Controller
@RequestMapping("insurances")
@SessionAttributes("insurancesReasonsDTO")
public class DeleteInsurancesController {

	@Autowired
	private InsurancesServiceImplementation insurancesService;
	
	@Autowired
	private DeletedInsurancesServiceImplementation deleterService;
	
	@ModelAttribute("insurancesReasonsDTO")
	public DeleteInsurancesReasonsDTO reasonsDto() {
	    return new DeleteInsurancesReasonsDTO(); 											// Only called once, when session starts
	}
	
	@RequestMapping("delete")
	public String renderDeleteInsurances(@ModelAttribute("insurancesReasonsDTO") DeleteInsurancesReasonsDTO insurancesReasonsDTO, Model model) {
		model.addAttribute("formAction", "delete/find");
		model.addAttribute("buttonLabel", "Povtrdit");
		
		return "pages/insurances/delete";
	}
	
	@PostMapping("delete/find")
	public String renderFindInsurancesToDelete(Model model, @ModelAttribute("insurancesReasonsDTO") DeleteInsurancesReasonsDTO insurancesReasonsDTO) {
		model.addAttribute("insurancesList", insurancesService.getAll());
		model.addAttribute("referenceLink", "confirmation/insurance-");
		
		return "pages/insurances/list";
	}
	
	@RequestMapping("delete/confirmation/insurance-{insurancesId}")
	public String renderConfirmInsuraceDeletion(@PathVariable("insurancesId") long insurancesId, @ModelAttribute("insurancesReasonsDTO") DeleteInsurancesReasonsDTO insurancesReasonsDTO, Model model) {
		model.addAttribute("ifShowDeleteForm", true);
		model.addAttribute("insurancesReasonsDTO", insurancesReasonsDTO);
		model.addAttribute("insurance", insurancesService.getById(insurancesId));
		model.addAttribute("confirmationLink", "/insurances/delete/insurance-" + insurancesId + "/confirmed");
		
		return "pages/insurances/detail";
	}
	
	@PostMapping("delete/insurance-{insurancesId}/confirmed")
	public String handleDeleteInsuranceConfirmed(@PathVariable("insurancesId") long insurancesId, @ModelAttribute("insurancesReasonsDTO") DeleteInsurancesReasonsDTO insurancesReasonsDTO, SessionStatus sessionStatus) {
		DeletedInsurancesDTO deletedInsuranceDTO = deleterService.createDeleteDTO(insurancesReasonsDTO, insurancesService.getById(insurancesId));
		deletedInsuranceDTO.setTodaysDate(LocalDate.now());
		System.out.println(deletedInsuranceDTO.getIsAutoRenewalRequired());
		deleterService.create(deletedInsuranceDTO);
		insurancesService.delete(insurancesId);
		sessionStatus.setComplete();
		
		return "redirect:/insurances";
	}
	
}