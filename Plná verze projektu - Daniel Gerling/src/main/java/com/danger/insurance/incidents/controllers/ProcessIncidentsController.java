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
import org.springframework.web.bind.support.SessionStatus;
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
		return commonSupportService.addProcessIncidentFormAttributes(model, "Zpracovávání pojistných událostí", "Potvrdit", "process/validate", true, false, false);
	}

	@PostMapping("process/validate")
	public String validateProcessIncidentFormPost(@ModelAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model, BindingResult bindingResult) {
		return commonSupportService.validateProcessIncidentFormPost(model, bindingResult, incidentCommentsCreatePostDTO, null, "incidents/process/find");
	}
	
	@GetMapping("process/find")
	public String renderSearchIncidentToCommentForm(@SessionAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model) {
		return commonSupportService.addFindIncidentsAttributes(model, "Zpracovávání pojistných událostí - dohledání pojistné událostí", "Vyhledej", true, true, "find/validate");
	}
	
	@PostMapping("process/find/validate")
	public String validateSearchIncidentToCommentFormPost(@SessionAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO,
			@ModelAttribute("formDTO") IncidentsFindPostDTO incidentsFindPostDTO, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return commonSupportService.validateFindIncidentsFormPost(model, incidentsFindPostDTO, "redirect:/incidents/process/find/select", bindingResult, redirectAttributes);
	}
	
	@GetMapping("process/find/select") 
	public String renderSelectIncidentToCommentList(@ModelAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model) {
		return commonSupportService.addSelectIncidentListAttributes(model, "selected-", "pages/incidents/list");
	}
	
	@GetMapping("process/find/selected-{incidentId}")
	public String renderConfirmIncidentCommentForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model) {
		return commonSupportService.addConfirmIncidentCommentFormAttributes(incidentId, incidentCommentsCreatePostDTO, null, model, "Zpracovávání pojistných událostí - potvrzení kroku zpracování",
				false, false, false, true, false, false);
	}
	
	@PostMapping("process/find/selected-{incidentId}/confirmed")
	public String handleConfirmIncidentCommentForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentCommentsCreateDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, SessionStatus sessionStatus) {
		IncidentCommentsDTO newIncidentComment = incidentCommentsMapper.mergeToIncidentCommentsDTO(new IncidentCommentsDTO(), incidentCommentsCreatePostDTO);
		newIncidentComment.setCommentDate(LocalDate.now());
		newIncidentComment.setIncidentsEntity(incidentsMapper.toEntity(incidentsService.getIncidentById(incidentId)));
		incidentCommentsService.create(newIncidentComment);
		sessionStatus.setComplete();
		
		return "redirect:/incidents/" + incidentId;
	}
}