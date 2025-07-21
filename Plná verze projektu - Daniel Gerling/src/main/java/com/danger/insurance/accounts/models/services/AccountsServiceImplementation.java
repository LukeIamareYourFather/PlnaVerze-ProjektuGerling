package com.danger.insurance.accounts.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.danger.insurance.accounts.data.entities.AccountsEntity;
import com.danger.insurance.accounts.data.repositories.AccountsRepository;
import com.danger.insurance.accounts.models.dto.AccountsDTO;
import com.danger.insurance.accounts.models.exceptions.DuplicateEmailException;
import com.danger.insurance.accounts.models.exceptions.PasswordsDoNotEqualException;

@Service
public class AccountsServiceImplementation implements AccountsService {

    @Autowired
    private AccountsRepository usersRepository;
    
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
            usersRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException();
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUserEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username, " + username + " not found"));
    }
    
}