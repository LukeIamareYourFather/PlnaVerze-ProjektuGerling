package com.danger.insurance.accounts.models.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.danger.insurance.accounts.data.entities.AccountsEntity;
import com.danger.insurance.accounts.data.repositories.AccountsRepository;
import com.danger.insurance.accounts.models.dto.AccountsDTO;
import com.danger.insurance.accounts.models.dto.get.AccountDetailsDTO;
import com.danger.insurance.accounts.models.dto.mappers.AccountsMapper;
import com.danger.insurance.accounts.models.exceptions.AccountNotFoundException;
import com.danger.insurance.accounts.models.exceptions.DuplicateEmailException;
import com.danger.insurance.accounts.models.exceptions.PasswordsDoNotEqualException;
import com.danger.insurance.specifications.AccountsSpecifications;

@Service
public class AccountsServiceImplementation implements AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;
    
    @Autowired
    private AccountsMapper accountsMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void create(AccountsDTO user, String confirmPassword) {
        if (!user.getUserPassword().equals(confirmPassword))
            throw new PasswordsDoNotEqualException();

        AccountsEntity userEntity = new AccountsEntity();
        userEntity.setUserEmail(user.getUserEmail());
        userEntity.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userEntity.setUserRole(user.getUserRole());

        try {
            accountsRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException();
        }
    }
 
    public void edit(AccountsDTO dto, boolean ifToUpdatePassword) {
    	AccountsEntity fetchedParty = getAccountOrThrow(dto.getAccountId());
    	accountsMapper.updateAccountsEntity(dto, fetchedParty);		
    	
    	//
    	if (ifToUpdatePassword) {
    		fetchedParty.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));

    	}
    	 
    	try {
            accountsRepository.save(fetchedParty);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException();
        }
    	
    }
    
    public List<AccountsDTO> getAll() {
		List<AccountsDTO> foundAccounts = new ArrayList<>();	// Initialize list to hold mapped party DTOs
	    Iterable<AccountsEntity> fetchedAccounts = accountsRepository.findAll();						// Fetch all party entities from the database
	    
	    // Map each entity to a DTO and add it to the result list
	    for (AccountsEntity accountEntity : fetchedAccounts) {	
	        AccountsDTO mappedAccount = accountsMapper.accountsEntityToAccountsDTO(accountEntity);						// Convert current entity to a DTO
	        foundAccounts.add(mappedAccount);							//  Add converted DTO to the result list
	    }
	   
	    return foundAccounts;											// Return list of all mapped party DTOs
	}
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountsRepository.findByUserEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username, " + username + " not found"));
    }
    
    public AccountDetailsDTO getDetailsById(long accountId) {
	    AccountsEntity fetchedInsuree = getAccountOrThrow(accountId);									// Retrieve party entity or throw exception if not found
	    
	    return accountsMapper.accountsEntityToAccountsDetailsDTO(fetchedInsuree);				// Convert party entity to DTO and return
	}
    
    public AccountsDTO getAccountsDTOById(long accountId) {
	    AccountsEntity fetchedInsuree = getAccountOrThrow(accountId);									// Retrieve party entity or throw exception if not found
	    
	    return accountsMapper.accountsEntityToAccountsDTO(fetchedInsuree);				// Convert party entity to DTO and return
	}
    
    private AccountsEntity getAccountOrThrow(long accountId) {
        return accountsRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);		// Return party entity or throw exception if not found
    }
    
    public List<AccountsEntity> findAccountIds(AccountDetailsDTO dto) {
    	List<AccountsEntity> foundParties = accountsRepository.findAll(AccountsSpecifications.dynamicAccountSearch(
    			emptyToNull(dto.getUserEmail()), 
    			dto.getUserRole()));			
    	
    	return foundParties;								// Return list of all matching parties
    }
    
    public void delete(long accountId) {
    	AccountsEntity fetchedAccount = getAccountOrThrow(accountId);									// Retrieve party entity or throw exception if not found
    	accountsRepository.delete(fetchedAccount);					// Delete the party entity from the database
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
}