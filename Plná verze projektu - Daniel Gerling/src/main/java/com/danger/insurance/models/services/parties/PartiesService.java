package com.danger.insurance.models.services.parties;

import java.util.List;

import com.danger.insurance.models.dto.parties.PartiesCreateDTO;
import com.danger.insurance.models.dto.parties.PartiesDetailsDTO;

/**
 * Service interface for managing party-related operations.
 * <p>
 * Defines the contract for creating, retrieving, updating, and deleting party records.
 * </p>
 */
public interface PartiesService {
	
	/**
     * Creates a new party based on the provided DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing the data to persist
     */
	long create(PartiesCreateDTO party);
	
	/**
     * Retrieves all parties from the database.
     *
     * @return a list of {@link PartiesDetailsDTO} representing all parties
     */
	List<PartiesDetailsDTO> getAll();
	
	/**
     * Retrieves a party by its unique ID.
     *
     * @param insureeId the ID of the party to retrieve
     * @return the corresponding {@link PartiesDetailsDTO}
     */
	PartiesDetailsDTO getById(long insureeId);
	
	/**
     * Updates an existing party with the values from the given DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing updated party data
     */
	void edit(PartiesDetailsDTO party);
	
	/**
     * Deletes the party with the specified ID from the database.
     *
     * @param insureeId the ID of the party to delete
     */
	void delete(long insureeId);
}