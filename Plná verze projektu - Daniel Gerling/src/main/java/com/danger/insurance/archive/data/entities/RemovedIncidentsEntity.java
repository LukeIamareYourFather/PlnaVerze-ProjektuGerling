package com.danger.insurance.archive.data.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.danger.insurance.incidents.data.enums.IncidentRemovalReasons;
import com.danger.insurance.incidents.data.enums.IncidentResolutionsType;
import com.danger.insurance.incidents.data.enums.IncidentStatus;
import com.danger.insurance.incidents.data.enums.IncidentType;
import com.danger.insurance.insurances.data.enums.InsurancesSubjects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class RemovedIncidentsEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long deletedIncidentId;

	@Column(nullable = false)
	private String caseNumber;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private String birthNumber;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private IncidentType incidentType;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private InsurancesSubjects incidentSubject;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private IncidentStatus currentStatus;
	
	@Column(nullable = false)
	private LocalDate accidentDate;
	
	@Column(nullable = false)
	private LocalDate reportDate;
	
	@Column(nullable = false)
	private LocalDate todaysDate;
	
	@Column(nullable = true)
	private LocalDate closureDate;
	
	@Column(nullable = true)
	private IncidentResolutionsType incidentResolution;

	@Column(nullable = false)
	private IncidentRemovalReasons incidentRemovalReason;

	@Column(nullable = false)
	private String removalDescription;
	
	@Column(nullable = false)
	private LocalDate removalDate;
	
	@OneToMany(mappedBy = "removedIncidentEntity", cascade = CascadeType.ALL)
	private List<RemovedIncidentCommentsEntity> deletedComments = new ArrayList<>();

	/**
	 * @return the deletedIncidentId
	 */
	public long getDeletedIncidentId() {
		return deletedIncidentId;
	}

	/**
	 * @param deletedIncidentId the deletedIncidentId to set
	 */
	public void setDeletedIncidentId(long deletedIncidentId) {
		this.deletedIncidentId = deletedIncidentId;
	}

	/**
	 * @return the caseNumber
	 */
	public String getCaseNumber() {
		return caseNumber;
	}

	/**
	 * @param caseNumber the caseNumber to set
	 */
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the birthNumber
	 */
	public String getBirthNumber() {
		return birthNumber;
	}

	/**
	 * @param birthNumber the birthNumber to set
	 */
	public void setBirthNumber(String birthNumber) {
		this.birthNumber = birthNumber;
	}

	/**
	 * @return the incidentType
	 */
	public IncidentType getIncidentType() {
		return incidentType;
	}

	/**
	 * @param incidentType the incidentType to set
	 */
	public void setIncidentType(IncidentType incidentType) {
		this.incidentType = incidentType;
	}

	/**
	 * @return the incidentSubject
	 */
	public InsurancesSubjects getIncidentSubject() {
		return incidentSubject;
	}

	/**
	 * @param incidentSubject the incidentSubject to set
	 */
	public void setIncidentSubject(InsurancesSubjects incidentSubject) {
		this.incidentSubject = incidentSubject;
	}

	/**
	 * @return the currentStatus
	 */
	public IncidentStatus getCurrentStatus() {
		return currentStatus;
	}

	/**
	 * @param currentStatus the currentStatus to set
	 */
	public void setCurrentStatus(IncidentStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

	/**
	 * @return the accidentDate
	 */
	public LocalDate getAccidentDate() {
		return accidentDate;
	}

	/**
	 * @param accidentDate the accidentDate to set
	 */
	public void setAccidentDate(LocalDate accidentDate) {
		this.accidentDate = accidentDate;
	}

	/**
	 * @return the reportDate
	 */
	public LocalDate getReportDate() {
		return reportDate;
	}

	/**
	 * @param reportDate the reportDate to set
	 */
	public void setReportDate(LocalDate reportDate) {
		this.reportDate = reportDate;
	}

	/**
	 * @return the todaysDate
	 */
	public LocalDate getTodaysDate() {
		return todaysDate;
	}

	/**
	 * @param todaysDate the todaysDate to set
	 */
	public void setTodaysDate(LocalDate todaysDate) {
		this.todaysDate = todaysDate;
	}

	/**
	 * @return the closureDate
	 */
	public LocalDate getClosureDate() {
		return closureDate;
	}

	/**
	 * @param closureDate the closureDate to set
	 */
	public void setClosureDate(LocalDate closureDate) {
		this.closureDate = closureDate;
	}

	/**
	 * @return the incidentResolution
	 */
	public IncidentResolutionsType getIncidentResolution() {
		return incidentResolution;
	}

	/**
	 * @param incidentResolution the incidentResolution to set
	 */
	public void setIncidentResolution(IncidentResolutionsType incidentResolution) {
		this.incidentResolution = incidentResolution;
	}

	/**
	 * @return the incidentRemovalReason
	 */
	public IncidentRemovalReasons getIncidentRemovalReason() {
		return incidentRemovalReason;
	}

	/**
	 * @param incidentRemovalReason the incidentRemovalReason to set
	 */
	public void setIncidentRemovalReason(IncidentRemovalReasons incidentRemovalReason) {
		this.incidentRemovalReason = incidentRemovalReason;
	}

	/**
	 * @return the removalDescription
	 */
	public String getRemovalDescription() {
		return removalDescription;
	}

	/**
	 * @param removalDescription the removalDescription to set
	 */
	public void setRemovalDescription(String removalDescription) {
		this.removalDescription = removalDescription;
	}

	/**
	 * @return the removalDate
	 */
	public LocalDate getRemovalDate() {
		return removalDate;
	}

	/**
	 * @param removalDate the removalDate to set
	 */
	public void setRemovalDate(LocalDate removalDate) {
		this.removalDate = removalDate;
	}

	/**
	 * @return the deletedComments
	 */
	public List<RemovedIncidentCommentsEntity> getDeletedComments() {
		return deletedComments;
	}

	/**
	 * @param deletedComments the deletedComments to set
	 */
	public void setDeletedComments(List<RemovedIncidentCommentsEntity> deletedComments) {
		this.deletedComments = deletedComments;
	}
	
}