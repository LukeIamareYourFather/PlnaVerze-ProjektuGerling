package com.danger.insurance.insurances.contracts.models.dto;

import java.time.LocalDate;

import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.data.enums.PartyStatus;

public class PartyContractsDTO {

	private Long id;
	
	private PartiesEntity partyEntity;
	
    private ContractsEntity contractEntity;
    
    private PartyStatus contractRole;
    
    private LocalDate todaysDate;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the partyEntity
	 */
	public PartiesEntity getPartyEntity() {
		return partyEntity;
	}

	/**
	 * @param partyEntity the partyEntity to set
	 */
	public void setPartyEntity(PartiesEntity partyEntity) {
		this.partyEntity = partyEntity;
	}

	/**
	 * @return the contractEntity
	 */
	public ContractsEntity getContractEntity() {
		return contractEntity;
	}

	/**
	 * @param contractEntity the contractEntity to set
	 */
	public void setContractEntity(ContractsEntity contractEntity) {
		this.contractEntity = contractEntity;
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
    
    
}