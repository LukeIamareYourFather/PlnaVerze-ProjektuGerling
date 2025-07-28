package com.danger.insurance.insurances.data.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.insurances.data.entities.InsurancesEntity;
import com.danger.insurance.insurances.data.enums.InsurancesType;

public interface InsurancesRepository extends CrudRepository<InsurancesEntity, Long> {

	List<InsurancesEntity> findByInsurancesType(InsurancesType insurancesType);
}