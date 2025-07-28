package com.danger.insurance.parties.models.dto;

import java.time.LocalDate;

import com.danger.insurance.parties.data.enums.PartiesRemovalReason;
import com.danger.insurance.validation.groups.OnDeleteParty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PartiesReasonsFormDTO {

	private long deleteReasonId;
	
	@NotNull(message = "Prosím zadejte důvod odebrání", groups = {OnDeleteParty.class})
	private PartiesRemovalReason removalReason;
	
	@NotNull(message = "Prosím zadejte datum požadavku", groups = {OnDeleteParty.class})
	private LocalDate dateOfRequest;
	
	private LocalDate todaysDate;
	
	@NotBlank(message = "Prosím zadejte dodatečné informace", groups = {OnDeleteParty.class})
	private String additionalInformation;
	
	
	/**
	 * @return the deleteReasonId
	 */
	public long getDeleteReasonId() {
		return deleteReasonId;
	}
	/**
	 * @param deleteReasonId the deleteReasonId to set
	 */
	public void setDeleteReasonId(long deleteReasonId) {
		this.deleteReasonId = deleteReasonId;
	}
	/**
	 * @return the removalReason
	 */
	public PartiesRemovalReason getRemovalReason() {
		return removalReason;
	}
	/**
	 * @param removalReason the removalReason to set
	 */
	public void setRemovalReason(PartiesRemovalReason removalReason) {
		this.removalReason = removalReason;
	}
	/**
	 * @return the dateOfRequest
	 */
	public LocalDate getDateOfRequest() {
		return dateOfRequest;
	}
	/**
	 * @param dateOfRequest the dateOfRequest to set
	 */
	public void setDateOfRequest(LocalDate dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
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
	 * @return the ifToDeleteParty
	 */
	/**
	 * @return the additionalInformation
	 */
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	/**
	 * @param additionalInformation the additionalInformation to set
	 */
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	
	
}