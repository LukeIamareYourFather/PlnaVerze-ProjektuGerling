package com.danger.insurance.parties.models.dto.mappers;

import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-13T06:24:59+0200",
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
    }
}
