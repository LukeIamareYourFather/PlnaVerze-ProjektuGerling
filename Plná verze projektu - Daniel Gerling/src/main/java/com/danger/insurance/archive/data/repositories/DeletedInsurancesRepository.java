package com.danger.insurance.archive.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.archive.data.entities.DeletedInsurancesEntity;

public interface DeletedInsurancesRepository extends CrudRepository<DeletedInsurancesEntity, Long> {
}