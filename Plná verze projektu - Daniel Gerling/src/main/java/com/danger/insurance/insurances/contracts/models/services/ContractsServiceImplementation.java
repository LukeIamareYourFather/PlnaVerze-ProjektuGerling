package com.danger.insurance.insurances.contracts.models.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.data.repositories.ContractsRepository;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.dto.mappers.ContractsMapper;
import com.danger.insurance.insurances.contracts.models.exceptions.ContractNotFoundException;
import com.danger.insurance.parties.models.exceptions.PartyNotFoundException;
import com.danger.insurance.specifications.ContractsSpecifications;

@Service
public class ContractsServiceImplementation implements ContractsService{

	// Object initialization
	
	@Autowired
	private ContractsRepository contractsRepository;
			
	@Autowired
	private ContractsMapper contractsMapper;
	// Start of code

	/**
	 * Creates a new party entry in the Parties database and returns the party ID of the created party.
	 *
	 * @param dto a DTO containing personal information used to create a new party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 * @return a {@code long} number of the party ID from the created party entry.
	 */
	@Override
	public long create(ContractsDTO dto) {
		ContractsEntity newInsurance = contractsMapper.toEntity(dto);										// Convert received DTO to party entity
		contractsRepository.save(newInsurance);						// Save the received party to the database
		
		return newInsurance.getContractId();
	}

	/**
	 * Retrieves all parties from the database and converts them into DTOs.
	 *
	 * @return a list of DTOs representing all parties in the database.
	 */
	@Override
	public List<ContractsDTO> getAll() {
		List<ContractsDTO> insurances = new ArrayList<>();	// Initialize list to hold mapped party DTOs
	    Iterable<ContractsEntity> fetchedInsurances = contractsRepository.findAll();						// Fetch all party entities from the database
	    
	    // Map each entity to a DTO and add it to the result list
	    for (ContractsEntity insurancesEntity : fetchedInsurances) {	
	    	ContractsDTO mappedInsurance = contractsMapper.toDTO(insurancesEntity);						// Convert current entity to a DTO
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
	public ContractsDTO getById(long partyId) {
		ContractsEntity fetchedInsurance = getInsuranceOrThrow(partyId);									// Retrieve party entity or throw exception if not found
	    
	    return contractsMapper.toDTO(fetchedInsurance);				// Convert party entity to DTO and return
	}

	/**
	 * Updates an existing party entity in the database using values from the provided DTO.
	 *
	 * @param dto a DTO containing personal information to update the party; includes fields such as name, surname, birth date, birth number, email, street, and phone number.
	 */
	@Override
    public void edit(ContractsDTO dto) {
		ContractsEntity fetchedInsurance = getInsuranceOrThrow(dto.getContractId());							// Retrieve party entity or throw exception if not found
		contractsMapper.updatePartiesEntity(dto, fetchedInsurance);										// Apply the DTO values to the party entity
		contractsRepository.save(fetchedInsurance);					// Save updated party to the database
    }
	
	public List<ContractsEntity> findContractId(ContractsDTO dto) {
    	
    	return contractsRepository.findAll(ContractsSpecifications.dynamicContractSearch(
    	        emptyToNull(dto.getContractNumber()),
    	        dto.getInsuredSubject(),
    	        dto.getInsuranceType(),
    	        dto.getBeginDate(),
    	        dto.getSignatureDate()
    	    ));													// Return list of all matching parties
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
    private ContractsEntity getInsuranceOrThrow(long partyId) {
        return contractsRepository.findById(partyId).orElseThrow(ContractNotFoundException::new);		// Return party entity or throw exception if not found
    }

	@Override
	public void delete(long insurancesId) {
		ContractsEntity fetchedInsurance = getInsuranceOrThrow(insurancesId);									// Retrieve party entity or throw exception if not found
		contractsRepository.delete(fetchedInsurance);					// Delete the party entity from the database
	}

}
