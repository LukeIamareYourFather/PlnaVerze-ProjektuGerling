package com.danger.insurance.archive.models.dto;

import java.time.LocalDate;

import com.danger.insurance.insurances.contracts.data.enums.InsurancesDeleteReason;

public class DeleteInsurancesReasonsDTO {

	private InsurancesDeleteReason removalReason;
	private LocalDate requestDate;
	private String deleteDescription;
	private Boolean ifToRemoveAllContracts;
	/**
	 * @return the removalReason
	 */
	public InsurancesDeleteReason getRemovalReason() {
		return removalReason;
	}
	/**
	 * @param removalReason the removalReason to set
	 */
	public void setRemovalReason(InsurancesDeleteReason removalReason) {
		this.removalReason = removalReason;
	}
	/**
	 * @return the requestDate
	 */
	public LocalDate getRequestDate() {
		return requestDate;
	}
	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}
	/**
	 * @return the deleteDescription
	 */
	public String getDeleteDescription() {
		return deleteDescription;
	}
	/**
	 * @param deleteDescription the deleteDescription to set
	 */
	public void setDeleteDescription(String deleteDescription) {
		this.deleteDescription = deleteDescription;
	}
	/**
	 * @return the ifToRemoveAllContracts
	 */
	public Boolean getIfToRemoveAllContracts() {
		return ifToRemoveAllContracts;
	}
	/**
	 * @param ifToRemoveAllContracts the ifToRemoveAllContracts to set
	 */
	public void setIfToRemoveAllContracts(Boolean ifToRemoveAllContracts) {
		this.ifToRemoveAllContracts = ifToRemoveAllContracts;
	}
	
	
	
}