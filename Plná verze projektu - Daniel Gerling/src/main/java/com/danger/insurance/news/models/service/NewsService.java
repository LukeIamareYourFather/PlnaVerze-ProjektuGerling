package com.danger.insurance.news.models.service;

import java.util.List;

import com.danger.insurance.news.models.dto.NewsDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

/**
 * Service interface for managing party-related operations.
 * <p>
 * Defines the contract for creating, retrieving, updating, and deleting party records.
 * </p>
 */
public interface NewsService {
	
	/**
     * Creates a new party based on the provided DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing the data to persist
     */
	long create(NewsDTO entry);
	
	/**
     * Retrieves all parties from the database.
     *
     * @return a list of {@link PartiesDetailsDTO} representing all parties
     */
	List<NewsDTO> getAll();
	
	/**
     * Retrieves a party by its unique ID.
     *
     * @param insureeId the ID of the party to retrieve
     * @return the corresponding {@link PartiesDetailsDTO}
     */
	NewsDTO getById(long newsId);
	
	/**
     * Updates an existing party with the values from the given DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing updated party data
     */
	void edit(NewsDTO news);
	
	/**
     * Deletes the party with the specified ID from the database.
     *
     * @param insureeId the ID of the party to delete
     */
	void delete(long newsId);
}