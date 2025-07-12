package com.danger.insurance.parties.data.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.danger.insurance.insurances.contracts.data.entities.PartyContractsEntity;
import com.danger.insurance.parties.data.enums.InsuranceType;
import com.danger.insurance.parties.data.enums.PartyStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class PartiesEntity {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long partyId;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String surname;
	
	@Column(nullable = false)
	private LocalDate birthDay;

	@Column(nullable = false)
	private String birthNumber;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String phoneNumber;
	
	@Column(nullable = false)
	private String street;
	
	@Column(nullable = false)
	private String city;
	
	@Column(nullable = false)
	private String zipCode;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PartyStatus partyStatus;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private InsuranceType insuranceType;
	
	@Column(nullable = true)
	private String contractNumber;
		
	@OneToMany(mappedBy = "partyEntity")
    private List<PartyContractsEntity> partyContracts = new ArrayList<>();

	// Getters and Setters Region
	
	
	
	/**
	 * @return the partyId
	 */
	public long getPartyId() {
		return partyId;
	}

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
	 * @param insureeId the partyId to set
	 */
	public void setPartyId(long partyId) {
		this.partyId = partyId;
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
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the birthDay
	 */
	public LocalDate getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the partyStatus
	 */
	public PartyStatus getPartyStatus() {
		return partyStatus;
	}

	/**
	 * @param partyStatus the partyStatus to set
	 */
	public void setPartyStatus(PartyStatus partyStatus) {
		this.partyStatus = partyStatus;
	}

	/**
	 * @return the insuranceType
	 */
	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	/**
	 * @param insuranceType the insuranceType to set
	 */
	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	/**
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber the activeInsuranceContracts to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

}