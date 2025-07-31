package com.danger.insurance.reports.models.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

@Service
public class ReportsService {
	
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
	
	public String addCompanyReportsOverviewAttributes(Model model) {
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
	
	public String addSegmentsReportsOverviewAttributes(Model model) {
		SegmentReportsDTO segmentsReportDTO = new SegmentReportsDTO();
		
		try {
			segmentsReportDTO.setAverageContractValuePerPerson(contractsRepository.getTotalContractValue().divide(BigDecimal.valueOf(partiesRepository.getTotalUserCount()), 2, RoundingMode.HALF_UP));
		} catch (NullPointerException e) {
			segmentsReportDTO.setAverageContractValuePerPerson(BigDecimal.valueOf(0));
		}
		
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