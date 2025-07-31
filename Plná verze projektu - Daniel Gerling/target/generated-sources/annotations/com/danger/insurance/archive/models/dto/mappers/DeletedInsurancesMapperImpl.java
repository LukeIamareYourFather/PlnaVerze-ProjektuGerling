package com.danger.insurance.archive.models.dto.mappers;

import com.danger.insurance.archive.data.entities.DeletedInsurancesEntity;
import com.danger.insurance.archive.models.dto.DeleteInsurancesReasonsDTO;
import com.danger.insurance.archive.models.dto.DeletedInsurancesDTO;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-31T23:31:48+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class DeletedInsurancesMapperImpl implements DeletedInsurancesMapper {

    @Override
    public DeletedInsurancesEntity toEntity(DeletedInsurancesDTO source) {
        if ( source == null ) {
            return null;
        }

        DeletedInsurancesEntity deletedInsurancesEntity = new DeletedInsurancesEntity();

        deletedInsurancesEntity.setRemovalReason( source.getRemovalReason() );
        deletedInsurancesEntity.setRequestDate( source.getRequestDate() );
        deletedInsurancesEntity.setTodaysDate( source.getTodaysDate() );
        deletedInsurancesEntity.setDeleteDescription( source.getDeleteDescription() );
        deletedInsurancesEntity.setName( source.getName() );
        deletedInsurancesEntity.setInsurancesType( source.getInsurancesType() );
        deletedInsurancesEntity.setDescription( source.getDescription() );
        deletedInsurancesEntity.setMinimumInsuranceValue( source.getMinimumInsuranceValue() );
        deletedInsurancesEntity.setMaximumInsuranceValue( source.getMaximumInsuranceValue() );
        deletedInsurancesEntity.setMaximumPayoutValue( source.getMaximumPayoutValue() );

        return deletedInsurancesEntity;
    }

    @Override
    public DeletedInsurancesEntity partiesCreateToEntity(DeletedInsurancesDTO source) {
        if ( source == null ) {
            return null;
        }

        DeletedInsurancesEntity deletedInsurancesEntity = new DeletedInsurancesEntity();

        deletedInsurancesEntity.setRemovalReason( source.getRemovalReason() );
        deletedInsurancesEntity.setRequestDate( source.getRequestDate() );
        deletedInsurancesEntity.setTodaysDate( source.getTodaysDate() );
        deletedInsurancesEntity.setDeleteDescription( source.getDeleteDescription() );
        deletedInsurancesEntity.setName( source.getName() );
        deletedInsurancesEntity.setInsurancesType( source.getInsurancesType() );
        deletedInsurancesEntity.setDescription( source.getDescription() );
        deletedInsurancesEntity.setMinimumInsuranceValue( source.getMinimumInsuranceValue() );
        deletedInsurancesEntity.setMaximumInsuranceValue( source.getMaximumInsuranceValue() );
        deletedInsurancesEntity.setMaximumPayoutValue( source.getMaximumPayoutValue() );

        return deletedInsurancesEntity;
    }

    @Override
    public DeletedInsurancesDTO toDTO(DeletedInsurancesEntity source) {
        if ( source == null ) {
            return null;
        }

        DeletedInsurancesDTO deletedInsurancesDTO = new DeletedInsurancesDTO();

        deletedInsurancesDTO.setTodaysDate( source.getTodaysDate() );
        deletedInsurancesDTO.setRemovalReason( source.getRemovalReason() );
        deletedInsurancesDTO.setRequestDate( source.getRequestDate() );
        deletedInsurancesDTO.setDeleteDescription( source.getDeleteDescription() );
        deletedInsurancesDTO.setName( source.getName() );
        deletedInsurancesDTO.setInsurancesType( source.getInsurancesType() );
        deletedInsurancesDTO.setDescription( source.getDescription() );
        deletedInsurancesDTO.setMinimumInsuranceValue( source.getMinimumInsuranceValue() );
        deletedInsurancesDTO.setMaximumInsuranceValue( source.getMaximumInsuranceValue() );
        deletedInsurancesDTO.setMaximumPayoutValue( source.getMaximumPayoutValue() );

        return deletedInsurancesDTO;
    }

    @Override
    public DeletedInsurancesDTO mergeToDeleteDTO(DeleteInsurancesReasonsDTO reasonsDto, InsurancesDTO insurancesDTO) {
        if ( reasonsDto == null && insurancesDTO == null ) {
            return null;
        }

        DeletedInsurancesDTO deletedInsurancesDTO = new DeletedInsurancesDTO();

        if ( reasonsDto != null ) {
            deletedInsurancesDTO.setRemovalReason( reasonsDto.getRemovalReason() );
            deletedInsurancesDTO.setRequestDate( reasonsDto.getRequestDate() );
            deletedInsurancesDTO.setDeleteDescription( reasonsDto.getDeleteDescription() );
        }
        if ( insurancesDTO != null ) {
            deletedInsurancesDTO.setIsAnnualPaymentRequired( insurancesDTO.getIsAnnualPaymentRequired() );
            deletedInsurancesDTO.setIsAutoRenewalRequired( insurancesDTO.getIsAutoRenewalRequired() );
            if ( insurancesDTO.getInsurancesId() != null ) {
                deletedInsurancesDTO.setInsurancesId( insurancesDTO.getInsurancesId() );
            }
            deletedInsurancesDTO.setName( insurancesDTO.getName() );
            deletedInsurancesDTO.setInsurancesType( insurancesDTO.getInsurancesType() );
            deletedInsurancesDTO.setDescription( insurancesDTO.getDescription() );
            if ( insurancesDTO.getMinimumInsuranceValue() != null ) {
                deletedInsurancesDTO.setMinimumInsuranceValue( insurancesDTO.getMinimumInsuranceValue() );
            }
            if ( insurancesDTO.getMaximumInsuranceValue() != null ) {
                deletedInsurancesDTO.setMaximumInsuranceValue( insurancesDTO.getMaximumInsuranceValue() );
            }
            if ( insurancesDTO.getMaximumPayoutValue() != null ) {
                deletedInsurancesDTO.setMaximumPayoutValue( insurancesDTO.getMaximumPayoutValue() );
            }
            if ( insurancesDTO.getRenewalPeriod() != null ) {
                deletedInsurancesDTO.setRenewalPeriod( insurancesDTO.getRenewalPeriod() );
            }
            if ( insurancesDTO.getMinimumPolicyTerm() != null ) {
                deletedInsurancesDTO.setMinimumPolicyTerm( insurancesDTO.getMinimumPolicyTerm() );
            }
        }

        return deletedInsurancesDTO;
    }

    @Override
    public void updatePartiesSearchDTO(DeletedInsurancesDTO source, DeletedInsurancesDTO target) {
        if ( source == null ) {
            return;
        }

        target.setIsAnnualPaymentRequired( source.getIsAnnualPaymentRequired() );
        target.setIsAutoRenewalRequired( source.getIsAutoRenewalRequired() );
        target.setTodaysDate( source.getTodaysDate() );
        target.setRemovalReason( source.getRemovalReason() );
        target.setRequestDate( source.getRequestDate() );
        target.setDeleteDescription( source.getDeleteDescription() );
        target.setInsurancesId( source.getInsurancesId() );
        target.setName( source.getName() );
        target.setInsurancesType( source.getInsurancesType() );
        target.setDescription( source.getDescription() );
        target.setMinimumInsuranceValue( source.getMinimumInsuranceValue() );
        target.setMaximumInsuranceValue( source.getMaximumInsuranceValue() );
        target.setMaximumPayoutValue( source.getMaximumPayoutValue() );
        target.setAnnualPaymentRequired( source.isAnnualPaymentRequired() );
        target.setRenewalPeriod( source.getRenewalPeriod() );
        target.setMinimumPolicyTerm( source.getMinimumPolicyTerm() );
        target.setAutoRenewalRequired( source.isAutoRenewalRequired() );
    }

    @Override
    public void updatePartiesEntity(DeletedInsurancesDTO source, DeletedInsurancesEntity target) {
        if ( source == null ) {
            return;
        }

        target.setRemovalReason( source.getRemovalReason() );
        target.setRequestDate( source.getRequestDate() );
        target.setTodaysDate( source.getTodaysDate() );
        target.setDeleteDescription( source.getDeleteDescription() );
        target.setName( source.getName() );
        target.setInsurancesType( source.getInsurancesType() );
        target.setDescription( source.getDescription() );
        target.setMinimumInsuranceValue( source.getMinimumInsuranceValue() );
        target.setMaximumInsuranceValue( source.getMaximumInsuranceValue() );
        target.setMaximumPayoutValue( source.getMaximumPayoutValue() );
    }
}
