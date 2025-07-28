package com.danger.insurance.insurances.contracts.models.dto;

import java.time.LocalDate;

import com.danger.insurance.insurances.data.entities.InsurancesEntity;
import com.danger.insurance.insurances.data.enums.InsurancesSubjects;
import com.danger.insurance.insurances.data.enums.InsurancesType;
import com.danger.insurance.validation.groups.OnCreateContract;
import com.danger.insurance.validation.groups.OnUpdateContract;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ContractsDTO {
	
	private long contractId;
	
	@NotBlank(message = "Prosím zadejte číslo smlouvy", groups = {OnUpdateContract.class, OnCreateContract.class})
	private String contractNumber;
	
	@NotNull(message = "Prosím zadejte pojištěný subjekt", groups = {OnUpdateContract.class, OnCreateContract.class})
	@Enumerated(EnumType.STRING)
	private InsurancesSubjects insuredSubject;
	
	@NotNull(message = "Prosím zadejte typ pojištění", groups = {OnUpdateContract.class, OnCreateContract.class})
	@Enumerated(EnumType.STRING)
	private InsurancesType insuranceType;
	
	@NotNull(message = "Prosím zadejte datum zahájení", groups = {OnUpdateContract.class, OnCreateContract.class})
	private LocalDate beginDate;
	
	@NotNull(message = "Prosím zadejte datum podpisu", groups = {OnUpdateContract.class, OnCreateContract.class})
	private LocalDate signatureDate;
	
	@NotNull(message = "Prosím zadejte cenu za období", groups = {OnUpdateContract.class, OnCreateContract.class})
	private Long pricePerPeriod;
	
	@NotNull(message = "Prosím zadejte procentualní spoluúčast", groups = {OnUpdateContract.class, OnCreateContract.class})
	private float liabilityPercentage;
	
	private InsurancesEntity insurancesEntity;

	
	/**
	 * @return the contractId
	 */
	public long getContractId() {
		return contractId;
	}

	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	/**
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber the contractNumber to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * @return the insuredSubject
	 */
	public InsurancesSubjects getInsuredSubject() {
		return insuredSubject;
	}

	/**
	 * @param insuredSubject the insuredSubject to set
	 */
	public void setInsuredSubject(InsurancesSubjects insuredSubject) {
		this.insuredSubject = insuredSubject;
	}

	/**
	 * @return the insuranceType
	 */
	public InsurancesType getInsuranceType() {
		return insuranceType;
	}

	/**
	 * @param insuranceType the insuranceType to set
	 */
	public void setInsuranceType(InsurancesType insuranceType) {
		this.insuranceType = insuranceType;
	}

	/**
	 * @return the beginDate
	 */
	public LocalDate getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the signatureDate
	 */
	public LocalDate getSignatureDate() {
		return signatureDate;
	}

	/**
	 * @param signatureDate the signatureDate to set
	 */
	public void setSignatureDate(LocalDate signatureDate) {
		this.signatureDate = signatureDate;
	}

	/**
	 * @return the pricePerPeriod
	 */
	public Long getPricePerPeriod() {
		return pricePerPeriod;
	}

	/**
	 * @param pricePerPeriod the pricePerPeriod to set
	 */
	public void setPricePerPeriod(Long pricePerPeriod) {
		this.pricePerPeriod = pricePerPeriod;
	}

	/**
	 * @return the liabilityPercentage
	 */
	public float getLiabilityPercentage() {
		return liabilityPercentage;
	}

	/**
	 * @param liabilityPercentage the liabilityPercentage to set
	 */
	public void setLiabilityPercentage(float liabilityPercentage) {
		this.liabilityPercentage = liabilityPercentage;
	}

	/**
	 * @return the insurancesEntity
	 */
	public InsurancesEntity getInsurancesEntity() {
		return insurancesEntity;
	}

	/**
	 * @param insurancesEntity the insurancesEntity to set
	 */
	public void setInsurancesEntity(InsurancesEntity insurancesEntity) {
		this.insurancesEntity = insurancesEntity;
	}
	
	
}