package com.danger.insurance.incidents.models.service.support;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import com.danger.insurance.archive.models.dto.RemoveIncidentReasonsDTO;
import com.danger.insurance.incidents.data.entities.IncidentCommentsEntity;
import com.danger.insurance.incidents.data.repositories.IncidentCommentsRepository;
import com.danger.insurance.incidents.models.dto.post.IncidentCommentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsClosePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.incidents.models.service.IncidentsServiceImplementation;
import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;

@Service
public class IncidentsAssigningServices {
	
	@Autowired
	private IncidentsServiceImplementation incidentsService;
	
	@Autowired
	private IncidentCommentsRepository incidentCommentsRepository;
	
	
	public String addFindIncidentsAttributes(Model model, String formName, ButtonLabels buttonLabel, Boolean isSearchForm, Boolean isCreateForm, String formAction) {
		FormNames customNeed = null;
		assignFormVisualAttributes(customNeed, formName, formAction, buttonLabel, isCreateForm, isSearchForm, model);
		model.addAttribute("formDTO", new IncidentsFindPostDTO());
		
		return "pages/incidents/find";
	}
	
	public String addProcessIncidentFormAttributes(IncidentCommentsCreatePostDTO incidentCommentCreateDTO, IncidentsClosePostDTO incidentCloseDTO, Model model, FormNames formName, ButtonLabels buttonLabel, String formAction, Boolean isCommentCreateForm, Boolean isClosureForm, Boolean isClosureDetail) {
		String noCustomName = null;
		Boolean noIsCreateAttribute = null;
		Boolean noIsSearchAttribute = null;
		assignFormVisualAttributes(formName, noCustomName, formAction, buttonLabel, noIsCreateAttribute, noIsSearchAttribute, model);
		model.addAttribute("isCommentCreateForm", isCommentCreateForm);
		model.addAttribute("isClosureForm", isClosureForm);
		model.addAttribute("isClosureDetail", isClosureDetail);
		
		if (incidentCommentCreateDTO != null) {
			
			if (!model.containsAttribute("incidentCommentDTO")) {
				model.addAttribute("incidentCommentDTO", incidentCommentCreateDTO);
			}
			
		} else {
			
			if (!model.containsAttribute("incidentCommentDTO")) {
				model.addAttribute("incidentCommentDTO", incidentCloseDTO);
			}
		}
		
		return "pages/incidents/process";
	}
	
	public String addSelectIncidentListAttributes(Model model, String referenceLink, String returnedPage) {
		model.addAttribute("referenceLink", referenceLink);
		
		return returnedPage;
	}
	
	public String addConfirmIncidentCommentFormAttributes(@PathVariable("incidentId") long incidentId, IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO, IncidentsClosePostDTO incidentsClosePostDTO,
			Model model, String pageTitle, Boolean isSearchForm, Boolean isCreateForm, Boolean isCommentCreateForm, Boolean isConfirmationForm, Boolean isClosureForm, Boolean isClosureDetail) {
		FormNames customNameNeed = null;
		String customFlagNeed = null;
		String formAction = "selected-" + incidentId + "/confirmed";	
		assignFormVisualAttributes(customNameNeed, customFlagNeed, formAction, ButtonLabels.CONFIRM, isCreateForm, isSearchForm, model);		
		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("formDTO", incidentsService.getDetailsById(incidentId));
		model.addAttribute("isCommentCreateForm", isCommentCreateForm);
		model.addAttribute("isConfirmationForm", isConfirmationForm);
		model.addAttribute("isClosureForm", isClosureForm);
		model.addAttribute("isClosureDetail", isClosureDetail);
		
		//
		if (incidentCommentsCreatePostDTO != null) {
			model.addAttribute("incidentCommentDTO", incidentCommentsCreatePostDTO);			
		} else {
			model.addAttribute("incidentCommentDTO", incidentsClosePostDTO);
		}
		
		return "pages/incidents/confirm-comment";
	}
	
	public String addCreateIncidentFromAttributes(Model model) {
		String noCustomName = null;
		String formAction = "create/validate";
		boolean isCreateForm = true;
		boolean isSearchForm = false;
		
		assignFormVisualAttributes(FormNames.INCIDENTS_CREATE, noCustomName, formAction, ButtonLabels.CREATE, isCreateForm, isSearchForm, model);
		
		if (!model.containsAttribute("formDTO")) {
			model.addAttribute("formDTO", new IncidentsCreatePostDTO());
		}
		
		return "pages/incidents/create";
	}
	
	public String addIncidentDetailsAttributes(long incidentId,  Model model) {
		String noCustomName = null;
		String noFormAction = null;
		ButtonLabels noButtonLabel = null;
		boolean isCreateForm = false;
		boolean isSearchForm = false;	
		assignFormVisualAttributes(FormNames.INCIDENTS_DETAILS, noCustomName, noFormAction, noButtonLabel, isCreateForm, isSearchForm, model);
		model.addAttribute("formDTO", incidentsService.getDetailsById(incidentId));
		List<IncidentCommentsEntity> foundComments = incidentCommentsRepository.findByIncidentsEntity_IncidentId(incidentId).reversed();
		boolean isListEmpty = true;
		
		//
		if (!foundComments.isEmpty()) {
			model.addAttribute("incidentCommentsList", foundComments);
			isListEmpty = false;
		}
		
		model.addAttribute("isListEmpty", isListEmpty);
		
		return "pages/incidents/detail";
	}
	
	
	public String addIncidentToRemoveAttributes(RemoveIncidentReasonsDTO removeIncidentReasonsDTO, Model model) {
		FormNames customNameNeed = null;
		String formName = FormNames.INCIDENTS_REMOVE.getDisplayName() + " - vyplnění údajů";
		String formAction = "delete/validate";
		Boolean noIsCreateAttribute = null;
		Boolean noIsSearchAttribute = null;
		model.addAttribute("isDeleteIncidentCreateForm", true);
		
		assignFormVisualAttributes(customNameNeed, formName, formAction, ButtonLabels.CONFIRM, noIsCreateAttribute, noIsSearchAttribute, model);
		
		if (!model.containsAttribute("incidentRemovalReasonsDTO")) {
			model.addAttribute("incidentRemovalReasonsDTO", removeIncidentReasonsDTO);
		}
		
		return "pages/incidents/remove";
	}
	
	public String addConfirmIncidentToRemoveAttributes(long incidentId, Model model) {
		FormNames customNameNeed = null;
		String cutomFlagNeed = null;
		String formAction = "selected-" + incidentId + "/confirmed";
		boolean isCreateForm = false;
		boolean isSearchForm = false;		
		assignFormVisualAttributes(customNameNeed, cutomFlagNeed, formAction, ButtonLabels.DELETE, isCreateForm, isSearchForm, model);
		model.addAttribute("pageTitle", "Odstranění pojistné události - kontrola zadání");
		model.addAttribute("formDTO", incidentsService.getDetailsById(incidentId));
		model.addAttribute("isDeleteIncidentCreateForm", false);
		
		return "pages/incidents/remove-confirm";
	}
	
	public String addEditIncidentFormAttributes(long incidentId, Model model) {
		String formAction = "edit/validate";
		String noCustomName = null;
		boolean isCreateForm = true;
		boolean isSearchForm = false;
		assignFormVisualAttributes(FormNames.INCIDENTS_UPDATE, noCustomName, formAction, ButtonLabels.CHANGE, isCreateForm, isSearchForm, model);
		model.addAttribute("incidentId", incidentId);
		
		//
		if (!model.containsAttribute("formDTO")) {
			model.addAttribute("formDTO", incidentsService.getDetailsById(incidentId));		// I really should use incidentCreatePostDTO here, but i liked the extra challenge of having to juggle two different DTO in one support method in the proceeding validation step
		}
		
		return "pages/incidents/create";
	}
	
	private void assignFormVisualAttributes(FormNames formName, String customFormName, String formAction, ButtonLabels buttonLabel, Boolean isCreateForm, Boolean isSearchForm, Model model) {
		
		if(formName != null) {
			model.addAttribute("formName", formName.getDisplayName());
		} else if (customFormName != null) {
			model.addAttribute("formName", customFormName);
		}
		
		if(isCreateForm != null && isSearchForm != null) {
			model.addAttribute("isCreateForm", isCreateForm);
			model.addAttribute("isSearchForm", isSearchForm);
		}
		
		if (buttonLabel != null && formAction != null) {
			model.addAttribute("formAction", formAction);
			model.addAttribute("buttonLabel", buttonLabel.getDisplayName());
		}
		
	}
	
}