package com.danger.insurance.archive.models.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danger.insurance.archive.data.repositories.DeletedInsurancesRepository;
import com.danger.insurance.archive.models.dto.DeleteInsurancesReasonsDTO;
import com.danger.insurance.archive.models.dto.DeletedInsurancesDTO;
import com.danger.insurance.archive.data.entities.DeletedInsurancesEntity;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.dto.mappers.DeletedInsurancesMapper;
import com.danger.insurance.parties.models.exceptions.PartyNotFoundException;

@Service
public class DeletedInsurancesServiceImplementation implements DeletedInsurancesService{

	// Object initialization
	
	@Autowired
	private DeletedInsurancesRepository deletedInsurancesRepository;				// Handles querying operations for the Parties entity
		
	@Autowired
	private DeletedInsurancesMapper deletedInsurancesMapper;						// 
	
	// Start of code

	/**
	 * Creates a new deleted insurance entry in the archive database and returns the ID of the created record.
	 *
	 * @param dto a DTO containing information about the deleted insurance; may include fields such as insurance number, reason for deletion, and metadata for archival.
	 * @return a {@code long} representing the ID of the created deleted insurance entry.
	 */
	@Override
	public long create(DeletedInsurancesDTO dto) {
		DeletedInsurancesEntity newInsurance = deletedInsurancesMapper.toEntity(dto);										// Convert received DTO to party entity
		deletedInsurancesRepository.save(newInsurance);								// Save the received party to the database
		
		return newInsurance.getDeletedInsurancesId();								//
	}

	/**
	 * Retrieves all parties from the database and converts them into DTOs.
	 *
	 * @return a list of DTOs representing all parties in the database.
	 */
	@Override
	public List<DeletedInsurancesDTO> getAll() {
		List<DeletedInsurancesDTO> insurances = new ArrayList<>();	// Initialize list to hold mapped party DTOs
	    Iterable<DeletedInsurancesEntity> fetchedInsurances = deletedInsurancesRepository.findAll();						// Fetch all party entities from the database
	    
	    // Map each entity to a DTO and add it to the result list
	    for (DeletedInsurancesEntity insurancesEntity : fetchedInsurances) {	
	    	DeletedInsurancesDTO mappedInsurance = deletedInsurancesMapper.toDTO(insurancesEntity);						// Convert current entity to a DTO
	    	insurances.add(mappedInsurance);							//  Add converted DTO to the result list
	    }
	   
	    return insurances;											// Return list of all mapped party DTOs
	}
	
	/**
	 * Retrieves a party entity by ID and converts it into a DTO.
	 *
	 * @param partyId the ID of the party to retrieve.
	 * @return a DTO containing personal details of the found party, such as name, surname, birth date, birth number, email, street, and phone number.
	 */
	@Override
	public DeletedInsurancesDTO getById(long partyId) {
		DeletedInsurancesEntity fetchedInsurance = getInsuranceOrThrow(partyId);									// Retrieve party entity or throw exception if not found
	    
	    return deletedInsurancesMapper.toDTO(fetchedInsurance);				// Convert party entity to DTO and return
	}
    
    /**
     * Retrieves a party entity by its ID or throws a {@link PartyNotFoundException} if not found.
     *
     * @param partyId the ID of the party to retrieve.
     * @return the corresponding {@code PartiesEntity} if found.
     * @throws PartyNotFoundException if no party with the given ID exists.
     */
    private DeletedInsurancesEntity getInsuranceOrThrow(long partyId) {
        return deletedInsurancesRepository.findById(partyId).orElseThrow(PartyNotFoundException::new);		// Return party entity or throw exception if not found
    }

	@Override
	public void delete(long insurancesId) {
		DeletedInsurancesEntity fetchedDeletedInsurance = getInsuranceOrThrow(insurancesId);									// Retrieve party entity or throw exception if not found
		deletedInsurancesRepository.delete(fetchedDeletedInsurance);					// Delete the party entity from the database
	}
	
	@Override
	public DeletedInsurancesDTO createDeleteDTO(DeleteInsurancesReasonsDTO reasonsDto, InsurancesDTO insurancesDTO) {
		return deletedInsurancesMapper.mergeToDeleteDTO(reasonsDto, insurancesDTO);
	}

}
