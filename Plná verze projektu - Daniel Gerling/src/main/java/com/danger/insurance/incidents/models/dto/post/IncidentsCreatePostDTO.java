package com.danger.insurance.incidents.models.dto.post;

import java.time.LocalDate;

import com.danger.insurance.incidents.data.enums.IncidentType;
import com.danger.insurance.insurances.data.enums.InsurancesSubjects;
import com.danger.insurance.validation.groups.OnCreateIncidentAsEmployee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class IncidentsCreatePostDTO {
	
	@NotBlank(message = "Prosím zadejte číslo pojistné události", groups = {OnCreateIncidentAsEmployee.class})
	private String caseNumber;

	@NotBlank(message = "Prosím zadejte předmět pojistné události", groups = {OnCreateIncidentAsEmployee.class})
	private String title;

	@NotBlank(message = "Prosím zadejte popis pojistné události", groups = {OnCreateIncidentAsEmployee.class})
	private String description;

	@NotBlank(message = "Prosím zadejte rodné číslo vázané na událost", groups = {OnCreateIncidentAsEmployee.class})
	private String birthNumber;


	@NotNull(message = "Prosím zadejte druh pojistné události", groups = {OnCreateIncidentAsEmployee.class})
	private IncidentType incidentType;

	@NotNull(message = "Prosím zadejte pojistnou událostí poškozený subjekt", groups = {OnCreateIncidentAsEmployee.class})
	private InsurancesSubjects incidentSubject;
	

	@NotNull(message = "Prosím zadejte kdy vznikla pojistná událost", groups = {OnCreateIncidentAsEmployee.class})
	private LocalDate accidentDate;

	@NotNull(message = "Prosím zadejte kdy byla pojistná událost nahlášena", groups = {OnCreateIncidentAsEmployee.class})
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