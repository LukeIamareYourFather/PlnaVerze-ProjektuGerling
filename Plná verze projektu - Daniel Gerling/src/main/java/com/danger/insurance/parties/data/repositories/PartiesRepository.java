package com.danger.insurance.parties.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.danger.insurance.parties.data.entities.PartiesEntity;

/**
 * Repository interface for accessing and querying {@link PartiesEntity} objects.
 * Provides standard CRUD methods and custom queries for advanced party lookups.
 */
public interface PartiesRepository extends JpaRepository<PartiesEntity, Long>, JpaSpecificationExecutor<PartiesEntity> {
	
	Optional<PartiesEntity> findByBirthNumber(String birthNumber);
	
	@Query("SELECT COUNT(p) FROM PartiesEntity p")
    long getTotalUserCount();

}