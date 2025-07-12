package com.danger.insurance.insurances.contracts.data.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.danger.insurance.insurances.data.entities.InsurancesEntity;
import com.danger.insurance.insurances.data.enums.InsurancesSubjects;
import com.danger.insurance.insurances.data.enums.InsurancesType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ContractsEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long contractId;

	@Column(nullable = false)
	private String contractNumber;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private InsurancesSubjects insuredSubject;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private InsurancesType insuranceType;
	
	@Column(nullable = false)
	private LocalDate beginDate;
	
	@Column(nullable = false)
	private LocalDate signatureDate;
	
	@Column(nullable = false)
	private Long pricePerPeriod;
	
	@Column(nullable = false)
	private float liabilityPercentage;
	
	@ManyToOne
	@JoinColumn(name = "insurances_id", nullable = false)
	private InsurancesEntity insurancesEntity;


	@OneToMany(mappedBy = "contractEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyContractsEntity> partyContracts = new ArrayList<>();
	
	
	/**
	 * @return the partyContracts
	 */
	public List<PartyContractsEntity> getPartyContracts() {
		return partyContracts;
	}

	/**
	 * @param partyContracts the partyContracts to set
	 */
	public void setPartyContracts(List<PartyContractsEntity> partyContracts) {
		this.partyContracts = partyContracts;
	}

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