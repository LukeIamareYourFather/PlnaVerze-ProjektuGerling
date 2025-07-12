package com.danger.insurance.models.dto.mappers;

import com.danger.insurance.data.entities.DeletedInsurancesEntity;
import com.danger.insurance.models.dto.insurances.DeleteInsurancesReasonsDTO;
import com.danger.insurance.models.dto.insurances.DeletedInsurancesDTO;
import com.danger.insurance.models.dto.insurances.InsurancesDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-12T02:27:19+0200",
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

        deletedInsurancesEntity.setTodaysDate( source.getTodaysDate() );
        deletedInsurancesEntity.setRemovalReason( source.getRemovalReason() );
        deletedInsurancesEntity.setRequestDate( source.getRequestDate() );
        deletedInsurancesEntity.setDeleteDescription( source.getDeleteDescription() );
        deletedInsurancesEntity.setIfToRemoveAllContracts( source.getIfToRemoveAllContracts() );
        deletedInsurancesEntity.setName( source.getName() );
        deletedInsurancesEntity.setInsurancesType( source.getInsurancesType() );
        deletedInsurancesEntity.setDescription( source.getDescription() );
        deletedInsurancesEntity.setMinimumInsuranceValue( source.getMinimumInsuranceValue() );
        deletedInsurancesEntity.setMaximumInsuranceValue( source.getMaximumInsuranceValue() );
        deletedInsurancesEntity.setMaximumPayoutValue( source.getMaximumPayoutValue() );
        deletedInsurancesEntity.setAnnualPaymentRequired( source.isAnnualPaymentRequired() );
        deletedInsurancesEntity.setRenewalPeriod( source.getRenewalPeriod() );
        deletedInsurancesEntity.setMinimumPolicyTerm( source.getMinimumPolicyTerm() );
        deletedInsurancesEntity.setAutoRenewalRequired( source.isAutoRenewalRequired() );

        return deletedInsurancesEntity;
    }

    @Override
    public DeletedInsurancesEntity partiesCreateToEntity(DeletedInsurancesDTO source) {
        if ( source == null ) {
            return null;
        }

        DeletedInsurancesEntity deletedInsurancesEntity = new DeletedInsurancesEntity();

        deletedInsurancesEntity.setTodaysDate( source.getTodaysDate() );
        deletedInsurancesEntity.setRemovalReason( source.getRemovalReason() );
        deletedInsurancesEntity.setRequestDate( source.getRequestDate() );
        deletedInsurancesEntity.setDeleteDescription( source.getDeleteDescription() );
        deletedInsurancesEntity.setIfToRemoveAllContracts( source.getIfToRemoveAllContracts() );
        deletedInsurancesEntity.setName( source.getName() );
        deletedInsurancesEntity.setInsurancesType( source.getInsurancesType() );
        deletedInsurancesEntity.setDescription( source.getDescription() );
        deletedInsurancesEntity.setMinimumInsuranceValue( source.getMinimumInsuranceValue() );
        deletedInsurancesEntity.setMaximumInsuranceValue( source.getMaximumInsuranceValue() );
        deletedInsurancesEntity.setMaximumPayoutValue( source.getMaximumPayoutValue() );
        deletedInsurancesEntity.setAnnualPaymentRequired( source.isAnnualPaymentRequired() );
        deletedInsurancesEntity.setRenewalPeriod( source.getRenewalPeriod() );
        deletedInsurancesEntity.setMinimumPolicyTerm( source.getMinimumPolicyTerm() );
        deletedInsurancesEntity.setAutoRenewalRequired( source.isAutoRenewalRequired() );

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
        deletedInsurancesDTO.setIfToRemoveAllContracts( source.isIfToRemoveAllContracts() );
        deletedInsurancesDTO.setName( source.getName() );
        deletedInsurancesDTO.setInsurancesType( source.getInsurancesType() );
        deletedInsurancesDTO.setDescription( source.getDescription() );
        deletedInsurancesDTO.setMinimumInsuranceValue( source.getMinimumInsuranceValue() );
        deletedInsurancesDTO.setMaximumInsuranceValue( source.getMaximumInsuranceValue() );
        deletedInsurancesDTO.setMaximumPayoutValue( source.getMaximumPayoutValue() );
        deletedInsurancesDTO.setAnnualPaymentRequired( source.isAnnualPaymentRequired() );
        deletedInsurancesDTO.setRenewalPeriod( source.getRenewalPeriod() );
        deletedInsurancesDTO.setMinimumPolicyTerm( source.getMinimumPolicyTerm() );
        deletedInsurancesDTO.setAutoRenewalRequired( source.isAutoRenewalRequired() );

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
            deletedInsurancesDTO.setIfToRemoveAllContracts( reasonsDto.getIfToRemoveAllContracts() );
        }
        if ( insurancesDTO != null ) {
            deletedInsurancesDTO.setIsAnnualPaymentRequired( insurancesDTO.getIsAnnualPaymentRequired() );
            deletedInsurancesDTO.setIsAutoRenewalRequired( insurancesDTO.getIsAutoRenewalRequired() );
            deletedInsurancesDTO.setInsurancesId( insurancesDTO.getInsurancesId() );
            deletedInsurancesDTO.setName( insurancesDTO.getName() );
            deletedInsurancesDTO.setInsurancesType( insurancesDTO.getInsurancesType() );
            deletedInsurancesDTO.setDescription( insurancesDTO.getDescription() );
            deletedInsurancesDTO.setMinimumInsuranceValue( insurancesDTO.getMinimumInsuranceValue() );
            deletedInsurancesDTO.setMaximumInsuranceValue( insurancesDTO.getMaximumInsuranceValue() );
            deletedInsurancesDTO.setMaximumPayoutValue( insurancesDTO.getMaximumPayoutValue() );
            deletedInsurancesDTO.setRenewalPeriod( insurancesDTO.getRenewalPeriod() );
            deletedInsurancesDTO.setMinimumPolicyTerm( insurancesDTO.getMinimumPolicyTerm() );
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
        target.setIfToRemoveAllContracts( source.getIfToRemoveAllContracts() );
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

        target.setTodaysDate( source.getTodaysDate() );
        target.setRemovalReason( source.getRemovalReason() );
        target.setRequestDate( source.getRequestDate() );
        target.setDeleteDescription( source.getDeleteDescription() );
        target.setIfToRemoveAllContracts( source.getIfToRemoveAllContracts() );
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
}
