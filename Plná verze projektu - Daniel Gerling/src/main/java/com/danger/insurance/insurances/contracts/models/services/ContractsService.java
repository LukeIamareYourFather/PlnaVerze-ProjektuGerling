package com.danger.insurance.insurances.contracts.models.services;

import java.util.List;

import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

public interface ContractsService {

	/**
     * Creates a new party based on the provided DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing the data to persist
     */
	long create(ContractsDTO party);
	
	/**
     * Retrieves all parties from the database.
     *
     * @return a list of {@link PartiesDetailsDTO} representing all parties
     */
	List<ContractsDTO> getAll();
	
	/**
     * Retrieves a party by its unique ID.
     *
     * @param insureeId the ID of the party to retrieve
     * @return the corresponding {@link PartiesDetailsDTO}
     */
	ContractsDTO getById(long contractsId);
	
	/**
     * Updates an existing party with the values from the given DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing updated party data
     */
	void edit(ContractsDTO insurancesDTO);
	
	/**
     * Deletes the party with the specified ID from the database.
     *
     * @param insureeId the ID of the party to delete
     */
	void delete(long contractsId);
}