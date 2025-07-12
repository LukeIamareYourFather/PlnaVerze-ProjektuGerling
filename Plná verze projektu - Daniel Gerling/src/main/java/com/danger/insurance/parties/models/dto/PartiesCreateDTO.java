package com.danger.insurance.parties.models.dto;

import java.time.LocalDate;

import com.danger.insurance.parties.data.enums.InsuranceType;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.validation.groups.OnCreateInsured;
import com.danger.insurance.validation.groups.OnCreatePolicyOwner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PartiesCreateDTO {

	// Define Enums	
	
	@NotNull(message = "Prosím zadejte typ pojištění", groups = {OnCreatePolicyOwner.class, OnCreateInsured.class})
	private InsuranceType insuranceType;
	private PartyStatus partyStatus;
	
	// Define Dates
	@NotNull(message = "Prosím zadejte den narození", groups = {OnCreatePolicyOwner.class, OnCreateInsured.class})
	private LocalDate birthDay;	
	
	// Define Strings
	@NotBlank(message = "Prosím zadejte jméno", groups = {OnCreatePolicyOwner.class, OnCreateInsured.class})
	private String name;
	
	@NotBlank(message = "Prosím zadejte příjmení", groups = {OnCreatePolicyOwner.class, OnCreateInsured.class})
	private String surname;
	
	@NotBlank(message = "Prosím zadejte rodné číslo", groups = {OnCreatePolicyOwner.class, OnCreateInsured.class})
	private String birthNumber;	
	
	@NotBlank(message = "Prosím zadejte e-mail", groups = {OnCreatePolicyOwner.class, OnCreateInsured.class})
	private String email;	
	
	@NotBlank(message = "Prosím zadejte telefonní číslo", groups = {OnCreatePolicyOwner.class, OnCreateInsured.class})
	private String phoneNumber;	
	
	@NotBlank(message = "Prosím zadejte ulici", groups = {OnCreatePolicyOwner.class, OnCreateInsured.class})
	private String street;	
	
	@NotBlank(message = "Prosím zadejte město", groups = {OnCreatePolicyOwner.class, OnCreateInsured.class})
	private String city;	
	
	@NotBlank(message = "Prosím zadejte číslo smlouvy", groups = {OnCreateInsured.class})
	private String contractNumber;
	
	@NotBlank(message = "Prosím zadejte poštovní směrovací číslo", groups = {OnCreatePolicyOwner.class, OnCreateInsured.class})
	private String zipCode;

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
	
	
}