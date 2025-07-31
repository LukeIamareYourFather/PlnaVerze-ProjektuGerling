package com.danger.insurance.incidents.models.service.support;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import com.danger.insurance.incidents.data.enums.IncidentStatus;
import com.danger.insurance.incidents.data.repositories.IncidentCommentsRepository;
import com.danger.insurance.incidents.models.dto.IncidentCommentsDTO;
import com.danger.insurance.incidents.models.dto.IncidentsDTO;
import com.danger.insurance.incidents.models.dto.get.IncidentDetailsGetDTO;
import com.danger.insurance.incidents.models.dto.mappers.IncidentCommentsMapper;
import com.danger.insurance.incidents.models.dto.mappers.IncidentsMapper;
import com.danger.insurance.incidents.models.dto.post.IncidentCommentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsClosePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsCreatePostDTO;
import com.danger.insurance.incidents.models.service.IncidentCommentsServiceImplementation;
import com.danger.insurance.incidents.models.service.IncidentsServiceImplementation;
import com.danger.insurance.infopages.data.enums.FlashMessages;

@Service
public class IncidentsProcesingServices {
	
	@Autowired
	private IncidentsServiceImplementation incidentsService;
	
	@Autowired
	private IncidentCommentsServiceImplementation incidentCommentsService;
	
	@Autowired
	private RemovedIncidentsServiceImplementation removedIncidentsService;
	
	@Autowired
	private RemovedIncidentCommentsServiceImplementation removedIncidentCommentsService;;
	
	@Autowired
	private IncidentsMapper incidentsMapper;
	
	@Autowired
	private IncidentCommentsMapper incidentCommentsMapper;
	
	@Autowired
	private RemovedIncidentsMapper removedIncidentsMapper;
	
	@Autowired
	private RemovedIncidentCommentsMapper removedIncidentCommentsMapper;
	
	@Autowired
	private IncidentCommentsRepository incidentCommentsRepository;
	

	public String processConfirmIncidentCloseForm(long incidentId, IncidentsClosePostDTO incidentCloseDTO, SessionStatus sessionStatus) {
		IncidentCommentsDTO newIncidentComment = incidentCommentsMapper.splitToIncidentCommentsDTO(new IncidentCommentsDTO(), incidentCloseDTO);
		newIncidentComment.setCommentDate(LocalDate.now());
		newIncidentComment.setIncidentsEntity(incidentsMapper.toEntity(incidentsService.getIncidentById(incidentId)));
		incidentCommentsService.create(newIncidentComment);
		IncidentsDTO incidentToClose = incidentsService.getIncidentById(incidentId);
		incidentToClose.setClosureDate(LocalDate.now());
		incidentToClose.setIncidentResolution(incidentCloseDTO.getIncidentResolution());
		incidentToClose.setCurrentStatus(IncidentStatus.CLOSED);
		incidentsService.edit(incidentToClose);
		sessionStatus.setComplete();
		
		return "redirect:/incidents/" + incidentId;
	}
	
	public String processConfirmIncidentCommentForm(long incidentId, IncidentCommentsCreatePostDTO incidentCommentsCreatDTO, RedirectAttributes redirectAttributes, SessionStatus sessionStatus) {
		IncidentCommentsDTO newIncidentComment = incidentCommentsMapper.mergeToIncidentCommentsDTO(new IncidentCommentsDTO(), incidentCommentsCreatDTO);
		newIncidentComment.setCommentDate(LocalDate.now());
		newIncidentComment.setIncidentsEntity(incidentsMapper.toEntity(incidentsService.getIncidentById(incidentId)));
		incidentCommentsService.create(newIncidentComment);
		IncidentsDTO incidentToProcess = incidentsService.getIncidentById(incidentId);
		incidentToProcess.setCurrentStatus(IncidentStatus.PROCESSING);
		incidentsService.edit(incidentToProcess);
		sessionStatus.setComplete();
		redirectAttributes.addFlashAttribute("success", FlashMessages.INCIDENT_PROCESSED.getDisplayName());
		
		return "redirect:/incidents/" + incidentId;
	}
	
	public String processCreateIncidentFormPost(IncidentsCreatePostDTO incidentCreateDTO, RedirectAttributes redirectAttributes) {
		IncidentsDTO newIncident = incidentsMapper.mergeCreatePostDTOToIncidentsDTO(new IncidentsDTO(), incidentCreateDTO);
		newIncident.setCurrentStatus(IncidentStatus.OPEN);
		newIncident.setTodaysDate(LocalDate.now());
		long incidentId = incidentsService.create(newIncident);
		redirectAttributes.addFlashAttribute("success", FlashMessages.INCIDENT_CREATED.getDisplayName());
		
		return "redirect:/incidents/" + incidentId;
	}
	
	public String processConfirmIncidentToRemoveFormPost(long incidentId, RemoveIncidentReasonsDTO removeIncidentReasonsDTO, RedirectAttributes redirectAttributes, SessionStatus sessionStatus) {
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
		redirectAttributes.addFlashAttribute("success", FlashMessages.INCIDENT_REMOVED.getDisplayName());
		
		return "redirect:/incidents";
	}
	
	public String processEditIncidentFormPost(long incidentId, IncidentDetailsGetDTO incidentDetailsDTO, RedirectAttributes redirectAttributes) {
		IncidentsDTO originalIncidentDTO = incidentsService.getIncidentById(incidentId);
		IncidentsDTO updatedIncidentDTO = incidentsMapper.mergeDetailsDTOToIncidentsDTO(new IncidentsDTO(), incidentDetailsDTO);
		updatedIncidentDTO.setIncidentId(incidentId);
		updatedIncidentDTO.setCurrentStatus(originalIncidentDTO.getCurrentStatus());
		updatedIncidentDTO.setTodaysDate(originalIncidentDTO.getTodaysDate());
		incidentsService.edit(updatedIncidentDTO);
		redirectAttributes.addFlashAttribute("success", FlashMessages.INCIDENT_UPDATED.getDisplayName());
		
		return "redirect:/incidents/" + incidentId;
	}
	
	
}