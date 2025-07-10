package com.danger.insurance.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.data.entities.DeletedInsurancesEntity;

public interface DeletedInsurancesRepository extends CrudRepository<DeletedInsurancesEntity, Long> {
}