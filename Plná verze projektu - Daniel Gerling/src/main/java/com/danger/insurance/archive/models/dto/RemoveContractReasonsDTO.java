package com.danger.insurance.archive.models.dto;

import java.time.LocalDate;

import com.danger.insurance.insurances.contracts.data.enums.ContractsRemovalReason;
import com.danger.insurance.insurances.data.enums.InsurancesSubjects;
import com.danger.insurance.insurances.data.enums.InsurancesType;

import jakarta.validation.constraints.NotNull;

public class RemoveContractReasonsDTO {

	private long removedInsurancesId;
	
	@NotNull(message = "Prosím zadejte důvod ukončení pojištění")
	private  ContractsRemovalReason deleteReason;	

	@NotNull(message = "Prosím zadejte popis důvodu ukončení pojištění")
	private String description;
	
	@NotNull(message = "Prosím zadejte datum požadavku ukončení pojištění")
	private LocalDate dateOfRequest;
	
	@NotNull(message = "Prosím zadejte ke kterému datu ukončit pojištění")
	private LocalDate dateOfCancellation;
	
	private LocalDate todaysDate;
	
	private String birthNumber;
	
	private String contractNumber;
	
	private InsurancesSubjects insuredSubject;
	
	private InsurancesType insuranceType;
	
	private LocalDate beginDate;
	
	private LocalDate signatureDate;
	
	private long pricePerPeriod;
	
	private float liabilityPercentage;
	
	private String insuranceName;
	
	private InsurancesType insurancesType;
	
	
	
	/**
	 * @return the removedInsurancesId
	 */
	public long getRemovedInsurancesId() {
		return removedInsurancesId;
	}

	/**
	 * @param removedInsurancesId the removedInsurancesId to set
	 */
	public void setRemovedInsurancesId(long removedInsurancesId) {
		this.removedInsurancesId = removedInsurancesId;
	}

	/**
	 * @return the deleteReason
	 */
	public ContractsRemovalReason getDeleteReason() {
		return deleteReason;
	}

	/**
	 * @param deleteReason the deleteReason to set
	 */
	public void setDeleteReason(ContractsRemovalReason deleteReason) {
		this.deleteReason = deleteReason;
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
	 * @return the dateOfRequest
	 */
	public LocalDate getDateOfRequest() {
		return dateOfRequest;
	}

	/**
	 * @param dateOfRequest the dateOfRequest to set
	 */
	public void setDateOfRequest(LocalDate dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}

	/**
	 * @return the dateOfCancellation
	 */
	public LocalDate getDateOfCancellation() {
		return dateOfCancellation;
	}

	/**
	 * @param dateOfCancellation the dateOfCancellation to set
	 */
	public void setDateOfCancellation(LocalDate dateOfCancellation) {
		this.dateOfCancellation = dateOfCancellation;
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
	 * @return the birthNumber
	 */
	public String getBirthNumber() {
		return birthNumber;
	}

	/**
	 * @param birthNumber the birthNumber to set
	 */
	public void setBirthNumber(String birthNumber) {
		this.birthNumber = birthNumber;
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
	public long getPricePerPeriod() {
		return pricePerPeriod;
	}

	/**
	 * @param pricePerPeriod the pricePerPeriod to set
	 */
	public void setPricePerPeriod(long pricePerPeriod) {
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
	 * @return the insuranceName
	 */
	public String getInsuranceName() {
		return insuranceName;
	}

	/**
	 * @param insuranceName the insuranceName to set
	 */
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
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
	
	
}