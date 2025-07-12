package com.danger.insurance.insurances.contracts.models.services;

import java.util.List;

import com.danger.insurance.insurances.contracts.models.dto.PartyContractsDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

public interface PartyContractsService {

	/**
     * Creates a new party based on the provided DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing the data to persist
     */
	long create(PartyContractsDTO partyContract);
	
	/**
     * Retrieves all parties from the database.
     *
     * @return a list of {@link PartiesDetailsDTO} representing all parties
     */
	List<PartyContractsDTO> getAll();
	
	/**
     * Retrieves a party by its unique ID.
     *
     * @param insureeId the ID of the party to retrieve
     * @return the corresponding {@link PartiesDetailsDTO}
     */
	PartyContractsDTO getById(long id);
	
	/**
     * Updates an existing party with the values from the given DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing updated party data
     */
	void edit(PartyContractsDTO partyContractsDTO);
	
	/**
     * Deletes the party with the specified ID from the database.
     *
     * @param insureeId the ID of the party to delete
     */
	void delete(long id);
}