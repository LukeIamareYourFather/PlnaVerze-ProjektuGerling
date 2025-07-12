package com.danger.insurance.parties.models.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danger.insurance.archive.data.entities.DeletedPartiesEntity;
import com.danger.insurance.archive.data.repositories.DeletedPartiesRepository;
import com.danger.insurance.parties.models.dto.DeletedPartiesDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesReasonsFormDTO;
import com.danger.insurance.parties.models.dto.mappers.DeletedPartiesMapper;
import com.danger.insurance.parties.models.exceptions.PartyNotFoundException;

@Service
public class DeletedPartiesServiceImplementation implements DeletedPartiesService{

	// Object initialization
	
	@Autowired
	private DeletedPartiesRepository deletedRepository;				// Handles querying operations for the Parties entity
	
	@Autowired
	private DeletedPartiesMapper deletedMapper;						// Handles mapping between Parties entity and DTOs
	
	// Start of code

	/**
	 * Creates a new party entry in the Parties database and returns the party ID of the created party.
	 *
	 * @param dto a DTO containing personal information used to create a new party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 * @return a {@code long} number of the party ID from the created party entry.
	 */
	@Override
	public long create(DeletedPartiesDTO dto) {
		DeletedPartiesEntity newInsuree = deletedMapper.partiesCreateToEntity(dto);										// Convert received DTO to party entity
		deletedRepository.save(newInsuree);						// Save the received party to the database
		
		return newInsuree.getPartyId();
	}

	/**
	 * Retrieves all parties from the database and converts them into DTOs.
	 *
	 * @return a list of DTOs representing all parties in the database.
	 */
	@Override
	public List<DeletedPartiesDTO> getAll() {
		List<DeletedPartiesDTO> parties = new ArrayList<>();	// Initialize list to hold mapped party DTOs
	    Iterable<DeletedPartiesEntity> fetchedParties = deletedRepository.findAll();						// Fetch all party entities from the database
	    
	    // Map each entity to a DTO and add it to the result list
	    for (DeletedPartiesEntity partyEntity : fetchedParties) {	
	    	DeletedPartiesDTO mappedParty = deletedMapper.toDTO(partyEntity);						// Convert current entity to a DTO
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
	public DeletedPartiesDTO getById(long partyId) {
		DeletedPartiesEntity fetchedInsuree = getInsureeOrThrow(partyId);									// Retrieve party entity or throw exception if not found
	    
	    return deletedMapper.toDTO(fetchedInsuree);				// Convert party entity to DTO and return
	}

	/**
	 * Updates an existing party entity in the database using values from the provided DTO.
	 *
	 * @param dto a DTO containing personal information to update the party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 */
	@Override
    public void edit(DeletedPartiesDTO dto) {
		DeletedPartiesEntity fetchedParty = getInsureeOrThrow(dto.getDeleteReasonId());							// Retrieve party entity or throw exception if not found
        deletedMapper.updatePartiesEntity(dto, fetchedParty);										// Apply the DTO values to the party entity
        deletedRepository.save(fetchedParty);					// Save updated party to the database
    }
    
    /**
     * Retrieves a party entity by its ID or throws a {@link PartyNotFoundException} if not found.
     *
     * @param partyId the ID of the party to retrieve.
     * @return the corresponding {@code PartiesEntity} if found.
     * @throws PartyNotFoundException if no party with the given ID exists.
     */
    private DeletedPartiesEntity getInsureeOrThrow(long partyId) {
        return deletedRepository.findById(partyId).orElseThrow(PartyNotFoundException::new);		// Return party entity or throw exception if not found
    }

	@Override
	public void delete(long reasonId) {
		DeletedPartiesEntity fetchedDeletedParty = getInsureeOrThrow(reasonId);									// Retrieve party entity or throw exception if not found
		deletedRepository.delete(fetchedDeletedParty);					// Delete the party entity from the database
		
	}
	
	public DeletedPartiesDTO createDeleteDTO(PartiesReasonsFormDTO reasonsDto, PartiesDetailsDTO partiesDTO) {
		return deletedMapper.mergeToDeleteDTO(reasonsDto, partiesDTO);
	}
    
}
