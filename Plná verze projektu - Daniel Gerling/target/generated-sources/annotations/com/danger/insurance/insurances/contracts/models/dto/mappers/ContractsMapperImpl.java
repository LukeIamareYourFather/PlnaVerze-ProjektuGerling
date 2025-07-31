package com.danger.insurance.insurances.contracts.models.dto.mappers;

import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-31T23:31:48+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class ContractsMapperImpl implements ContractsMapper {

    @Override
    public ContractsEntity toEntity(ContractsDTO source) {
        if ( source == null ) {
            return null;
        }

        ContractsEntity contractsEntity = new ContractsEntity();

        contractsEntity.setContractId( source.getContractId() );
        contractsEntity.setContractNumber( source.getContractNumber() );
        contractsEntity.setInsuredSubject( source.getInsuredSubject() );
        contractsEntity.setInsuranceType( source.getInsuranceType() );
        contractsEntity.setBeginDate( source.getBeginDate() );
        contractsEntity.setSignatureDate( source.getSignatureDate() );
        contractsEntity.setPricePerPeriod( source.getPricePerPeriod() );
        contractsEntity.setLiabilityPercentage( source.getLiabilityPercentage() );
        contractsEntity.setInsurancesEntity( source.getInsurancesEntity() );

        return contractsEntity;
    }

    @Override
    public ContractsDTO toDTO(ContractsEntity source) {
        if ( source == null ) {
            return null;
        }

        ContractsDTO contractsDTO = new ContractsDTO();

        contractsDTO.setContractId( source.getContractId() );
        contractsDTO.setContractNumber( source.getContractNumber() );
        contractsDTO.setInsuredSubject( source.getInsuredSubject() );
        contractsDTO.setInsuranceType( source.getInsuranceType() );
        contractsDTO.setBeginDate( source.getBeginDate() );
        contractsDTO.setSignatureDate( source.getSignatureDate() );
        contractsDTO.setPricePerPeriod( source.getPricePerPeriod() );
        contractsDTO.setLiabilityPercentage( source.getLiabilityPercentage() );
        contractsDTO.setInsurancesEntity( source.getInsurancesEntity() );

        return contractsDTO;
    }

    @Override
    public void updatePartiesSearchDTO(ContractsDTO source, ContractsDTO target) {
        if ( source == null ) {
            return;
        }

        target.setContractId( source.getContractId() );
        target.setContractNumber( source.getContractNumber() );
        target.setInsuredSubject( source.getInsuredSubject() );
        target.setInsuranceType( source.getInsuranceType() );
        target.setBeginDate( source.getBeginDate() );
        target.setSignatureDate( source.getSignatureDate() );
        target.setPricePerPeriod( source.getPricePerPeriod() );
        target.setLiabilityPercentage( source.getLiabilityPercentage() );
        target.setInsurancesEntity( source.getInsurancesEntity() );
    }

    @Override
    public void updatePartiesEntity(ContractsDTO source, ContractsEntity target) {
        if ( source == null ) {
            return;
        }

        target.setContractId( source.getContractId() );
        target.setContractNumber( source.getContractNumber() );
        target.setInsuredSubject( source.getInsuredSubject() );
        target.setInsuranceType( source.getInsuranceType() );
        target.setBeginDate( source.getBeginDate() );
        target.setSignatureDate( source.getSignatureDate() );
        target.setPricePerPeriod( source.getPricePerPeriod() );
        target.setLiabilityPercentage( source.getLiabilityPercentage() );
        target.setInsurancesEntity( source.getInsurancesEntity() );
    }
}
