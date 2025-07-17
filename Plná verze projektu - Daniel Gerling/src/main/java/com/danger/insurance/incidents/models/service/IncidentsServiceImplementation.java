package com.danger.insurance.incidents.models.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danger.insurance.incidents.data.entities.IncidentsEntity;
import com.danger.insurance.incidents.data.repositories.IncidentsRepository;
import com.danger.insurance.incidents.models.dto.IncidentsDTO;
import com.danger.insurance.incidents.models.dto.get.IncidentDetailsGetDTO;
import com.danger.insurance.incidents.models.dto.mappers.IncidentsMapper;
import com.danger.insurance.incidents.models.dto.post.IncidentsFindPostDTO;
import com.danger.insurance.incidents.models.exceptions.IncidentNotFoundException;
import com.danger.insurance.parties.models.exceptions.PartyNotFoundException;

@Service
public class IncidentsServiceImplementation implements IncidentsService {

	// Object initialization
	
	@Autowired
	private IncidentsRepository incidentsRepository;				// Handles querying operations for the Parties entity
	
	@Autowired
	private IncidentsMapper incidentsMapper;						// Handles mapping between Parties entity and DTOs
	
	// Start of code

	/**
	 * Creates a new party entry in the Parties database and returns the party ID of the created party.
	 *
	 * @param dto a DTO containing personal information used to create a new party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 * @return a {@code long} number of the party ID from the created party entry.
	 */
	@Override
	public long create(IncidentsDTO dto) {
		IncidentsEntity newIncident = incidentsMapper.incidentCreateToEntity(dto);										// Convert received DTO to party entity
		incidentsRepository.save(newIncident);						// Save the received party to the database
		
		return newIncident.getIncidentId();
	}

	/**
	 * Retrieves all parties from the database and converts them into DTOs.
	 *
	 * @return a list of DTOs representing all parties in the database.
	 */
	@Override
	public List<IncidentsDTO> getAll() {
		List<IncidentsDTO> parties = new ArrayList<>();	// Initialize list to hold mapped party DTOs
	    Iterable<IncidentsEntity> fetchedParties = incidentsRepository.findAll();						// Fetch all party entities from the database
	    
	    // Map each entity to a DTO and add it to the result list
	    for (IncidentsEntity partyEntity : fetchedParties) {	
	    	IncidentsDTO mappedParty = incidentsMapper.toDTO(partyEntity);						// Convert current entity to a DTO
	        parties.add(mappedParty);							//  Add converted DTO to the result list
	    }
	   
	    return parties;											// Return list of all mapped party DTOs
	}
	
	public List<IncidentDetailsGetDTO> getAllIncidentsDetails() {
		List<IncidentDetailsGetDTO> parties = new ArrayList<>();	// Initialize list to hold mapped party DTOs
	    Iterable<IncidentsEntity> fetchedParties = incidentsRepository.findAll();						// Fetch all party entities from the database
	    
	    // Map each entity to a DTO and add it to the result list
	    for (IncidentsEntity partyEntity : fetchedParties) {	
	    	IncidentDetailsGetDTO mappedParty = incidentsMapper.toDetailsToDTO(partyEntity);						// Convert current entity to a DTO
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
	public IncidentsDTO getIncidentById(long incidentId) {
		IncidentsEntity fetchedIncident = getIncidentOrThrow(incidentId);									// Retrieve party entity or throw exception if not found
	    
	    return incidentsMapper.toDTO(fetchedIncident);				// Convert party entity to DTO and return
	}
	
	@Override
	public IncidentDetailsGetDTO getDetailsById(long incidentId) {
		IncidentsEntity fetchedIncident = getIncidentOrThrow(incidentId);									// Retrieve party entity or throw exception if not found
	    
	    return incidentsMapper.toDetailsToDTO(fetchedIncident);				// Convert party entity to DTO and return
	}

	/**
	 * Updates an existing party entity in the database using values from the provided DTO.
	 *
	 * @param dto a DTO containing personal information to update the party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 */
	@Override
    public void edit(IncidentsDTO dto) {
		IncidentsEntity fetchedParty = getIncidentOrThrow(dto.getIncidentId());							// Retrieve party entity or throw exception if not found
		incidentsMapper.updateNewsEntity(dto, fetchedParty);										// Apply the DTO values to the party entity
		incidentsRepository.save(fetchedParty);					// Save updated party to the database
    }
    
    public List<IncidentsEntity> findIncidentId(IncidentsFindPostDTO findDTO) {
    	List<IncidentsEntity> foundIncidents = incidentsRepository.searchIncidents(
    			emptyToNull(findDTO.getCaseNumber()),
    	        emptyToNull(findDTO.getBirthNumber()),
    	        emptyToNull(findDTO.getTitle()),
    	        findDTO.getIncidentType(),
    	        findDTO.getIncidentSubject(),
    	        findDTO.getCurrentStatus(),
    	        findDTO.getAccidentDate()
    	    );			
    	
    	return foundIncidents;							// Return list of all matching parties
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
     * Retrieves a party entity by its ID or throws a {@link PartyNotFoundException} if not found.
     *
     * @param partyId the ID of the party to retrieve.
     * @return the corresponding {@code PartiesEntity} if found.
     * @throws PartyNotFoundException if no party with the given ID exists.
     */
    private IncidentsEntity getIncidentOrThrow(long incidentId) {
        return incidentsRepository.findById(incidentId).orElseThrow(IncidentNotFoundException::new);		// Return party entity or throw exception if not found
    }

    /**
     * Deletes a party entity by its ID from the database, or throws a {@link PartyNotFoundException} if not found.
     *
     * @param partyId the ID of the party to delete.
     */
    @Override
	public void delete(long partyId) {
    	IncidentsEntity fetchedParty = getIncidentOrThrow(partyId);									// Retrieve party entity or throw exception if not found
    	incidentsRepository.delete(fetchedParty);					// Delete the party entity from the database
	}
  	
}