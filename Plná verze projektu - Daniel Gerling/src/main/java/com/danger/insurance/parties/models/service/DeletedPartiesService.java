package com.danger.insurance.parties.models.service;

import java.util.List;

import com.danger.insurance.parties.models.dto.DeletedPartiesDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesReasonsFormDTO;

public interface DeletedPartiesService {

	/**
     * Creates a new party based on the provided DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing the data to persist
     */
	long create(DeletedPartiesDTO party);
	
	/**
     * Retrieves all parties from the database.
     *
     * @return a list of {@link PartiesDetailsDTO} representing all parties
     */
	List<DeletedPartiesDTO> getAll();
	
	/**
     * Retrieves a party by its unique ID.
     *
     * @param insureeId the ID of the party to retrieve
     * @return the corresponding {@link PartiesDetailsDTO}
     */
	DeletedPartiesDTO getById(long reasonId);
	
	/**
     * Updates an existing party with the values from the given DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing updated party data
     */
	void edit(DeletedPartiesDTO reason);
	
	/**
     * Deletes the party with the specified ID from the database.
     *
     * @param insureeId the ID of the party to delete
     */
	void delete(long reasonId);
	
	public DeletedPartiesDTO createDeleteDTO(PartiesReasonsFormDTO reasonsDto, PartiesDetailsDTO partiesDTO);
}