package com.danger.insurance.incidents.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.incidents.models.dto.IncidentCommentsDTO;
import com.danger.insurance.incidents.models.dto.mappers.IncidentCommentsMapper;
import com.danger.insurance.incidents.models.dto.mappers.IncidentsMapper;
import com.danger.insurance.incidents.models.dto.post.IncidentCommentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.incidents.models.service.CommonSupportServiceIncidents;
import com.danger.insurance.incidents.models.service.IncidentCommentsServiceImplementation;
import com.danger.insurance.incidents.models.service.IncidentsServiceImplementation;

@Controller
@RequestMapping("incidents")
@SessionAttributes("incidentCommentDTO")
public class ProcessIncidentsController {
	
	@Autowired
	private IncidentCommentsServiceImplementation incidentCommentsService;
	
	@Autowired
	private IncidentsServiceImplementation incidentsService;
	
	@Autowired
	private CommonSupportServiceIncidents commonSupportService;
	
	@Autowired
	private IncidentCommentsMapper incidentCommentsMapper;
	
	@Autowired
	private IncidentsMapper incidentsMapper;

	@ModelAttribute("incidentCommentDTO")
	public IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO() {
	    return new IncidentCommentsCreatePostDTO(); 											// Only called once, when session starts
	}

	@GetMapping("process")
	public String renderProcessIncidentForm(@ModelAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model) {
		model.addAttribute("formName", "Zpracovávání pojistných událostí");
		model.addAttribute("buttonLabel", "Potvrdit");
		model.addAttribute("formAction", "process/validate");
		model.addAttribute("isCommentCreateForm", true);

		return "pages/incidents/process";
	}

	@PostMapping("process/validate")
	public String validateProcessIncidentFormPost(@ModelAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model, BindingResult bindingResult) {

		//
		if (bindingResult.hasErrors()) {
			model.addAttribute("incidentCommentDTO", incidentCommentsCreatePostDTO);
			
			return "pages/incidents/process";
	    }
		
		return "redirect:/incidents/process/find";
	}
	
	@GetMapping("process/find")
	public String renderSearchIncidentToCommentForm(@ModelAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model) {
		return commonSupportService.addFindIncidentsAttributes(model, "Zpracovávání pojistných událostí - vyhledání pojistné událostí", "Vyhledej", true, true, "find/validate", "pages/incidents/find");
	}
	
	@PostMapping("process/find/validate")
	public String validateSearchIncidentToCommentFormPost(
			@SessionAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO,
			@ModelAttribute("formDTO") IncidentsFindPostDTO incidentsFindPostDTO, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return commonSupportService.validateFindIncidentsFormPost(model, incidentsFindPostDTO, "pages/incidents/find", "redirect:/incidents/process/find/select", bindingResult, redirectAttributes);
	}
	
	@GetMapping("process/find/select") 
	public String renderSelectIncidentToCommentList(@ModelAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model) {
		return commonSupportService.addSelectIncidentListAttributes(model, "selected-", "pages/incidents/list");
	}
	
	@GetMapping("process/find/selected-{incidentId}")
	public String renderConfirmIncidentCommentForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model) {
		model.addAttribute("pageTitle", "Zpracovávání pojistných událostí - potvrzení kroku zpracování");
		model.addAttribute("formAction", "selected-" + incidentId + "/confirmed");
		model.addAttribute("buttonLabel", "Potvrdit");
		model.addAttribute("formDTO", incidentsService.getDetailsById(incidentId));
		model.addAttribute("incidentCommentDTO", incidentCommentsCreatePostDTO);
		model.addAttribute("isSearchForm", false);
		model.addAttribute("isCreateForm", false);
		model.addAttribute("isCommentCreateForm", false);
		model.addAttribute("isConfirmationForm", true);
		
		
		return "pages/incidents/confirm-comment";
	}
	
	@PostMapping("process/find/selected-{incidentId}/confirmed")
	public String handleConfirmIncidentCommentForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentCommentsCreateDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO) {
		IncidentCommentsDTO newIncidentComment = incidentCommentsMapper.mergeToIncidentCommentsDTO(new IncidentCommentsDTO(), incidentCommentsCreatePostDTO);
		newIncidentComment.setCommentDate(LocalDate.now());
		newIncidentComment.setIncidentsEntity(incidentsMapper.toEntity(incidentsService.getIncidentById(incidentId)));
		incidentCommentsService.create(newIncidentComment);
		
		return "redirect:/incidents/" + incidentId;
	}
}