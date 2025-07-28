package com.danger.insurance.incidents.controllers;

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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.incidents.models.dto.IncidentCommentsDTO;
import com.danger.insurance.incidents.models.dto.mappers.IncidentCommentsMapper;
import com.danger.insurance.incidents.models.dto.mappers.IncidentsMapper;
import com.danger.insurance.incidents.models.dto.post.IncidentCommentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsClosePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.incidents.models.service.CommonSupportServiceIncidents;
import com.danger.insurance.incidents.models.service.IncidentCommentsServiceImplementation;
import com.danger.insurance.incidents.models.service.IncidentsServiceImplementation;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.validation.groups.OnProcessIncident;

@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
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
	public String renderProcessIncidentForm(Model model) {
		String formAction ="process/validate";
		boolean isCommentCreateForm = true;
		boolean isClosureForm = false;
		boolean isClosureDetailForm = false;
		IncidentsClosePostDTO incidentCloseDTO = null;
		
		return commonSupportService.addProcessIncidentFormAttributes(incidentCommentsCreatePostDTO(), incidentCloseDTO, model, FormNames.INCIDENTS_PROCESS, ButtonLabels.CONFIRM, formAction, isCommentCreateForm, isClosureForm, isClosureDetailForm);
	}

	@PostMapping("process/validate")
	public String validateProcessIncidentFormPost(@Validated(OnProcessIncident.class) @ModelAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String failRedirect = "incidents/process";
		String sucessRedirect = failRedirect + "/find";
		IncidentsClosePostDTO incidentCloseDTO = null;
		
		return commonSupportService.validateProcessIncidentFormPost(incidentCommentsCreatePostDTO, incidentCloseDTO, failRedirect,  sucessRedirect, bindingResult, redirectAttributes);
	}
	
	@GetMapping("process/find")
	public String renderSearchIncidentToCommentForm(@SessionAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, Model model) {
		return commonSupportService.addFindIncidentsAttributes(model, "Zpracovávání pojistných událostí - dohledání pojistné událostí", "Vyhledej", true, true, "find/validate");
	}
	
	@PostMapping("process/find/validate")
	public String validateSearchIncidentToCommentFormPost(@SessionAttribute("incidentCommentDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO,
			@ModelAttribute("formDTO") IncidentsFindPostDTO incidentsFindPostDTO, Model model, RedirectAttributes redirectAttributes) {
		return commonSupportService.validateFindIncidentsFormPost(model, incidentsFindPostDTO, "redirect:/incidents/process/find/select", "incidents/process/find", redirectAttributes);
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
	public String handleConfirmIncidentCommentForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentCommentsCreateDTO") IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		IncidentCommentsDTO newIncidentComment = incidentCommentsMapper.mergeToIncidentCommentsDTO(new IncidentCommentsDTO(), incidentCommentsCreatePostDTO);
		newIncidentComment.setCommentDate(LocalDate.now());
		newIncidentComment.setIncidentsEntity(incidentsMapper.toEntity(incidentsService.getIncidentById(incidentId)));
		incidentCommentsService.create(newIncidentComment);
		sessionStatus.setComplete();
		redirectAttributes.addFlashAttribute("success", FlashMessages.INCIDENT_PROCESSED.getDisplayName());
		
		return "redirect:/incidents/" + incidentId;
	}
}