package com.danger.insurance.incidents.controllers.crud;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.RemoveIncidentReasonsDTO;
import com.danger.insurance.archive.models.dto.RemovedIncidentCommentsDTO;
import com.danger.insurance.archive.models.dto.RemovedIncidentsDTO;
import com.danger.insurance.archive.models.dto.mappers.RemovedIncidentCommentsMapper;
import com.danger.insurance.archive.models.dto.mappers.RemovedIncidentsMapper;
import com.danger.insurance.archive.models.services.incidents.RemovedIncidentCommentsServiceImplementation;
import com.danger.insurance.archive.models.services.incidents.RemovedIncidentsServiceImplementation;
import com.danger.insurance.incidents.data.entities.IncidentCommentsEntity;
import com.danger.insurance.incidents.data.repositories.IncidentCommentsRepository;
import com.danger.insurance.incidents.models.dto.IncidentCommentsDTO;
import com.danger.insurance.incidents.models.dto.mappers.IncidentCommentsMapper;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.incidents.models.service.CommonSupportServiceIncidents;
import com.danger.insurance.incidents.models.service.IncidentCommentsServiceImplementation;
import com.danger.insurance.incidents.models.service.IncidentsServiceImplementation;

@PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("incidents")
@SessionAttributes("incidentRemovalReasonsDTO")
public class RemoveIncidentsController {
	
	@Autowired
	private CommonSupportServiceIncidents commonSupportServiceIncidents;
	
	@Autowired
	private IncidentsServiceImplementation incidentsService;
	
	@Autowired
	private IncidentCommentsServiceImplementation incidentCommentsService;
	
	@Autowired
	private IncidentCommentsRepository incidentCommentsRepository;
	
	@Autowired
	private RemovedIncidentsServiceImplementation removedIncidentsService;
	
	@Autowired
	private RemovedIncidentCommentsServiceImplementation removedIncidentCommentsService;;

	@Autowired
	private RemovedIncidentsMapper removedIncidentsMapper;
	
	@Autowired
	private IncidentCommentsMapper incidentCommentsMapper;
	
	@Autowired
	private RemovedIncidentCommentsMapper removedIncidentCommentsMapper;
	
	@ModelAttribute("incidentRemovalReasonsDTO")
	public RemoveIncidentReasonsDTO removeIncidentReasonsDTO() {
	    return new RemoveIncidentReasonsDTO(); 											// Only called once, when session starts
	}
	
	@GetMapping("delete")
	public String renderIncidentToRemoveForm(@ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO, Model model) {
		model.addAttribute("formName", "Odstranění pojistné události - vyplnění údajů");
		model.addAttribute("formAction", "delete/validate");
		model.addAttribute("buttonLabel", "Potvrdit");
		model.addAttribute("isDeleteIncidentCreateForm", true);
		
		return "pages/incidents/remove";
	}
	
	@PostMapping("delete/validate")
	public String validateIncidentToRemoveFormPost(@ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO) {
		
		return "redirect:/incidents/delete/find";
	}
	
	@GetMapping("delete/find")
	public String renderFindIncidentToRemoveForm(@ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO, Model model) {
		return commonSupportServiceIncidents.addFindIncidentsAttributes(model, "Odstranění pojistné události - dohledání události", "Vyhledat", true, true, "find/validate");
	}
	
	@PostMapping("delete/find/validate")
	public String validateFindIncidentToRemoveFormPost(@ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO, 
			@ModelAttribute("formDTO") IncidentsFindPostDTO incidentsFindPostDTO , Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return commonSupportServiceIncidents.validateFindIncidentsFormPost(model, incidentsFindPostDTO, "redirect:/incidents/delete/find/select", bindingResult, redirectAttributes);
	}
	
	@GetMapping("delete/find/select") 
	public String renderSelectIncidentToRemoveList(@ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO, Model model) {
		return commonSupportServiceIncidents.addSelectIncidentListAttributes(model, "selected-", "pages/incidents/list");
	}
	
	@GetMapping("delete/find/selected-{incidentId}")
	public String renderConfirmIncidentToRemoveForm(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO, Model model) {
		model.addAttribute("pageTitle", "Odstranění pojistné události - kontrola zadání");
		model.addAttribute("formAction", "selected-" + incidentId + "/confirmed");
		model.addAttribute("buttonLabel", "Odstranit");
		model.addAttribute("formDTO", incidentsService.getDetailsById(incidentId));
		model.addAttribute("isSearchForm", false);
		model.addAttribute("isCreateForm", false);
		model.addAttribute("isDeleteIncidentCreateForm", false);
		
		return "pages/incidents/remove-confirm";
	}
	
	@PostMapping("delete/find/selected-{incidentId}/confirmed")
	public String handleConfirmIncidentToRemoveFormPost(@PathVariable("incidentId") long incidentId, @ModelAttribute("incidentRemovalReasonsDTO") RemoveIncidentReasonsDTO removeIncidentReasonsDTO, SessionStatus sessionStatus) {
		RemovedIncidentsDTO removedIncident = removedIncidentsMapper.mergeToRemovedIncidentsDTO(incidentsService.getIncidentById(incidentId), removeIncidentReasonsDTO);
		removedIncident.setRemovalDate(LocalDate.now());
		long removedIncidentID = removedIncidentsService.create(removedIncident);
		List<IncidentCommentsEntity> removedIncidentComments = incidentCommentsRepository.findByIncidentsEntity_IncidentId(incidentId);
		
		for (IncidentCommentsEntity incidentComment : removedIncidentComments) {
			IncidentCommentsDTO incidentCommentDTO = incidentCommentsMapper.toDTO(incidentComment);
			RemovedIncidentCommentsDTO newEntryDTO = new RemovedIncidentCommentsDTO();
			newEntryDTO = removedIncidentCommentsMapper.mergeToRemovedIncidentCommentsDTO(newEntryDTO, incidentCommentDTO);
			newEntryDTO.setRemovedIncidentEntity(removedIncidentsMapper.toEntity(removedIncidentsService.getById(removedIncidentID)));
			removedIncidentCommentsService.create(newEntryDTO);
			incidentCommentsService.delete(incidentComment.getIncidentCommentId());
		}
		
		incidentsService.delete(incidentId);
		sessionStatus.setComplete();
		
		return "redirect:/incidents";
	}
	
}