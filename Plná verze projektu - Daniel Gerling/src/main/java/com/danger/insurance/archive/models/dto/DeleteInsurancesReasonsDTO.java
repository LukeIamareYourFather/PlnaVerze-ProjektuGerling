package com.danger.insurance.archive.models.dto;

import java.time.LocalDate;

import com.danger.insurance.insurances.contracts.data.enums.InsurancesDeleteReason;
import com.danger.insurance.validation.groups.OnDeleteInsurance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DeleteInsurancesReasonsDTO {

	@NotNull(message = "Prosím zadejte důvod odstranění pojištění", groups = {OnDeleteInsurance.class})
	private InsurancesDeleteReason removalReason;
	
	@NotNull(message = "Prosím zadejte datum vzniku požadavku k odstranění", groups = {OnDeleteInsurance.class})
	private LocalDate requestDate;
	
	@NotBlank(message = "Prosím zadejte popis důvodu odstranění pojištění", groups = {OnDeleteInsurance.class})
	private String deleteDescription;
	
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
	
}