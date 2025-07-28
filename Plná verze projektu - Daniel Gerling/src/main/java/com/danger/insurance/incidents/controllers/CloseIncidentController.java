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

import com.danger.insurance.incidents.data.enums.IncidentStatus;
import com.danger.insurance.incidents.models.dto.IncidentCommentsDTO;
import com.danger.insurance.incidents.models.dto.IncidentsDTO;
import com.danger.insurance.incidents.models.dto.mappers.IncidentCommentsMapper;
import com.danger.insurance.incidents.models.dto.mappers.IncidentsMapper;
import com.danger.insurance.incidents.models.dto.post.IncidentCommentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsClosePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.incidents.models.service.CommonSupportServiceIncidents;
import com.danger.insurance.incidents.models.service.IncidentCommentsServiceImplementation;
import com.danger.insurance.incidents.models.service.IncidentsServiceImplementation;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.validation.groups.OnCloseIncident;

@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("incidents")
@SessionAttributes("incidentCommentDTO")
public class CloseIncidentController {
	
	@Autowired
	private CommonSupportServiceIncidents commonSupportService;
	
	@Autowired
	private IncidentsServiceImplementation incidentsService;
	
	@Autowired
	private IncidentCommentsServiceImplementation incidentCommentsService;
	
	@Autowired
	private IncidentsMapper incidentsMapper;
	
	@Autowired
	private IncidentCommentsMapper incidentCommentsMapper;
	
	@ModelAttribute("incidentCommentDTO")
	public IncidentsClosePostDTO incidentsClosePostDTO() {
	    return new IncidentsClosePostDTO(); 											// Only called once, when session starts
	}

	@GetMapping("close")
	public String renderCloseIncidentForm(Model model) {
		String formAction ="close/validate";
		boolean isCommentCreateForm = true;
		boolean isClosureForm = true;
		boolean isClosureDetailForm = false;
		IncidentCommentsCreatePostDTO incidentCommentCreateDTO = null;
		
		return commonSupportService.addProcessIncidentFormAttributes(incidentCommentCreateDTO, incidentsClosePostDTO(), model, FormNames.INCIDENTS_CLOSE, ButtonLabels.CONFIRM, formAction, isCommentCreateForm, isClosureForm, isClosureDetailForm);
	}
	
	@PostMapping("close/validate")
	public String validateCloseIncidentFormPost(@Validated(OnCloseIncident.class) @ModelAttribute("incidentCommentDTO") IncidentsClosePostDTO incidentsClosePostDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String failRedirect = "incidents/close";
		String sucessRedirect = failRedirect + "/find";
		IncidentCommentsCreatePostDTO incidentCommentCreateDTO = null;
		
		return commonSupportService.validateProcessIncidentFormPost(incidentCommentCreateDTO, incidentsClosePostDTO, failRedirect, sucessRedirect, bindingResult, redirectAttributes);
	}
	
	@GetMapping("close/find")
	public String renderSearchIncidentToCloseForm(@SessionAttribute("incidentCommentDTO") IncidentsClosePostDTO incidentsClosePostDTO, Model model) {
		return commonSupportService.addFindIncidentsAttributes(model, "Uzavření pojistné události - dohledání pojistné událostí", "Vyhledej", true, true, "find/validate");
	}
	
	@PostMapping("close/find/validate")
	public String validateSearchIncidentToCloseFormPost(@SessionAttribute("incidentCommentDTO") IncidentsClosePostDTO incidentsClosePostDTO,
			@ModelAttribute("formDTO") IncidentsFindPostDTO incidentsFindPostDTO, Model model, RedirectAttributes redirectAttributes) {
		return commonSupportService.validateFindIncidentsFormPost(model, incidentsFindPostDTO, "redirect:/incidents/close/find/select", "incidents/close/find", redirectAttributes);
	}
	
	@GetMapping("close/find/select") 
	public String renderSelectIncidentToClosetList(@ModelAttribute("incidentCommentDTO") IncidentsClosePostDTO incidentsClosePostDTO, Model model) {
		return commonSupportService.addSelectIncidentListAttributes(model, "selected-", "pages/incidents/list");
	}
	
	@GetMapping("close/find/selected-{incidentId}")
	public String renderConfirmIncidentClosureForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentCommentDTO") IncidentsClosePostDTO incidentsClosePostDTO, Model model) {
		return commonSupportService.addConfirmIncidentCommentFormAttributes(incidentId, null, incidentsClosePostDTO, model, "Uzavření pojistné události - potvrzení kroku zpracování",
				false, false, false, true, true, true);
	}
	
	@PostMapping("close/find/selected-{incidentId}/confirmed")
	public String handleConfirmIncidentCloseForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentCommentDTO") IncidentsClosePostDTO incidentsClosePostDTO, SessionStatus sessionStatus) {
		IncidentCommentsDTO newIncidentComment = incidentCommentsMapper.splitToIncidentCommentsDTO(new IncidentCommentsDTO(), incidentsClosePostDTO);
		newIncidentComment.setCommentDate(LocalDate.now());
		newIncidentComment.setIncidentsEntity(incidentsMapper.toEntity(incidentsService.getIncidentById(incidentId)));
		incidentCommentsService.create(newIncidentComment);
		IncidentsDTO incidentToClose = incidentsService.getIncidentById(incidentId);
		incidentToClose.setClosureDate(LocalDate.now());
		incidentToClose.setIncidentResolution(incidentsClosePostDTO.getIncidentResolution());
		incidentToClose.setCurrentStatus(IncidentStatus.CLOSED);
		incidentsService.edit(incidentToClose);
		sessionStatus.setComplete();
		
		return "redirect:/incidents/" + incidentId;
	}
	
}