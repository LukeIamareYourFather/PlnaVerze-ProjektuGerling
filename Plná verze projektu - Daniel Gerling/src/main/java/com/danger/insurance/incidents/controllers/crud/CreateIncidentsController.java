package com.danger.insurance.incidents.controllers.crud;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.incidents.data.enums.IncidentStatus;
import com.danger.insurance.incidents.models.dto.IncidentsDTO;
import com.danger.insurance.incidents.models.dto.mappers.IncidentsMapper;
import com.danger.insurance.incidents.models.dto.post.IncidentsCreatePostDTO;
import com.danger.insurance.incidents.models.service.CommonSupportServiceIncidents;
import com.danger.insurance.incidents.models.service.IncidentsServiceImplementation;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.validation.groups.OnCreateIncidentAsEmployee;

@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("incidents")
public class CreateIncidentsController {
	
	@Autowired
	private IncidentsServiceImplementation incidentsService;

	@Autowired
	private IncidentsMapper incidentsMapper;
	
	@Autowired
	private CommonSupportServiceIncidents supportServiceIncidents;
	
	@GetMapping("create")
	public String renderCreateIncidentForm(Model model) {
		model.addAttribute("formAction", "create/validate");
		model.addAttribute("formName", "Vytvoření pojistné události");
		model.addAttribute("buttonLabel", "Vytvoř");
		model.addAttribute("isCreateForm", true);
		model.addAttribute("isSearchForm", false);	
		
		if (!model.containsAttribute("formDTO")) {
			model.addAttribute("formDTO", new IncidentsCreatePostDTO());
		}
		
		return "pages/incidents/create";
	}
	
	@PostMapping("create/validate")
	public String validateCreateIncidentFormPost(@Validated(OnCreateIncidentAsEmployee.class) @ModelAttribute("formDTO") IncidentsCreatePostDTO createDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String failRedirect = "incidents/create";
		String successRedirect = failRedirect + "/process";
		
		return supportServiceIncidents.validateAccidentFormPost(createDTO, null, failRedirect, successRedirect, bindingResult, redirectAttributes);
	}
	
	@GetMapping("create/process")
	public String handleCreateIncidentFormPost(@ModelAttribute("formDTO") IncidentsCreatePostDTO createDTO, Model model, RedirectAttributes redirectAttributes) {
		IncidentsDTO newIncident = incidentsMapper.mergeCreatePostDTOToIncidentsDTO(new IncidentsDTO(), createDTO);
		newIncident.setCurrentStatus(IncidentStatus.OPEN);
		newIncident.setTodaysDate(LocalDate.now());
		long incidentId = incidentsService.create(newIncident);
		redirectAttributes.addFlashAttribute("success", FlashMessages.INCIDENT_CREATED.getDisplayName());
		
		return "redirect:/incidents/" + incidentId;
	}
	
}