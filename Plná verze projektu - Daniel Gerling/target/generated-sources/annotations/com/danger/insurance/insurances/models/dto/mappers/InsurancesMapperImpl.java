package com.danger.insurance.insurances.models.dto.mappers;

import com.danger.insurance.insurances.data.entities.InsurancesEntity;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T13:23:39+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class InsurancesMapperImpl implements InsurancesMapper {

    @Override
    public InsurancesEntity toEntity(InsurancesDTO source) {
        if ( source == null ) {
            return null;
        }

        InsurancesEntity insurancesEntity = new InsurancesEntity();

        insurancesEntity.setInsurancesId( source.getInsurancesId() );
        insurancesEntity.setName( source.getName() );
        insurancesEntity.setInsurancesType( source.getInsurancesType() );
        insurancesEntity.setDescription( source.getDescription() );
        insurancesEntity.setMinimumInsuranceValue( source.getMinimumInsuranceValue() );
        insurancesEntity.setMaximumInsuranceValue( source.getMaximumInsuranceValue() );
        insurancesEntity.setMaximumPayoutValue( source.getMaximumPayoutValue() );
        insurancesEntity.setIsAnnualPaymentRequired( source.getIsAnnualPaymentRequired() );
        insurancesEntity.setRenewalPeriod( source.getRenewalPeriod() );
        insurancesEntity.setMinimumPolicyTerm( source.getMinimumPolicyTerm() );
        insurancesEntity.setIsAutoRenewalRequired( source.getIsAutoRenewalRequired() );

        return insurancesEntity;
    }

    @Override
    public InsurancesDTO toDTO(InsurancesEntity source) {
        if ( source == null ) {
            return null;
        }

        InsurancesDTO insurancesDTO = new InsurancesDTO();

        insurancesDTO.setInsurancesId( source.getInsurancesId() );
        insurancesDTO.setName( source.getName() );
        insurancesDTO.setInsurancesType( source.getInsurancesType() );
        insurancesDTO.setDescription( source.getDescription() );
        insurancesDTO.setMinimumInsuranceValue( source.getMinimumInsuranceValue() );
        insurancesDTO.setMaximumInsuranceValue( source.getMaximumInsuranceValue() );
        insurancesDTO.setMaximumPayoutValue( source.getMaximumPayoutValue() );
        insurancesDTO.setIsAnnualPaymentRequired( source.getIsAnnualPaymentRequired() );
        insurancesDTO.setRenewalPeriod( source.getRenewalPeriod() );
        insurancesDTO.setMinimumPolicyTerm( source.getMinimumPolicyTerm() );
        insurancesDTO.setIsAutoRenewalRequired( source.getIsAutoRenewalRequired() );

        return insurancesDTO;
    }

    @Override
    public void updatePartiesSearchDTO(InsurancesDTO source, InsurancesDTO target) {
        if ( source == null ) {
            return;
        }

        target.setInsurancesId( source.getInsurancesId() );
        target.setName( source.getName() );
        target.setInsurancesType( source.getInsurancesType() );
        target.setDescription( source.getDescription() );
        target.setMinimumInsuranceValue( source.getMinimumInsuranceValue() );
        target.setMaximumInsuranceValue( source.getMaximumInsuranceValue() );
        target.setMaximumPayoutValue( source.getMaximumPayoutValue() );
        target.setIsAnnualPaymentRequired( source.getIsAnnualPaymentRequired() );
        target.setRenewalPeriod( source.getRenewalPeriod() );
        target.setMinimumPolicyTerm( source.getMinimumPolicyTerm() );
        target.setIsAutoRenewalRequired( source.getIsAutoRenewalRequired() );
    }

    @Override
    public void updatePartiesEntity(InsurancesDTO source, InsurancesEntity target) {
        if ( source == null ) {
            return;
        }

        target.setInsurancesId( source.getInsurancesId() );
        target.setName( source.getName() );
        target.setInsurancesType( source.getInsurancesType() );
        target.setDescription( source.getDescription() );
        target.setMinimumInsuranceValue( source.getMinimumInsuranceValue() );
        target.setMaximumInsuranceValue( source.getMaximumInsuranceValue() );
        target.setMaximumPayoutValue( source.getMaximumPayoutValue() );
        target.setIsAnnualPaymentRequired( source.getIsAnnualPaymentRequired() );
        target.setRenewalPeriod( source.getRenewalPeriod() );
        target.setMinimumPolicyTerm( source.getMinimumPolicyTerm() );
        target.setIsAutoRenewalRequired( source.getIsAutoRenewalRequired() );
    }
}
