package com.danger.insurance.archive.models.services.incidents;

import java.util.List;

import com.danger.insurance.archive.models.dto.RemovedIncidentsDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

public interface RemovedIncidentsService {

	/**
     * Creates a new party based on the provided DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing the data to persist
     */
	long create(RemovedIncidentsDTO entry);
	
	/**
     * Retrieves all parties from the database.
     *
     * @return a list of {@link PartiesDetailsDTO} representing all parties
     */
	List<RemovedIncidentsDTO> getAll();
	
	/**
     * Retrieves a party by its unique ID.
     *
     * @param insureeId the ID of the party to retrieve
     * @return the corresponding {@link PartiesDetailsDTO}
     */
	RemovedIncidentsDTO getById(long removedIncidentId);
	
	/**
     * Updates an existing party with the values from the given DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing updated party data
     */
	void edit(RemovedIncidentsDTO removedIncidentsDTO);
	
	/**
     * Deletes the party with the specified ID from the database.
     *
     * @param insureeId the ID of the party to delete
     */
	void delete(long removedIncidentId);
}