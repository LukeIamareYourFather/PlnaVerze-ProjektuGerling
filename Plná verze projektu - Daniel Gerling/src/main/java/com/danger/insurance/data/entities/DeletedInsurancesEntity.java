package com.danger.insurance.data.entities;

import java.time.LocalDate;

import com.danger.insurance.data.enums.insurances.InsurancesDeleteReason;
import com.danger.insurance.data.enums.insurances.InsurancesType;

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
	
	private Boolean isAnnualPaymentRequired;
	
	@Column(nullable = false)
	private int renewalPeriod;
	
	@Column(nullable = false)
	private long minimumPolicyTerm;
	
	@Column(nullable = false)
	private Boolean isAutoRenewalRequired;

	
	
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
	 * @return the deletedInsuranceId
	 */
	public long getDeletedInsurancesId() {
		return deletedInsurancesId;
	}

	/**
	 * @param deletedInsuranceId the deletedInsuranceId to set
	 */
	public void setDeletedInsurancesId(long deletedInsuranceId) {
		this.deletedInsurancesId = deletedInsuranceId;
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
	public Boolean isIfToRemoveAllContracts() {
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

	/**
	 * @return the isAnnualPaymentRequired
	 */
	public Boolean isAnnualPaymentRequired() {
		return isAnnualPaymentRequired;
	}

	/**
	 * @param isAnnualPaymentRequired the isAnnualPaymentRequired to set
	 */
	public void setAnnualPaymentRequired(Boolean isAnnualPaymentRequired) {
		this.isAnnualPaymentRequired = isAnnualPaymentRequired;
	}

	/**
	 * @return the renewalPeriod
	 */
	public int getRenewalPeriod() {
		return renewalPeriod;
	}

	/**
	 * @param renewalPeriod the renewalPeriod to set
	 */
	public void setRenewalPeriod(int renewalPeriod) {
		this.renewalPeriod = renewalPeriod;
	}

	/**
	 * @return the minimumPolicyTerm
	 */
	public long getMinimumPolicyTerm() {
		return minimumPolicyTerm;
	}

	/**
	 * @param minimumPolicyTerm the minimumPolicyTerm to set
	 */
	public void setMinimumPolicyTerm(long minimumPolicyTerm) {
		this.minimumPolicyTerm = minimumPolicyTerm;
	}

	/**
	 * @return the isAutoRenewalRequired
	 */
	public Boolean isAutoRenewalRequired() {
		return isAutoRenewalRequired;
	}

	/**
	 * @param isAutoRenewalRequired the isAutoRenewalRequired to set
	 */
	public void setAutoRenewalRequired(Boolean isAutoRenewalRequired) {
		this.isAutoRenewalRequired = isAutoRenewalRequired;
	}	
	
}