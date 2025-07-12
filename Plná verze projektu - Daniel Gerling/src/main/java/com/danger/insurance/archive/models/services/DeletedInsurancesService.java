package com.danger.insurance.archive.models.services;

import java.util.List;

import com.danger.insurance.insurances.models.dto.DeleteInsurancesReasonsDTO;
import com.danger.insurance.insurances.models.dto.DeletedInsurancesDTO;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

public interface DeletedInsurancesService {

	/**
     * Creates a new party based on the provided DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing the data to persist
     */
	long create(DeletedInsurancesDTO party);
	
	/**
     * Retrieves all parties from the database.
     *
     * @return a list of {@link PartiesDetailsDTO} representing all parties
     */
	List<DeletedInsurancesDTO> getAll();
	
	/**
     * Retrieves a party by its unique ID.
     *
     * @param insureeId the ID of the party to retrieve
     * @return the corresponding {@link PartiesDetailsDTO}
     */
	DeletedInsurancesDTO getById(long deletedInsurancesId);
	
	/**
     * Updates an existing party with the values from the given DTO.
     *
     * @param party the {@link PartiesDetailsDTO} containing updated party data
     */
	void edit(DeletedInsurancesDTO reason);
	
	/**
     * Deletes the party with the specified ID from the database.
     *
     * @param insureeId the ID of the party to delete
     */
	void delete(long deletedInsurancesId);
	
	public DeletedInsurancesDTO createDeleteDTO(DeleteInsurancesReasonsDTO reasonsDto, InsurancesDTO insurancesDTO);
}