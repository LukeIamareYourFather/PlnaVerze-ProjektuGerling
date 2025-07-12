package com.danger.insurance.insurances.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.insurances.data.entities.InsurancesEntity;

public interface InsurancesRepository extends CrudRepository<InsurancesEntity, Long> {

}