package com.danger.insurance.accounts.data.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.accounts.data.entities.AccountsEntity;
import com.danger.insurance.parties.data.entities.PartiesEntity;

/**
 * Repository interface for accessing and querying {@link PartiesEntity} objects.
 * Provides standard CRUD methods and custom queries for advanced party lookups.
 */
public interface AccountsRepository extends CrudRepository<AccountsEntity, Long> {
	Optional<AccountsEntity> findByUserEmail(String userEmail);
}