package com.danger.insurance.archive.models.services;

import java.util.List;

import com.danger.insurance.insurances.contracts.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

public interface RemovedContractsService {

	/**
     * Creates a new party based on the provided DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing the data to persist
     */
	long create(RemoveContractReasonsDTO entry);
	
	/**
     * Retrieves all parties from the database.
     *
     * @return a list of {@link PartiesDetailsDTO} representing all parties
     */
	List<RemoveContractReasonsDTO> getAll();
	
	/**
     * Retrieves a party by its unique ID.
     *
     * @param insureeId the ID of the party to retrieve
     * @return the corresponding {@link PartiesDetailsDTO}
     */
	RemoveContractReasonsDTO getById(long removedInsurancesId);
	
	/**
     * Updates an existing party with the values from the given DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing updated party data
     */
	void edit(RemoveContractReasonsDTO removeContractReasonsDTO);
	
	/**
     * Deletes the party with the specified ID from the database.
     *
     * @param insureeId the ID of the party to delete
     */
	void delete(long removedInsurancesId);
}