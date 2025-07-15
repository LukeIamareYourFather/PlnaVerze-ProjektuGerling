package com.danger.insurance.incidents.models.dto.get;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.danger.insurance.incidents.data.entities.IncidentCommentsEntity;
import com.danger.insurance.incidents.data.enums.IncidentStatus;
import com.danger.insurance.incidents.data.enums.IncidentType;
import com.danger.insurance.insurances.data.enums.InsurancesSubjects;

public class IncidentDetailsGetDTO {
	
	private String caseNumber;
	private String title;
	private String description;
	private String birthNumber;

	private IncidentType incidentType;
	private InsurancesSubjects incidentSubject;
	private IncidentStatus currentStatus;
	
	private LocalDate accidentDate;
	private LocalDate reportDate;
	private LocalDate todaysDate;
	private LocalDate closureDate;

	private List<IncidentCommentsEntity> comments = new ArrayList<>();

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
	 * @return the comments
	 */
	public List<IncidentCommentsEntity> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<IncidentCommentsEntity> comments) {
		this.comments = comments;
	}

}