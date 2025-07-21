package com.danger.insurance.archive.data.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.archive.data.entities.DeletedPartiesEntity;

public interface DeletedPartiesRepository extends CrudRepository<DeletedPartiesEntity, Long> {
	
	@Query("SELECT COUNT(dp) FROM DeletedPartiesEntity dp")
	long getTotalDeletedPartiesCount();
}