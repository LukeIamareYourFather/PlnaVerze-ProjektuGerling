package com.danger.insurance.incidents.models.service;

import java.util.List;

import com.danger.insurance.incidents.models.dto.IncidentCommentsDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

/**
 * Service interface for managing party-related operations.
 * <p>
 * Defines the contract for creating, retrieving, updating, and deleting party records.
 * </p>
 */
public interface IncidentCommentsService {
	
	/**
     * Creates a new party based on the provided DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing the data to persist
     */
	long create(IncidentCommentsDTO entry);
	
	/**
     * Retrieves all parties from the database.
     *
     * @return a list of {@link PartiesDetailsDTO} representing all parties
     */
	List<IncidentCommentsDTO> getAll();
	
	/**
     * Retrieves a party by its unique ID.
     *
     * @param insureeId the ID of the party to retrieve
     * @return the corresponding {@link PartiesDetailsDTO}
     */
	IncidentCommentsDTO getById(long incidentCommentId);
	
	/**
     * Updates an existing party with the values from the given DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing updated party data
     */
	void edit(IncidentCommentsDTO incidentComment);
	
	/**
     * Deletes the party with the specified ID from the database.
     *
     * @param insureeId the ID of the party to delete
     */
	void delete(long incidentCommentId);
}