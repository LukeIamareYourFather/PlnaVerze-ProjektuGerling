package com.danger.insurance.reports.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.archive.data.repositories.DeletedInsurancesRepository;
import com.danger.insurance.archive.data.repositories.DeletedPartiesRepository;
import com.danger.insurance.archive.data.repositories.RemovedContractsRepository;
import com.danger.insurance.archive.data.repositories.RemovedIncidentCommentsRepository;
import com.danger.insurance.archive.data.repositories.RemovedIncidentsRepository;
import com.danger.insurance.incidents.data.enums.IncidentStatus;
import com.danger.insurance.incidents.data.enums.IncidentType;
import com.danger.insurance.incidents.data.repositories.IncidentsRepository;
import com.danger.insurance.insurances.contracts.data.repositories.ContractsRepository;
import com.danger.insurance.insurances.contracts.data.repositories.PartyContractsRepository;
import com.danger.insurance.insurances.data.enums.InsurancesSubjects;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.data.repositories.PartiesRepository;
import com.danger.insurance.reports.models.dto.CompanyReportsDTO;
import com.danger.insurance.reports.models.dto.SegmentReportsDTO;

@PreAuthorize("hasRole('ADMINISTRATOR')")
@Controller
@RequestMapping("reports")
public class DisplayReportsController {
	
	@Autowired
	private PartyContractsRepository partyContractsRepository;
	
	@Autowired
	private PartiesRepository partiesRepository;
	
	@Autowired 
	private ContractsRepository contractsRepository;
	
	@Autowired 
	private IncidentsRepository incidentsRepository;
	
	@Autowired
	private DeletedInsurancesRepository deletedInsurancesRepository;
	
	@Autowired
	private DeletedPartiesRepository deletedPartiesRepository;
	
	@Autowired
	private RemovedContractsRepository removedContractsRepository;
	
	@Autowired
	private RemovedIncidentCommentsRepository removedIncidentCommentsRepository;
	
	@Autowired
	private RemovedIncidentsRepository removedIncidentsRepository;

	@GetMapping("select")
	public String renderReportsIndex() {
		return "pages/reports/index";
	}
	
	@GetMapping("company")
	public String renderCompanyReportsOverview(Model model) {
		CompanyReportsDTO companyReportsDTO = new CompanyReportsDTO();
		companyReportsDTO.setPolicyOwnerCount(partyContractsRepository.getTotalUserCount(PartyStatus.POLICY_OWNER));
		companyReportsDTO.setInsuredCount(partyContractsRepository.getTotalUserCount(PartyStatus.INSURED));
		companyReportsDTO.setPersonCount(partiesRepository.getTotalUserCount());
		companyReportsDTO.setActiveContractsCount(contractsRepository.getTotalContractsCount());
		companyReportsDTO.setContractsValue(contractsRepository.getTotalContractValue());
		companyReportsDTO.setIncidentsCount(incidentsRepository.getTotalIncidentsCount());
		companyReportsDTO.setProcessingIncidentsCount(incidentsRepository.getTotalStatusIncidentCount(IncidentStatus.PROCESSING));
		companyReportsDTO.setProcessedIncidentsCount(incidentsRepository.getTotalStatusIncidentCount(IncidentStatus.CLOSED));
		model.addAttribute("companyReportDTO", companyReportsDTO);
		
		return "pages/reports/company";
	}
	
	@GetMapping("segments")
	public String renderSegmentsReportsOverview(Model model) {
		SegmentReportsDTO segmentsReportDTO = new SegmentReportsDTO();
		segmentsReportDTO.setAverageContractValuePerPerson(contractsRepository.getTotalContractValue().divide(BigDecimal.valueOf(partiesRepository.getTotalUserCount()), 2, RoundingMode.HALF_UP));
		segmentsReportDTO.setTotalPersonContracts(partyContractsRepository.getTotalContractsOfType(InsurancesSubjects.PERSON.name()));
		segmentsReportDTO.setTotalPropertyContracts(partyContractsRepository.getTotalContractsOfType(InsurancesSubjects.PROPERTY.name()));
		segmentsReportDTO.setTotalAssetContracts(partyContractsRepository.getTotalContractsOfType(InsurancesSubjects.ASSETS.name()));
		segmentsReportDTO.setTotalLegalContracts(partyContractsRepository.getTotalContractsOfType(InsurancesSubjects.LEGAL.name()));
		segmentsReportDTO.setTotalAnimalContracts(partyContractsRepository.getTotalContractsOfType(InsurancesSubjects.ANIMAL.name()));
		segmentsReportDTO.setTotalDamageIncidents(incidentsRepository.getTotalIncidentTypeCount(IncidentType.DAMAGE));
		segmentsReportDTO.setTotalTheftIncidents(incidentsRepository.getTotalIncidentTypeCount(IncidentType.THEFT));
		segmentsReportDTO.setTotalLostIncidents(incidentsRepository.getTotalIncidentTypeCount(IncidentType.LOST));
		segmentsReportDTO.setTotalElementsIncidents(incidentsRepository.getTotalIncidentTypeCount(IncidentType.ELEMENTS));
		segmentsReportDTO.setTotalInjuryIncidents(incidentsRepository.getTotalIncidentTypeCount(IncidentType.INJURY));
		segmentsReportDTO.setTotalPartiesRemoved(deletedPartiesRepository.getTotalDeletedPartiesCount());
		segmentsReportDTO.setTotalInsurancesRemoved(deletedInsurancesRepository.getTotalDeletedInsurancesCount());
		segmentsReportDTO.setTotalContractsRemoved(removedContractsRepository.getTotalRemovedContractsCount());
		segmentsReportDTO.setTotalIncidentsRemoved(removedIncidentsRepository.getTotalRemovedIncidentsCount());
		segmentsReportDTO.setTotalIncidentCommentsRemoved(removedIncidentCommentsRepository.getTotalRemovedIncidentCommentsCount());
		model.addAttribute("segmentReportDTO", segmentsReportDTO);
		
		return "pages/reports/segments";
	}
}