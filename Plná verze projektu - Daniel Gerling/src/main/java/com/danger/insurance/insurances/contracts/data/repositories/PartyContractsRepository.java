package com.danger.insurance.insurances.contracts.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.data.entities.PartyContractsEntity;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.data.enums.PartyStatus;

public interface PartyContractsRepository extends CrudRepository<PartyContractsEntity, Long> {
    List<PartyContractsEntity> findByPartyEntity_PartyId(Long partyId);
    List<PartyContractsEntity> findByContractEntity_ContractId(Long contractId);
    
    @Query("SELECT pc.contractEntity FROM PartyContractsEntity pc WHERE pc.partyEntity.partyId = :partyId")
    List<ContractsEntity> findContractsByPartyId(@Param("partyId") Long partyId);
    
    @Query("SELECT pc.partyEntity FROM PartyContractsEntity pc WHERE pc.contractEntity.contractId = :contractId")
    List<PartiesEntity> findPartiesByContractId(@Param("contractId") Long contractId);
    
    @Query("SELECT pc.contractRole FROM PartyContractsEntity pc WHERE pc.contractEntity.contractId = :contractId AND pc.partyEntity.partyId = :partyId")
   	PartyStatus findPartyStatus(@Param("contractId") Long contractId, @Param("partyId") Long partyId);
    
    @Query("SELECT COUNT(p) FROM PartyContractsEntity p WHERE p.contractRole = :contractRole")
    long getTotalUserCount(@Param("contractRole") PartyStatus contractRole);
 
    @Query(value = "SELECT COUNT(*) " +
            "FROM party_contracts_entity p " +
            "JOIN contracts_entity c ON p.contract_id = c.contract_id " +
            "WHERE c.insured_subject = :insuredSubject",
    nativeQuery = true)
	long getTotalContractsOfType(@Param("insuredSubject") String insuredSubject);

    @Query("SELECT pc.id FROM PartyContractsEntity pc WHERE pc.contractEntity.contractId = :contractId AND pc.partyEntity.partyId = :partyId")
   	long findContractPartyIdOfPartyContract(@Param("contractId") Long contractId, @Param("partyId") Long partyId);
}