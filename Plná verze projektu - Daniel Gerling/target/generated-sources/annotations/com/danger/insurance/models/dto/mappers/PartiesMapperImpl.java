package com.danger.insurance.models.dto.mappers;

import com.danger.insurance.data.entities.PartiesEntity;
import com.danger.insurance.models.dto.parties.PartiesCreateDTO;
import com.danger.insurance.models.dto.parties.PartiesDetailsDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-10T22:44:00+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class PartiesMapperImpl implements PartiesMapper {

    @Override
    public PartiesEntity toEntity(PartiesDetailsDTO source) {
        if ( source == null ) {
            return null;
        }

        PartiesEntity partiesEntity = new PartiesEntity();

        partiesEntity.setPartyId( source.getPartyId() );
        partiesEntity.setName( source.getName() );
        partiesEntity.setSurname( source.getSurname() );
        partiesEntity.setBirthDay( source.getBirthDay() );
        partiesEntity.setBirthNumber( source.getBirthNumber() );
        partiesEntity.setEmail( source.getEmail() );
        partiesEntity.setPhoneNumber( source.getPhoneNumber() );
        partiesEntity.setStreet( source.getStreet() );
        partiesEntity.setCity( source.getCity() );
        partiesEntity.setZipCode( source.getZipCode() );
        partiesEntity.setPartyStatus( source.getPartyStatus() );
        partiesEntity.setInsuranceType( source.getInsuranceType() );
        partiesEntity.setContractNumber( source.getContractNumber() );

        return partiesEntity;
    }

    @Override
    public PartiesEntity partiesCreateToEntity(PartiesCreateDTO source) {
        if ( source == null ) {
            return null;
        }

        PartiesEntity partiesEntity = new PartiesEntity();

        partiesEntity.setName( source.getName() );
        partiesEntity.setSurname( source.getSurname() );
        partiesEntity.setBirthDay( source.getBirthDay() );
        partiesEntity.setBirthNumber( source.getBirthNumber() );
        partiesEntity.setEmail( source.getEmail() );
        partiesEntity.setPhoneNumber( source.getPhoneNumber() );
        partiesEntity.setStreet( source.getStreet() );
        partiesEntity.setCity( source.getCity() );
        partiesEntity.setZipCode( source.getZipCode() );
        partiesEntity.setPartyStatus( source.getPartyStatus() );
        partiesEntity.setInsuranceType( source.getInsuranceType() );
        partiesEntity.setContractNumber( source.getContractNumber() );

        return partiesEntity;
    }

    @Override
    public PartiesDetailsDTO toDTO(PartiesEntity source) {
        if ( source == null ) {
            return null;
        }

        PartiesDetailsDTO partiesDetailsDTO = new PartiesDetailsDTO();

        partiesDetailsDTO.setPartyId( source.getPartyId() );
        partiesDetailsDTO.setName( source.getName() );
        partiesDetailsDTO.setSurname( source.getSurname() );
        partiesDetailsDTO.setBirthDay( source.getBirthDay() );
        partiesDetailsDTO.setBirthNumber( source.getBirthNumber() );
        partiesDetailsDTO.setEmail( source.getEmail() );
        partiesDetailsDTO.setPhoneNumber( source.getPhoneNumber() );
        partiesDetailsDTO.setStreet( source.getStreet() );
        partiesDetailsDTO.setCity( source.getCity() );
        partiesDetailsDTO.setZipCode( source.getZipCode() );
        partiesDetailsDTO.setPartyStatus( source.getPartyStatus() );
        partiesDetailsDTO.setInsuranceType( source.getInsuranceType() );
        partiesDetailsDTO.setContractNumber( source.getContractNumber() );

        return partiesDetailsDTO;
    }

    @Override
    public void updatePartiesSearchDTO(PartiesDetailsDTO source, PartiesDetailsDTO target) {
        if ( source == null ) {
            return;
        }

        target.setPartyId( source.getPartyId() );
        target.setName( source.getName() );
        target.setSurname( source.getSurname() );
        target.setBirthDay( source.getBirthDay() );
        target.setBirthNumber( source.getBirthNumber() );
        target.setEmail( source.getEmail() );
        target.setPhoneNumber( source.getPhoneNumber() );
        target.setStreet( source.getStreet() );
        target.setCity( source.getCity() );
        target.setZipCode( source.getZipCode() );
        target.setPartyStatus( source.getPartyStatus() );
        target.setInsuranceType( source.getInsuranceType() );
        target.setContractNumber( source.getContractNumber() );
    }

    @Override
    public void updatePartiesEntity(PartiesDetailsDTO source, PartiesEntity target) {
        if ( source == null ) {
            return;
        }

        target.setPartyId( source.getPartyId() );
        target.setName( source.getName() );
        target.setSurname( source.getSurname() );
        target.setBirthDay( source.getBirthDay() );
        target.setBirthNumber( source.getBirthNumber() );
        target.setEmail( source.getEmail() );
        target.setPhoneNumber( source.getPhoneNumber() );
        target.setStreet( source.getStreet() );
        target.setCity( source.getCity() );
        target.setZipCode( source.getZipCode() );
        target.setPartyStatus( source.getPartyStatus() );
        target.setInsuranceType( source.getInsuranceType() );
        target.setContractNumber( source.getContractNumber() );
    }
}
