package com.danger.insurance.insurances.data.entities;

import java.util.ArrayList;
import java.util.List;

import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.data.enums.InsurancesType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class InsurancesEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long insurancesId;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private InsurancesType insurancesType;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private Long minimumInsuranceValue;
	
	@Column(nullable = false)
	private Long maximumInsuranceValue;
	
	@Column(nullable = false)
	private Long maximumPayoutValue;
	
	
	private Boolean isAnnualPaymentRequired;
	
	@Column(nullable = false)
	private Integer renewalPeriod;
	
	@Column(nullable = false)
	private Long minimumPolicyTerm;
	
	private Boolean isAutoRenewalRequired;
	
	// One insurance → many contracts
    @OneToMany(mappedBy = "insurancesEntity", cascade = CascadeType.ALL)
    private List<ContractsEntity> contracts = new ArrayList<>();

    
    
	/**
	 * @return the contracts
	 */
	public List<ContractsEntity> getContracts() {
		return contracts;
	}

	/**
	 * @param contracts the contracts to set
	 */
	public void setContracts(List<ContractsEntity> contracts) {
		this.contracts = contracts;
	}

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