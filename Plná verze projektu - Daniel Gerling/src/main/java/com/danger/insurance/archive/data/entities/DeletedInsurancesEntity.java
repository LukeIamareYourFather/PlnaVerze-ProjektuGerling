package com.danger.insurance.archive.data.entities;

import java.time.LocalDate;

import com.danger.insurance.insurances.contracts.data.enums.InsurancesDeleteReason;
import com.danger.insurance.insurances.data.enums.InsurancesType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DeletedInsurancesEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long deletedInsurancesId;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private InsurancesDeleteReason removalReason;
	
	@Column(nullable = false)
	private LocalDate requestDate;
	
	@Column(nullable = false)
	private LocalDate todaysDate;
	
	@Column(nullable = false)
	private String deleteDescription;
	
	@Column(nullable = false)
	private Boolean ifToRemoveAllContracts;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private InsurancesType insurancesType;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private long minimumInsuranceValue;
	
	@Column(nullable = false)
	private long maximumInsuranceValue;
	
	@Column(nullable = false)
	private long maximumPayoutValue;

	/**
	 * @return the deletedInsurancesId
	 */
	public long getDeletedInsurancesId() {
		return deletedInsurancesId;
	}

	/**
	 * @param deletedInsurancesId the deletedInsurancesId to set
	 */
	public void setDeletedInsurancesId(long deletedInsurancesId) {
		this.deletedInsurancesId = deletedInsurancesId;
	}

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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the insurancesType
	 */
	public InsurancesType getInsurancesType() {
		return insurancesType;
	}

	/**
	 * @param insurancesType the insurancesType to set
	 */
	public void setInsurancesType(InsurancesType insurancesType) {
		this.insurancesType = insurancesType;
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
	 * @return the minimumInsuranceValue
	 */
	public long getMinimumInsuranceValue() {
		return minimumInsuranceValue;
	}

	/**
	 * @param minimumInsuranceValue the minimumInsuranceValue to set
	 */
	public void setMinimumInsuranceValue(long minimumInsuranceValue) {
		this.minimumInsuranceValue = minimumInsuranceValue;
	}

	/**
	 * @return the maximumInsuranceValue
	 */
	public long getMaximumInsuranceValue() {
		return maximumInsuranceValue;
	}

	/**
	 * @param maximumInsuranceValue the maximumInsuranceValue to set
	 */
	public void setMaximumInsuranceValue(long maximumInsuranceValue) {
		this.maximumInsuranceValue = maximumInsuranceValue;
	}

	/**
	 * @return the maximumPayoutValue
	 */
	public long getMaximumPayoutValue() {
		return maximumPayoutValue;
	}

	/**
	 * @param maximumPayoutValue the maximumPayoutValue to set
	 */
	public void setMaximumPayoutValue(long maximumPayoutValue) {
		this.maximumPayoutValue = maximumPayoutValue;
	}
	
}