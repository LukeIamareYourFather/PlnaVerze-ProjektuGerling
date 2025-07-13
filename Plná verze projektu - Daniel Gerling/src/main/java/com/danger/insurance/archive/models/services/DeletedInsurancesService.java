package com.danger.insurance.archive.models.services;

import java.util.List;

import com.danger.insurance.archive.models.dto.DeleteInsurancesReasonsDTO;
import com.danger.insurance.archive.models.dto.DeletedInsurancesDTO;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;

public interface DeletedInsurancesService {

	/**
     * Creates a new deleted insurance entry based on the provided DTO.
     *
     * @param entry the {@link DeletedInsurancesDTO} containing the data to persist
     */
	long create(DeletedInsurancesDTO entry);
	
	/**
     * Retrieves all deleted insurances from the database.
     *
     * @return a list of {@link DeletedInsurancesDTO} representing all deleted insurances
     */
	List<DeletedInsurancesDTO> getAll();
	
	/**
     * Retrieves a deleted insurance entry by its unique ID.
     *
     * @param deletedInsurancesId the ID of the deleted insurance to retrieve
     * @return the corresponding {@link DeletedInsurancesDTO}
     */
	DeletedInsurancesDTO getById(long deletedInsurancesId);
	
	/**
     * Deletes the entry of the deleted insurance with the specified ID from the database.
     *
     * @param deletedInsurancesId the ID of the deleted insurance entry
     */
	void delete(long deletedInsurancesId);
	
	public DeletedInsurancesDTO createDeleteDTO(DeleteInsurancesReasonsDTO reasonsDto, InsurancesDTO insurancesDTO);
}