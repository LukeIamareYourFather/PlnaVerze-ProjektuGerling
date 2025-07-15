package com.danger.insurance.incidents.models.dto.post;

import java.time.LocalDate;

import com.danger.insurance.incidents.data.enums.IncidentType;
import com.danger.insurance.insurances.data.enums.InsurancesSubjects;
import com.danger.insurance.validation.groups.OnCreateIncidentAsEmployee;

import jakarta.validation.constraints.NotNull;

public class IncidentsCreatePostDTO {
	
	@NotNull(message = "Prosím zadejte den narození", groups = {OnCreateIncidentAsEmployee.class})
	private String caseNumber;

	@NotNull(message = "Prosím zadejte den narození")
	private String Title;

	@NotNull(message = "Prosím zadejte den narození")
	private String description;

	@NotNull(message = "Prosím zadejte den narození")
	private String birthNumber;


	@NotNull(message = "Prosím zadejte den narození")
	private IncidentType incidentType;

	@NotNull(message = "Prosím zadejte den narození")
	private InsurancesSubjects incidentSubject;
	

	@NotNull(message = "Prosím zadejte den narození")
	private LocalDate accidentDate;

	@NotNull(message = "Prosím zadejte den narození")
	private LocalDate reportDate;

	private LocalDate todaysDate;

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
		return Title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		Title = title;
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

}