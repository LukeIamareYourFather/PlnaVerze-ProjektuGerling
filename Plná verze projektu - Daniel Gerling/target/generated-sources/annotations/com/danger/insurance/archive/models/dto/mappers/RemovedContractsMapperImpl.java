package com.danger.insurance.archive.models.dto.mappers;

import com.danger.insurance.archive.data.entities.RemovedContractsEntity;
import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-19T18:12:43+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class RemovedContractsMapperImpl implements RemovedContractsMapper {

    @Override
    public RemovedContractsEntity toEntity(RemoveContractReasonsDTO source) {
        if ( source == null ) {
            return null;
        }

        RemovedContractsEntity removedContractsEntity = new RemovedContractsEntity();

        removedContractsEntity.setRemovedInsurancesId( source.getRemovedInsurancesId() );
        removedContractsEntity.setDeleteReason( source.getDeleteReason() );
        removedContractsEntity.setDescription( source.getDescription() );
        removedContractsEntity.setDateOfRequest( source.getDateOfRequest() );
        removedContractsEntity.setDateOfCancellation( source.getDateOfCancellation() );
        removedContractsEntity.setTodaysDate( source.getTodaysDate() );
        removedContractsEntity.setBirthNumber( source.getBirthNumber() );
        removedContractsEntity.setContractNumber( source.getContractNumber() );
        removedContractsEntity.setInsuredSubject( source.getInsuredSubject() );
        removedContractsEntity.setInsuranceType( source.getInsuranceType() );
        removedContractsEntity.setBeginDate( source.getBeginDate() );
        removedContractsEntity.setSignatureDate( source.getSignatureDate() );
        removedContractsEntity.setPricePerPeriod( source.getPricePerPeriod() );
        removedContractsEntity.setLiabilityPercentage( source.getLiabilityPercentage() );
        removedContractsEntity.setInsuranceName( source.getInsuranceName() );
        removedContractsEntity.setInsurancesType( source.getInsurancesType() );

        return removedContractsEntity;
    }

    @Override
    public RemoveContractReasonsDTO toDTO(RemovedContractsEntity source) {
        if ( source == null ) {
            return null;
        }

        RemoveContractReasonsDTO removeContractReasonsDTO = new RemoveContractReasonsDTO();

        removeContractReasonsDTO.setRemovedInsurancesId( source.getRemovedInsurancesId() );
        removeContractReasonsDTO.setDeleteReason( source.getDeleteReason() );
        removeContractReasonsDTO.setDescription( source.getDescription() );
        removeContractReasonsDTO.setDateOfRequest( source.getDateOfRequest() );
        removeContractReasonsDTO.setDateOfCancellation( source.getDateOfCancellation() );
        removeContractReasonsDTO.setTodaysDate( source.getTodaysDate() );
        removeContractReasonsDTO.setBirthNumber( source.getBirthNumber() );
        removeContractReasonsDTO.setContractNumber( source.getContractNumber() );
        removeContractReasonsDTO.setInsuredSubject( source.getInsuredSubject() );
        removeContractReasonsDTO.setInsuranceType( source.getInsuranceType() );
        removeContractReasonsDTO.setBeginDate( source.getBeginDate() );
        removeContractReasonsDTO.setSignatureDate( source.getSignatureDate() );
        removeContractReasonsDTO.setPricePerPeriod( source.getPricePerPeriod() );
        removeContractReasonsDTO.setLiabilityPercentage( source.getLiabilityPercentage() );
        removeContractReasonsDTO.setInsuranceName( source.getInsuranceName() );
        removeContractReasonsDTO.setInsurancesType( source.getInsurancesType() );

        return removeContractReasonsDTO;
    }

    @Override
    public RemoveContractReasonsDTO mergeToRemoveContractReasonsDTO(ContractsDTO contractsDTO, InsurancesDTO insurancesDTO, RemoveContractReasonsDTO removeContractReasonsDTO) {
        if ( contractsDTO == null && insurancesDTO == null ) {
            return removeContractReasonsDTO;
        }

        if ( contractsDTO != null ) {
            removeContractReasonsDTO.setContractNumber( contractsDTO.getContractNumber() );
            removeContractReasonsDTO.setInsuredSubject( contractsDTO.getInsuredSubject() );
            removeContractReasonsDTO.setInsuranceType( contractsDTO.getInsuranceType() );
            removeContractReasonsDTO.setBeginDate( contractsDTO.getBeginDate() );
            removeContractReasonsDTO.setSignatureDate( contractsDTO.getSignatureDate() );
            if ( contractsDTO.getPricePerPeriod() != null ) {
                removeContractReasonsDTO.setPricePerPeriod( contractsDTO.getPricePerPeriod() );
            }
            removeContractReasonsDTO.setLiabilityPercentage( contractsDTO.getLiabilityPercentage() );
        }
        if ( insurancesDTO != null ) {
            removeContractReasonsDTO.setDescription( insurancesDTO.getDescription() );
            removeContractReasonsDTO.setInsurancesType( insurancesDTO.getInsurancesType() );
        }

        return removeContractReasonsDTO;
    }

    @Override
    public void updatePartiesSearchDTO(RemoveContractReasonsDTO source, RemoveContractReasonsDTO target) {
        if ( source == null ) {
            return;
        }

        target.setRemovedInsurancesId( source.getRemovedInsurancesId() );
        target.setDeleteReason( source.getDeleteReason() );
        target.setDescription( source.getDescription() );
        target.setDateOfRequest( source.getDateOfRequest() );
        target.setDateOfCancellation( source.getDateOfCancellation() );
        target.setTodaysDate( source.getTodaysDate() );
        target.setBirthNumber( source.getBirthNumber() );
        target.setContractNumber( source.getContractNumber() );
        target.setInsuredSubject( source.getInsuredSubject() );
        target.setInsuranceType( source.getInsuranceType() );
        target.setBeginDate( source.getBeginDate() );
        target.setSignatureDate( source.getSignatureDate() );
        target.setPricePerPeriod( source.getPricePerPeriod() );
        target.setLiabilityPercentage( source.getLiabilityPercentage() );
        target.setInsuranceName( source.getInsuranceName() );
        target.setInsurancesType( source.getInsurancesType() );
    }

    @Override
    public void updatePartiesEntity(RemoveContractReasonsDTO source, RemovedContractsEntity target) {
        if ( source == null ) {
            return;
        }

        target.setRemovedInsurancesId( source.getRemovedInsurancesId() );
        target.setDeleteReason( source.getDeleteReason() );
        target.setDescription( source.getDescription() );
        target.setDateOfRequest( source.getDateOfRequest() );
        target.setDateOfCancellation( source.getDateOfCancellation() );
        target.setTodaysDate( source.getTodaysDate() );
        target.setBirthNumber( source.getBirthNumber() );
        target.setContractNumber( source.getContractNumber() );
        target.setInsuredSubject( source.getInsuredSubject() );
        target.setInsuranceType( source.getInsuranceType() );
        target.setBeginDate( source.getBeginDate() );
        target.setSignatureDate( source.getSignatureDate() );
        target.setPricePerPeriod( source.getPricePerPeriod() );
        target.setLiabilityPercentage( source.getLiabilityPercentage() );
        target.setInsuranceName( source.getInsuranceName() );
        target.setInsurancesType( source.getInsurancesType() );
    }
}
