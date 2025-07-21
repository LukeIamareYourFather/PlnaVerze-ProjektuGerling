package com.danger.insurance.archive.data.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.archive.data.entities.DeletedInsurancesEntity;

public interface DeletedInsurancesRepository extends CrudRepository<DeletedInsurancesEntity, Long> {
	
	@Query("SELECT COUNT(di) FROM DeletedInsurancesEntity di")
	long getTotalDeletedInsurancesCount();
}