package com.danger.insurance.models.services.insurances;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danger.insurance.data.entities.PartyContractsEntity;
import com.danger.insurance.data.repositories.PartyContractsRepository;
import com.danger.insurance.models.dto.insurances.PartyContractsDTO;
import com.danger.insurance.models.dto.mappers.PartyContractsMapper;
import com.danger.insurance.models.exceptions.PartyNotFoundException;

@Service
public class PartyContractsServiceImplementation implements PartyContractsService{

	// Object initialization
	
	@Autowired
	private PartyContractsRepository partyContractsRepository;				// Handles querying operations for the Parties entity
		
	@Autowired
	private PartyContractsMapper partyContractsMapper;
	// Start of code

	/**
	 * Creates a new party entry in the Parties database and returns the party ID of the created party.
	 *
	 * @param dto a DTO containing personal information used to create a new party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 * @return a {@code long} number of the party ID from the created party entry.
	 */
	@Override
	public long create(PartyContractsDTO dto) {
		PartyContractsEntity newInsurance = partyContractsMapper.toEntity(dto);										// Convert received DTO to party entity
		partyContractsRepository.save(newInsurance);						// Save the received party to the database
		
		return newInsurance.getId();
	}

	/**
	 * Retrieves all parties from the database and converts them into DTOs.
	 *
	 * @return a list of DTOs representing all parties in the database.
	 */
	@Override
	public List<PartyContractsDTO> getAll() {
		List<PartyContractsDTO> insurances = new ArrayList<>();	// Initialize list to hold mapped party DTOs
	    Iterable<PartyContractsEntity> fetchedInsurances = partyContractsRepository.findAll();						// Fetch all party entities from the database
	    
	    // Map each entity to a DTO and add it to the result list
	    for (PartyContractsEntity insurancesEntity : fetchedInsurances) {	
	    	PartyContractsDTO mappedInsurance = partyContractsMapper.toDTO(insurancesEntity);						// Convert current entity to a DTO
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
	public PartyContractsDTO getById(long partyId) {
		PartyContractsEntity fetchedInsurance = getInsuranceOrThrow(partyId);									// Retrieve party entity or throw exception if not found
	    
	    return partyContractsMapper.toDTO(fetchedInsurance);				// Convert party entity to DTO and return
	}

	/**
	 * Updates an existing party entity in the database using values from the provided DTO.
	 *
	 * @param dto a DTO containing personal information to update the party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 */
	@Override
    public void edit(PartyContractsDTO dto) {
		PartyContractsEntity fetchedInsurance = getInsuranceOrThrow(dto.getId());							// Retrieve party entity or throw exception if not found
		partyContractsMapper.updatePartiesEntity(dto, fetchedInsurance);										// Apply the DTO values to the party entity
		partyContractsRepository.save(fetchedInsurance);					// Save updated party to the database
    }
	
    /**
     * Retrieves a party entity by its ID or throws a {@link PartyNotFoundException} if not found.
     *
     * @param partyId the ID of the party to retrieve.
     * @return the corresponding {@code PartiesEntity} if found.
     * @throws PartyNotFoundException if no party with the given ID exists.
     */
    private PartyContractsEntity getInsuranceOrThrow(long partyId) {
        return partyContractsRepository.findById(partyId).orElseThrow(PartyNotFoundException::new);		// Return party entity or throw exception if not found
    }

	@Override
	public void delete(long insurancesId) {
		PartyContractsEntity fetchedInsurance = getInsuranceOrThrow(insurancesId);									// Retrieve party entity or throw exception if not found
		partyContractsRepository.delete(fetchedInsurance);					// Delete the party entity from the database
	}

}
