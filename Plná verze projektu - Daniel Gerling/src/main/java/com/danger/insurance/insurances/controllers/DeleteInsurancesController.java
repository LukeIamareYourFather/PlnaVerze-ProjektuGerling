package com.danger.insurance.insurances.controllers;

import java.time.LocalDate;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.DeleteInsurancesReasonsDTO;
import com.danger.insurance.archive.models.dto.DeletedInsurancesDTO;
import com.danger.insurance.archive.models.services.DeletedInsurancesServiceImplementation;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.insurances.models.services.InsurancesServiceImplementation;
import com.danger.insurance.validation.groups.OnDeleteInsurance;

@PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATOR')")
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
	
	@GetMapping("delete")
	public String renderDeleteInsurances(Model model) {
		model.addAttribute("formAction", "delete/validate");
		model.addAttribute("buttonLabel", "Povtrdit");
		model.addAttribute("formName", FormNames.INSURANCES_DELETE.getDisplayName());
		
		if (!model.containsAttribute("insurancesResonsDTO")) {
			model.addAttribute("insurancesResonsDTO", reasonsDto());
		}
		
		return "pages/insurances/delete";
	}
	
	@PostMapping("delete/validate")
	public String validateDeleteInsuranceFormPost(@Validated(OnDeleteInsurance.class) @ModelAttribute("insurancesReasonsDTO") DeleteInsurancesReasonsDTO insurancesReasonsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("insurancesReasonsDTO", insurancesReasonsDTO);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.insurancesReasonsDTO", bindingResult);
			redirectAttributes.addFlashAttribute("error", FlashMessages.INVALID_INPUT.getDisplayName());
			
			return "redirect:/insurances/delete";
		}
		
		if (insurancesReasonsDTO.getRequestDate().isAfter(LocalDate.now())) {
			redirectAttributes.addFlashAttribute("insurancesReasonsDTO", insurancesReasonsDTO);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.insurancesReasonsDTO", bindingResult);
			redirectAttributes.addFlashAttribute("error", FlashMessages.FUTURE_DATE.getDisplayName());
			
			return "redirect:/insurances/delete";
			
		}
		
		return "redirect:/insurances/delete/find";
	}
	
	
	@GetMapping("delete/find")
	public String renderFindInsurancesToDelete(Model model, @ModelAttribute("insurancesReasonsDTO") DeleteInsurancesReasonsDTO insurancesReasonsDTO) {
		model.addAttribute("insurancesList", insurancesService.getAll());
		model.addAttribute("referenceLink", "confirmation/insurance-");
		
		return "pages/insurances/list";
	}
	
	@GetMapping("delete/confirmation/insurance-{insurancesId}")
	public String renderConfirmInsuraceDeletion(@PathVariable("insurancesId") long insurancesId, @ModelAttribute("insurancesReasonsDTO") DeleteInsurancesReasonsDTO insurancesReasonsDTO, Model model) {
		model.addAttribute("ifShowDeleteForm", true);
		model.addAttribute("insurancesReasonsDTO", insurancesReasonsDTO);
		model.addAttribute("insurance", insurancesService.getById(insurancesId));
		model.addAttribute("confirmationLink", "/insurances/delete/insurance-" + insurancesId + "/confirmed");
		
		return "pages/insurances/detail";
	}
	
	@PostMapping("delete/insurance-{insurancesId}/confirmed")
	public String handleDeleteInsuranceConfirmed(@PathVariable("insurancesId") long insurancesId, @ModelAttribute("insurancesReasonsDTO") DeleteInsurancesReasonsDTO insurancesReasonsDTO, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		DeletedInsurancesDTO deletedInsuranceDTO = deleterService.createDeleteDTO(insurancesReasonsDTO, insurancesService.getById(insurancesId));
		deletedInsuranceDTO.setTodaysDate(LocalDate.now());
		System.out.println(deletedInsuranceDTO.getIsAutoRenewalRequired());
		deleterService.create(deletedInsuranceDTO);
		insurancesService.delete(insurancesId);
		sessionStatus.setComplete();
		redirectAttributes.addFlashAttribute("success", FlashMessages.INSURANCE_REMOVED.getDisplayName());
		
		return "redirect:/insurances";
	}
	
}