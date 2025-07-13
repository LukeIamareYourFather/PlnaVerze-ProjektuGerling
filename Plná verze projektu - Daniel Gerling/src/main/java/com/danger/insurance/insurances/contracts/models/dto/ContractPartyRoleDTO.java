package com.danger.insurance.insurances.contracts.models.dto;

import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.data.enums.PartyStatus;

public class ContractPartyRoleDTO {

	private PartiesEntity partyEntity;
	private PartyStatus contractRole;
	

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
	
	
}