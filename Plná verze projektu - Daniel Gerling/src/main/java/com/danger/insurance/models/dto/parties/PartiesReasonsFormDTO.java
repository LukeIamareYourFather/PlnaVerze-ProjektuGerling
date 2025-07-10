package com.danger.insurance.models.dto.parties;

import java.io.Serializable;
import java.time.LocalDate;

import com.danger.insurance.data.enums.parties.PartiesRemovalReason;

public class PartiesReasonsFormDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private long deleteReasonId;
	private PartiesRemovalReason removalReason;
	private LocalDate dateOfRequest;
	private LocalDate todaysDate;
	private Boolean ifToDeleteParty;
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
	public Boolean getIfToDeleteParty() {
		return ifToDeleteParty;
	}
	/**
	 * @param ifToDeleteParty the ifToDeleteParty to set
	 */
	public void setIfToDeleteParty(Boolean ifToDeleteParty) {
		this.ifToDeleteParty = ifToDeleteParty;
	}
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