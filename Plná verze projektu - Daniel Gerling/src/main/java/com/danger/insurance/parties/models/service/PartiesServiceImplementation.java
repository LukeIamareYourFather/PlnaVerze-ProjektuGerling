package com.danger.insurance.parties.models.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.data.enums.PartyStatus;
import com.danger.insurance.parties.data.repositories.PartiesRepository;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.mappers.PartiesMapper;
import com.danger.insurance.parties.models.exceptions.PartyNotFoundException;

@Service
public class PartiesServiceImplementation implements PartiesService {

	// Object initialization
	
	@Autowired
	private PartiesRepository partiesRepository;				// Handles querying operations for the Parties entity
	
	@Autowired
	private PartiesMapper partiesMapper;						// Handles mapping between Parties entity and DTOs
	
	// Start of code

	/**
	 * Creates a new party entry in the Parties database and returns the party ID of the created party.
	 *
	 * @param dto a DTO containing personal information used to create a new party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 * @return a {@code long} number of the party ID from the created party entry.
	 */
	@Override
	public long create(PartiesCreateDTO dto) {
		PartiesEntity newInsuree = partiesMapper.partiesCreateToEntity(dto);										// Convert received DTO to party entity
		partiesRepository.save(newInsuree);						// Save the received party to the database
		
		return newInsuree.getPartyId();
	}

	/**
	 * Retrieves all parties from the database and converts them into DTOs.
	 *
	 * @return a list of DTOs representing all parties in the database.
	 */
	@Override
	public List<PartiesDetailsDTO> getAll() {
		List<PartiesDetailsDTO> parties = new ArrayList<>();	// Initialize list to hold mapped party DTOs
	    Iterable<PartiesEntity> fetchedParties = partiesRepository.findAll();						// Fetch all party entities from the database
	    
	    // Map each entity to a DTO and add it to the result list
	    for (PartiesEntity partyEntity : fetchedParties) {	
	        PartiesDetailsDTO mappedParty = partiesMapper.toDTO(partyEntity);						// Convert current entity to a DTO
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
	public PartiesDetailsDTO getById(long partyId) {
	    PartiesEntity fetchedInsuree = getInsureeOrThrow(partyId);									// Retrieve party entity or throw exception if not found
	    
	    return partiesMapper.toDTO(fetchedInsuree);				// Convert party entity to DTO and return
	}

	/**
	 * Updates an existing party entity in the database using values from the provided DTO.
	 *
	 * @param dto a DTO containing personal information to update the party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 */
	@Override
    public void edit(PartiesDetailsDTO dto) {
        PartiesEntity fetchedParty = getInsureeOrThrow(dto.getPartyId());							// Retrieve party entity or throw exception if not found
        partiesMapper.updatePartiesEntity(dto, fetchedParty);										// Apply the DTO values to the party entity
        partiesRepository.save(fetchedParty);					// Save updated party to the database
    }
    
    /**
     * Retrieves a party entity by its ID or throws a {@link PartyNotFoundException} if not found.
     *
     * @param partyId the ID of the party to retrieve.
     * @return the corresponding {@code PartiesEntity} if found.
     * @throws PartyNotFoundException if no party with the given ID exists.
     */
    private PartiesEntity getInsureeOrThrow(long partyId) {
        return partiesRepository.findById(partyId).orElseThrow(PartyNotFoundException::new);		// Return party entity or throw exception if not found
    }

    /**
     * Deletes a party entity by its ID from the database, or throws a {@link PartyNotFoundException} if not found.
     *
     * @param partyId the ID of the party to delete.
     */
    @Override
	public void delete(long partyId) {
    	PartiesEntity fetchedParty = getInsureeOrThrow(partyId);									// Retrieve party entity or throw exception if not found
    	partiesRepository.delete(fetchedParty);					// Delete the party entity from the database
	}
    
    /**
     * Searches for party entities based on the criteria submitted by the user via DTO.
     *
     * @param dto a DTO containing the search criteria; includes personal details such as name, surname, birth date, birth number, email, street, and phone number.
     * @param status the {@link PartyStatus} to filter by (e.g., {@code POLICY_OWNER}, {@code INSURED}).
     * @return a list of matching {@link PartiesEntity} objects.
     */
    public List<PartiesEntity> findUserId(PartiesDetailsDTO dto, PartyStatus status) {
    	
    	return partiesRepository.searchParties(
    			status,
    	        emptyToNull(dto.getName()),
    	        emptyToNull(dto.getSurname()),
    	        emptyToNull(dto.getStreet()),
    	        emptyToNull(dto.getEmail()),
    	        emptyToNull(dto.getPhoneNumber()),
    	        dto.getBirthDay()
    	    );													// Return list of all matching parties
    }
    
    /**
     * Converts an empty or blank string to {@code null}.
     *
     * @param value the input string to evaluate.
     * @return {@code null} if the string is {@code null}, empty, or only whitespace; otherwise, returns the original string.
     */
    private String emptyToNull(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value;							// Return original string or null if empty/blank
    }
    
    /**
     * Validates the submitted search criteria to ensure the request is valid.
     *
     * @param dto a DTO containing the search criteria; includes personal details such as name, surname, birth date, birth number, email, street, and phone number.
     * @return {@code true} if the submitted criteria meet the minimum requirements for a valid search; {@code false} otherwise.
     */
    public final boolean isSearchRequestValid(PartiesDetailsDTO dto) {
    	
    	// Should the birth number be provided
    	if(dto.getBirthNumber() != null) {
    		return true;										// Return evaluation as passed
    	} else {		// Or should the birth number be missing
			int checksPassed = 0;								// Initialize counter of passed checks
    		
			// Should the name be provided...
			if (!dto.getName().equals("")) {
				checksPassed++;									// Increase the number of passed checks...
			}
    		
			if (!dto.getSurname().equals("")) {
				checksPassed++;							
			}
    		
			if (!dto.getStreet().equals("")) {
				checksPassed++;					
			}
    		
			if (!dto.getEmail().equals("")) {
				checksPassed++;
			}
    		
			if (!dto.getPhoneNumber().equals("")) {
    			checksPassed++;
    		}
			
			if (dto.getBirthDay() != null) {
				checksPassed++;
			}
    		
			// Should the desired amount of checks be passed
			if (checksPassed >= 3) {							
				return true;									// Return evaluation as passed
			}
		}
    	
    	return false;											// Since no requirement was met, return evaluation as failed
    }
    
	
}