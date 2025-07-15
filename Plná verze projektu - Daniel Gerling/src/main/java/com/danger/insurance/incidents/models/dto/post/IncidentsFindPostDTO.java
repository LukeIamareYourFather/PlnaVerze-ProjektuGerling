package com.danger.insurance.incidents.models.dto.post;

import java.time.LocalDate;

import com.danger.insurance.incidents.data.enums.IncidentStatus;
import com.danger.insurance.incidents.data.enums.IncidentType;
import com.danger.insurance.insurances.data.enums.InsurancesSubjects;

import jakarta.validation.constraints.NotNull;

public class IncidentsFindPostDTO {
	
	@NotNull(message = "Prosím zadejte číslo případu pojistné události")
	private String caseNumber;
	
	@NotNull(message = "Prosím zadejte předmět pojistné události")
	private String Title;

	@NotNull(message = "Prosím zadejte rodné číslo pojistné události")
	private String birthNumber;


	@NotNull(message = "Prosím zadejte druh pojistné události")
	private IncidentType incidentType;

	@NotNull(message = "Prosím zadejte stranu událostí poškozenou")
	private InsurancesSubjects incidentSubject;

	@NotNull(message = "Prosím zadejte status zpracování pojistné události")
	private IncidentStatus currentStatus;
	

	@NotNull(message = "Prosím zadejte datum pojistné události")
	private LocalDate accidentDate;

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

}