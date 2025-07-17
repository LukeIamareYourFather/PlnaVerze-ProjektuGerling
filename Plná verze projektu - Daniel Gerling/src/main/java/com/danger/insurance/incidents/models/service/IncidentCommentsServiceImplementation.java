package com.danger.insurance.incidents.models.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danger.insurance.incidents.data.entities.IncidentCommentsEntity;
import com.danger.insurance.incidents.data.repositories.IncidentCommentsRepository;
import com.danger.insurance.incidents.models.dto.IncidentCommentsDTO;
import com.danger.insurance.incidents.models.dto.mappers.IncidentCommentsMapper;
import com.danger.insurance.incidents.models.exceptions.IncidentCommentNotFoundException;
import com.danger.insurance.parties.models.exceptions.PartyNotFoundException;

@Service
public class IncidentCommentsServiceImplementation implements IncidentCommentsService {

	// Object initialization
	
	@Autowired
	private IncidentCommentsRepository incidentCommentsRepository;				// Handles querying operations for the Parties entity
	
	@Autowired
	private IncidentCommentsMapper incidentCommentsMapper;						// Handles mapping between Parties entity and DTOs
	
	// Start of code

	/**
	 * Creates a new party entry in the Parties database and returns the party ID of the created party.
	 *
	 * @param dto a DTO containing personal information used to create a new party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 * @return a {@code long} number of the party ID from the created party entry.
	 */
	@Override
	public long create(IncidentCommentsDTO dto) {
		IncidentCommentsEntity newInsuree = incidentCommentsMapper.newsCreateToEntity(dto);										// Convert received DTO to party entity
		incidentCommentsRepository.save(newInsuree);						// Save the received party to the database
		
		return newInsuree.getIncidentCommentId();
	}

	/**
	 * Retrieves all parties from the database and converts them into DTOs.
	 *
	 * @return a list of DTOs representing all parties in the database.
	 */
	@Override
	public List<IncidentCommentsDTO> getAll() {
		List<IncidentCommentsDTO> parties = new ArrayList<>();	// Initialize list to hold mapped party DTOs
	    Iterable<IncidentCommentsEntity> fetchedParties = incidentCommentsRepository.findAll();						// Fetch all party entities from the database
	    
	    // Map each entity to a DTO and add it to the result list
	    for (IncidentCommentsEntity partyEntity : fetchedParties) {	
	    	IncidentCommentsDTO mappedParty = incidentCommentsMapper.toDTO(partyEntity);						// Convert current entity to a DTO
	        parties.add(mappedParty);							//  Add converted DTO to the result list
	    }
	   
	    return parties;											// Return list of all mapped party DTOs
	}
	
	/**
	 * Retrieves a party entity by ID and converts it into a DTO.
	 *
	 * @param partyId the ID of the party to retrieve.
	 * @return a DTO containing personal details of the found party, such as name, surname, birth date, birth number, email, street, and phone number.
	 */
	@Override
	public IncidentCommentsDTO getById(long partyId) {
		IncidentCommentsEntity fetchedInsuree = getInsureeOrThrow(partyId);									// Retrieve party entity or throw exception if not found
	    
	    return incidentCommentsMapper.toDTO(fetchedInsuree);				// Convert party entity to DTO and return
	}

	/**
	 * Updates an existing party entity in the database using values from the provided DTO.
	 *
	 * @param dto a DTO containing personal information to update the party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 */
	@Override
    public void edit(IncidentCommentsDTO dto) {
		IncidentCommentsEntity fetchedParty = getInsureeOrThrow(dto.getIncidentCommentId());							// Retrieve party entity or throw exception if not found
		incidentCommentsMapper.updateNewsEntity(dto, fetchedParty);										// Apply the DTO values to the party entity
		incidentCommentsRepository.save(fetchedParty);					// Save updated party to the database
    }
    
    /**
     * Retrieves a party entity by its ID or throws a {@link PartyNotFoundException} if not found.
     *
     * @param partyId the ID of the party to retrieve.
     * @return the corresponding {@code PartiesEntity} if found.
     * @throws PartyNotFoundException if no party with the given ID exists.
     */
    private IncidentCommentsEntity getInsureeOrThrow(long partyId) {
        return incidentCommentsRepository.findById(partyId).orElseThrow(IncidentCommentNotFoundException::new);		// Return party entity or throw exception if not found
    }

    /**
     * Deletes a party entity by its ID from the database, or throws a {@link PartyNotFoundException} if not found.
     *
     * @param partyId the ID of the party to delete.
     */
    @Override
	public void delete(long partyId) {
    	IncidentCommentsEntity fetchedParty = getInsureeOrThrow(partyId);									// Retrieve party entity or throw exception if not found
    	incidentCommentsRepository.delete(fetchedParty);					// Delete the party entity from the database
	}
  	
}