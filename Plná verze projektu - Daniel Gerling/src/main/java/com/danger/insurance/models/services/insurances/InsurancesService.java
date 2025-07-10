package com.danger.insurance.models.services.insurances;

import java.util.List;

import com.danger.insurance.models.dto.insurances.InsurancesDTO;
import com.danger.insurance.models.dto.parties.PartiesDetailsDTO;

public interface InsurancesService {

	/**
     * Creates a new party based on the provided DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing the data to persist
     */
	long create(InsurancesDTO party);
	
	/**
     * Retrieves all parties from the database.
     *
     * @return a list of {@link PartiesDetailsDTO} representing all parties
     */
	List<InsurancesDTO> getAll();
	
	/**
     * Retrieves a party by its unique ID.
     *
     * @param insureeId the ID of the party to retrieve
     * @return the corresponding {@link PartiesDetailsDTO}
     */
	InsurancesDTO getById(long insurancesId);
	
	/**
     * Updates an existing party with the values from the given DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing updated party data
     */
	void edit(InsurancesDTO insurancesDTO);
	
	/**
     * Deletes the party with the specified ID from the database.
     *
     * @param insureeId the ID of the party to delete
     */
	void delete(long insurancesId);
}