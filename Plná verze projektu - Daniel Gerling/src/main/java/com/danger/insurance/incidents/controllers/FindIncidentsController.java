package com.danger.insurance.incidents.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.incidents.models.service.CommonSupportServiceIncidents;

@Controller
@RequestMapping("incidents")
public class FindIncidentsController {
	
	@Autowired
	private CommonSupportServiceIncidents commonSupportService;
	
	@GetMapping("find")
	public String renderSearchIncidentForm(Model model) {
		return commonSupportService.addFindIncidentsAttributes(model, "Vyhledávání pojistných událostí", "Vyhledej", true, true, "find/validate", "pages/incidents/find");
	}
	
	@PostMapping("find/validate")
	public String validateSearchIncidentForm(@ModelAttribute("formDTO") IncidentsFindPostDTO incidentsFindPostDTO, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return commonSupportService.validateFindIncidentsFormPost(model, incidentsFindPostDTO, "pages/incidents/find", "redirect:/incidents/find/select", bindingResult, redirectAttributes);
	}
	
	@GetMapping("find/select")
	public String renderSelectIncidentList(Model model) {
		return commonSupportService.addSelectIncidentListAttributes(model, "/incidents/", "pages/incidents/list");
	}
	
}