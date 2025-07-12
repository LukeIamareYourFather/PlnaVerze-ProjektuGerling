package com.danger.insurance.models.dto.insurances;

import java.time.LocalDate;

import com.danger.insurance.data.enums.insurances.InsurancesDeleteReason;
import com.danger.insurance.data.enums.insurances.InsurancesType;

public class DeletedInsurancesDTO {
	
	private InsurancesDeleteReason removalReason;
	private LocalDate requestDate;
	private LocalDate todaysDate;
	private String deleteDescription;
	private Boolean ifToRemoveAllContracts;
	
	private long insurancesId;
	private String name;
	private InsurancesType insurancesType;
	private String description;
	private long minimumInsuranceValue;
	private long maximumInsuranceValue;
	private long maximumPayoutValue;
	private Boolean isAnnualPaymentRequired;
	private int renewalPeriod;
	private long minimumPolicyTerm;
	private Boolean isAutoRenewalRequired;
	
	
	
	/**
	 * @return the isAnnualPaymentRequired
	 */
	public Boolean getIsAnnualPaymentRequired() {
		return isAnnualPaymentRequired;
	}
	/**
	 * @param isAnnualPaymentRequired the isAnnualPaymentRequired to set
	 */
	public void setIsAnnualPaymentRequired(Boolean isAnnualPaymentRequired) {
		this.isAnnualPaymentRequired = isAnnualPaymentRequired;
	}
	/**
	 * @return the isAutoRenewalRequired
	 */
	public Boolean getIsAutoRenewalRequired() {
		return isAutoRenewalRequired;
	}
	/**
	 * @param isAutoRenewalRequired the isAutoRenewalRequired to set
	 */
	public void setIsAutoRenewalRequired(Boolean isAutoRenewalRequired) {
		this.isAutoRenewalRequired = isAutoRenewalRequired;
	}
	/**
	 * @return the ifToRemoveAllContracts
	 */
	public Boolean getIfToRemoveAllContracts() {
		return ifToRemoveAllContracts;
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
	 * @return the insurancesId
	 */
	public long getInsurancesId() {
		return insurancesId;
	}
	/**
	 * @param insurancesId the insurancesId to set
	 */
	public void setInsurancesId(long insurancesId) {
		this.insurancesId = insurancesId;
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