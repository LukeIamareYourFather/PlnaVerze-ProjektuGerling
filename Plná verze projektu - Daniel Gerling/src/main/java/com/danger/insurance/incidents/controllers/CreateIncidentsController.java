package com.danger.insurance.incidents.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.danger.insurance.incidents.models.service.IncidentsServiceImplementation;
import com.danger.insurance.validation.groups.OnCreateIncidentAsEmployee;

@Controller
@RequestMapping("incidents")
public class CreateIncidentsController {
	
	@Autowired
	private IncidentsServiceImplementation incidentsService;

	@Autowired
	private IncidentsMapper incidentsMapper;
	
	@GetMapping("create")
	public String renderCreateIncidentForm(Model model) {
		model.addAttribute("formDTO", new IncidentsCreatePostDTO());
		model.addAttribute("formAction", "create/process");
		model.addAttribute("formName", "Vytvoření pojistné události");
		model.addAttribute("buttonLabel", "Vytvoř");
		model.addAttribute("isCreateForm", true);
		model.addAttribute("isSearchForm", false);	
		
		return "pages/incidents/create";
	}
	
	@PostMapping("create/process")
	public String handleCreateIncidentFormPost(@Validated(OnCreateIncidentAsEmployee.class) @ModelAttribute("formDTO") IncidentsCreatePostDTO createDTO, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//
		if (bindingResult.hasErrors()) {
			model.addAttribute("formDTO", createDTO);
			
			return "pages/incidents/create";
	    }
		
		IncidentsDTO newIncident = incidentsMapper.mergeToIncidentsDTO(new IncidentsDTO(), createDTO);
		newIncident.setCurrentStatus(IncidentStatus.OPEN);
		newIncident.setTodaysDate(LocalDate.now());
		long incidentId = incidentsService.create(newIncident);
		
		return "redirect:/incidents/" + incidentId;
	}
	
}