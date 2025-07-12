package com.danger.insurance.data.entities;

import java.time.LocalDate;

import com.danger.insurance.data.enums.parties.PartyStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PartyContractsEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private PartiesEntity partyEntity;

    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false)
    private ContractsEntity contractEntity;

    @Enumerated(EnumType.STRING)
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