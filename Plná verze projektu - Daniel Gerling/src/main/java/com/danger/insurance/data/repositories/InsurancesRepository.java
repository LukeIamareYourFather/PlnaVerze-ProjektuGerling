package com.danger.insurance.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.data.entities.InsurancesEntity;

public interface InsurancesRepository extends CrudRepository<InsurancesEntity, Long> {

}