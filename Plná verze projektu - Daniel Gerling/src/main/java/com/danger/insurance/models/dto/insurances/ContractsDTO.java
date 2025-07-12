package com.danger.insurance.models.dto.insurances;

import java.time.LocalDate;

import com.danger.insurance.data.entities.InsurancesEntity;
import com.danger.insurance.data.enums.insurances.InsurancesSubjects;
import com.danger.insurance.data.enums.insurances.InsurancesType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ContractsDTO {
	//make nullable false when using for creation or a new DTO
	private long contractId;
	
	private String contractNumber;
	
	@Enumerated(EnumType.STRING)
	private InsurancesSubjects insuredSubject;
	
	@Enumerated(EnumType.STRING)
	private InsurancesType insuranceType;
	
	private LocalDate beginDate;
	
	private LocalDate signatureDate;
	
	private Long pricePerPeriod;
	
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