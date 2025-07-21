package com.danger.insurance.accounts.models.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.danger.insurance.accounts.models.dto.AccountsDTO;

public interface AccountsService extends UserDetailsService{

	void create(AccountsDTO accountsDTO, String confirmPassword);

}