package com.danger.insurance.archive.models.services.incidents;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danger.insurance.archive.data.repositories.RemovedIncidentsRepository;
import com.danger.insurance.archive.models.dto.RemovedIncidentsDTO;
import com.danger.insurance.archive.models.dto.mappers.RemovedIncidentsMapper;
import com.danger.insurance.archive.models.exceptions.RemovedIncidentNotFoundException;
import com.danger.insurance.archive.data.entities.RemovedIncidentsEntity;
import com.danger.insurance.parties.models.exceptions.PartyNotFoundException;

@Service
public class RemovedIncidentsServiceImplementation implements RemovedIncidentsService{

	// Object initialization
	
	@Autowired
	private RemovedIncidentsRepository removedIncidentsRepository;				// Handles querying operations for the Parties entity
		
	@Autowired
	private RemovedIncidentsMapper removedIncidentsMapper;
	// Start of code

	/**
	 * Creates a new party entry in the Parties database and returns the party ID of the created party.
	 *
	 * @param dto a DTO containing personal information used to create a new party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 * @return a {@code long} number of the party ID from the created party entry.
	 */
	@Override
	public long create(RemovedIncidentsDTO dto) {
		RemovedIncidentsEntity newInsurance = removedIncidentsMapper.toEntity(dto);										// Convert received DTO to party entity
		removedIncidentsRepository.save(newInsurance);						// Save the received party to the database
		
		return newInsurance.getDeletedIncidentId();
	}

	/**
	 * Retrieves all parties from the database and converts them into DTOs.
	 *
	 * @return a list of DTOs representing all parties in the database.
	 */
	@Override
	public List<RemovedIncidentsDTO> getAll() {
		List<RemovedIncidentsDTO> insurances = new ArrayList<>();	// Initialize list to hold mapped party DTOs
	    Iterable<RemovedIncidentsEntity> fetchedInsurances = removedIncidentsRepository.findAll();						// Fetch all party entities from the database
	    
	    // Map each entity to a DTO and add it to the result list
	    for (RemovedIncidentsEntity insurancesEntity : fetchedInsurances) {	
	    	RemovedIncidentsDTO mappedInsurance = removedIncidentsMapper.toDTO(insurancesEntity);						// Convert current entity to a DTO
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
	public RemovedIncidentsDTO getById(long partyId) {
		RemovedIncidentsEntity fetchedInsurance = getInsuranceOrThrow(partyId);									// Retrieve party entity or throw exception if not found
	    
	    return removedIncidentsMapper.toDTO(fetchedInsurance);				// Convert party entity to DTO and return
	}

	/**
	 * Updates an existing party entity in the database using values from the provided DTO.
	 *
	 * @param dto a DTO containing personal information to update the party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 */
	@Override
    public void edit(RemovedIncidentsDTO dto) {
		RemovedIncidentsEntity fetchedInsurance = getInsuranceOrThrow(dto.getDeletedIncidentId());							// Retrieve party entity or throw exception if not found
		removedIncidentsMapper.updateRemovedIncidentsEntity(dto, fetchedInsurance);										// Apply the DTO values to the party entity
		removedIncidentsRepository.save(fetchedInsurance);					// Save updated party to the database
    }
	
    /**
     * Retrieves a party entity by its ID or throws a {@link PartyNotFoundException} if not found.
     *
     * @param partyId the ID of the party to retrieve.
     * @return the corresponding {@code PartiesEntity} if found.
     * @throws PartyNotFoundException if no party with the given ID exists.
     */
    private RemovedIncidentsEntity getInsuranceOrThrow(long partyId) {
        return removedIncidentsRepository.findById(partyId).orElseThrow(RemovedIncidentNotFoundException::new);		// Return party entity or throw exception if not found
    }

	@Override
	public void delete(long insurancesId) {
		RemovedIncidentsEntity fetchedInsurance = getInsuranceOrThrow(insurancesId);									// Retrieve party entity or throw exception if not found
		removedIncidentsRepository.delete(fetchedInsurance);					// Delete the party entity from the database
	}

}
