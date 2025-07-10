package com.danger.insurance.models.dto.insurances;

import com.danger.insurance.data.enums.insurances.InsurancesType;

public class InsurancesDTO {

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
	public Boolean getIsAutoRenewalRequired() {
		return isAutoRenewalRequired;
	}
	/**
	 * @param isAutoRenewalRequired the isAutoRenewalRequired to set
	 */
	public void setIsAutoRenewalRequired(Boolean isAutoRenewalRequired) {
		this.isAutoRenewalRequired = isAutoRenewalRequired;
	}

}