package com.danger.insurance.insurances.contracts.models.dto;

import com.danger.insurance.insurances.data.enums.InsurancesType;
import com.danger.insurance.parties.data.enums.PartyStatus;

public class PartyContractsProfileDTO {

	private long contractId;
	private String contractNumber;
	private PartyStatus contractRole;
	private InsurancesType insuranceType;
	private String insuranceName;
	
	
	
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
	 * @return the contractRole
	 */
	public PartyStatus getContractRole() {
		return contractRole;
	}
	/**
	 * @param contractRole the contractRole to set
	 */
	public void setContractRole(PartyStatus contractRole) {
		this.contractRole = contractRole;
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
	
	
}