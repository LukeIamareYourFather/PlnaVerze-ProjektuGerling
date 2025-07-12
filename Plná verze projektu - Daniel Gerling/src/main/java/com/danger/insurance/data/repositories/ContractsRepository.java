package com.danger.insurance.data.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.danger.insurance.data.entities.ContractsEntity;
import com.danger.insurance.data.enums.insurances.InsurancesSubjects;
import com.danger.insurance.data.enums.insurances.InsurancesType;

public interface ContractsRepository extends CrudRepository<ContractsEntity, Long> {
	
	@Query("""
			SELECT c FROM ContractsEntity c
			WHERE (:contractNumber IS NULL OR c.contractNumber = :contractNumber)
			AND (:insuredSubject IS NULL OR c.insuredSubject = :insuredSubject)
		   	AND (:insuranceType IS NULL OR c.insuranceType = :insuranceType)
			AND (:beginDate IS NULL OR c.beginDate = :beginDate)
			AND (:signatureDate IS NULL OR c.signatureDate = :signatureDate)
		  	AND (:pricePerPeriod IS NULL OR c.pricePerPeriod = :pricePerPeriod)
		""")
	List<ContractsEntity> searchContracts(
		@Param("contractNumber") String contractNumber,
		@Param("insuredSubject") InsurancesSubjects insuredSubject,
		@Param("insuranceType") InsurancesType insuranceType,
   		@Param("beginDate") LocalDate beginDate,
   		@Param("signatureDate") LocalDate signatureDate,
    	@Param("pricePerPeriod") Long pricePerPeriod
	);
}