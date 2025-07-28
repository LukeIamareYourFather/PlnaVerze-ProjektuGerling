package com.danger.insurance.insurances.contracts.data.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;

public interface ContractsRepository extends JpaRepository<ContractsEntity, Long>, JpaSpecificationExecutor<ContractsEntity> {
	
	@Query("SELECT COUNT(c) FROM ContractsEntity c")
	long getTotalContractsCount();

	@Query(value = "SELECT SUM(c.price_per_period * (12.0 / i.renewal_period)) " +
            	   "FROM contracts_entity c " +
            	   "JOIN insurances_entity i ON c.insurances_id = i.insurances_id", nativeQuery = true)
	BigDecimal getTotalContractValue();

}