package com.danger.insurance.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.danger.insurance.data.entities.ContractsEntity;
import com.danger.insurance.data.entities.PartiesEntity;
import com.danger.insurance.data.entities.PartyContractsEntity;

public interface PartyContractsRepository extends CrudRepository<PartyContractsEntity, Long> {
    List<PartyContractsEntity> findByPartyEntity_PartyId(Long partyId);
    List<PartyContractsEntity> findByContractEntity_ContractId(Long contractId);
    
    @Query("SELECT pc.contractEntity FROM PartyContractsEntity pc WHERE pc.partyEntity.partyId = :partyId")
    List<ContractsEntity> findContractsByPartyId(@Param("partyId") Long partyId);
    
    @Query("SELECT pc.partyEntity FROM PartyContractsEntity pc WHERE pc.contractEntity.contractId = :contractId")
    List<PartiesEntity> findPartiesByContractId(@Param("contractId") Long contractId);
}