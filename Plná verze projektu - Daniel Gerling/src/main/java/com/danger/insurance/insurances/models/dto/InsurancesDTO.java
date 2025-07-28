package com.danger.insurance.insurances.models.dto;

import com.danger.insurance.insurances.data.enums.InsurancesType;
import com.danger.insurance.validation.groups.OnCreateInsurance;
import com.danger.insurance.validation.groups.OnUpdateInsurance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InsurancesDTO {

	private Long insurancesId;
	
	@NotBlank(message = "Prosím zadejte název pojištění", groups = {OnUpdateInsurance.class, OnCreateInsurance.class})
	private String name;
	

	@NotNull(message = "Prosím zadejte typ pojištění", groups = {OnUpdateInsurance.class, OnCreateInsurance.class})
	private InsurancesType insurancesType;

	@NotBlank(message = "Prosím zadejte popis pojištění", groups = {OnUpdateInsurance.class, OnCreateInsurance.class})
	private String description;
	
	@NotNull(message = "Prosím zadejte minimální pojistnou hodnotu", groups = {OnUpdateInsurance.class, OnCreateInsurance.class})
	private Long minimumInsuranceValue;
	
	@NotNull(message = "Prosím zadejte maximální pojistnou hodnotu", groups = {OnUpdateInsurance.class, OnCreateInsurance.class})
	private Long maximumInsuranceValue;
	
	@NotNull(message = "Prosím zadejte maximální vyplacenou částku", groups = {OnUpdateInsurance.class, OnCreateInsurance.class})
	private Long maximumPayoutValue;
	
	@NotNull(message = "Prosím zadejte frekvenci plateb", groups = {OnUpdateInsurance.class, OnCreateInsurance.class})
	private Boolean isAnnualPaymentRequired;
	
	@NotNull(message = "Prosím zadejte obnovovací období", groups = {OnUpdateInsurance.class, OnCreateInsurance.class})
	private Integer renewalPeriod;

	@NotNull(message = "Prosím zadejte minimální dobu trvání", groups = {OnUpdateInsurance.class, OnCreateInsurance.class})
	private Long minimumPolicyTerm;
	
	@NotNull(message = "Prosím zadejte zda vyžadovat automatické prodloužení", groups = {OnUpdateInsurance.class, OnCreateInsurance.class})
	private Boolean isAutoRenewalRequired;
	

	/**
	 * @return the insurancesId
	 */
	public Long getInsurancesId() {
		return insurancesId;
	}
	/**
	 * @param insurancesId the insurancesId to set
	 */
	public void setInsurancesId(Long insurancesId) {
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
	public Long getMinimumInsuranceValue() {
		return minimumInsuranceValue;
	}
	/**
	 * @param minimumInsuranceValue the minimumInsuranceValue to set
	 */
	public void setMinimumInsuranceValue(Long minimumInsuranceValue) {
		this.minimumInsuranceValue = minimumInsuranceValue;
	}
	/**
	 * @return the maximumInsuranceValue
	 */
	public Long getMaximumInsuranceValue() {
		return maximumInsuranceValue;
	}
	/**
	 * @param maximumInsuranceValue the maximumInsuranceValue to set
	 */
	public void setMaximumInsuranceValue(Long maximumInsuranceValue) {
		this.maximumInsuranceValue = maximumInsuranceValue;
	}
	/**
	 * @return the maximumPayoutValue
	 */
	public Long getMaximumPayoutValue() {
		return maximumPayoutValue;
	}
	/**
	 * @param maximumPayoutValue the maximumPayoutValue to set
	 */
	public void setMaximumPayoutValue(Long maximumPayoutValue) {
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
	public Integer getRenewalPeriod() {
		return renewalPeriod;
	}
	/**
	 * @param renewalPeriod the renewalPeriod to set
	 */
	public void setRenewalPeriod(Integer renewalPeriod) {
		this.renewalPeriod = renewalPeriod;
	}
	/**
	 * @return the minimumPolicyTerm
	 */
	public Long getMinimumPolicyTerm() {
		return minimumPolicyTerm;
	}
	/**
	 * @param minimumPolicyTerm the minimumPolicyTerm to set
	 */
	public void setMinimumPolicyTerm(Long minimumPolicyTerm) {
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