package com.danger.insurance.archive.data.entities;

import java.time.LocalDate;

import com.danger.insurance.parties.data.enums.PartiesRemovalReason;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DeletedPartiesEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long deleteReasonId;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PartiesRemovalReason removalReason;
	
	@Column(nullable = false)
	private LocalDate dateOfRequest;
	
	@Column(nullable = false)
	private LocalDate todaysDate;
		
	@Column(nullable = true)
	private String additionalInformation;

	@Column(nullable = false)
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

	/**
	 * @return the deleteReasonId
	 */
	public long getDeleteReasonId() {
		return deleteReasonId;
	}

	/**
	 * @param deleteReasonId the deleteReasonId to set
	 */
	public void setDeleteReasonId(long deleteReasonId) {
		this.deleteReasonId = deleteReasonId;
	}

	/**
	 * @return the removalReason
	 */
	public PartiesRemovalReason getRemovalReason() {
		return removalReason;
	}

	/**
	 * @param removalReason the removalReason to set
	 */
	public void setRemovalReason(PartiesRemovalReason removalReason) {
		this.removalReason = removalReason;
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
	 * @return the additionalInformation
	 */
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	/**
	 * @param additionalInformation the additionalInformation to set
	 */
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	/**
	 * @return the partyId
	 */
	public long getPartyId() {
		return partyId;
	}

	/**
	 * @param partyId the partyId to set
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
	
}