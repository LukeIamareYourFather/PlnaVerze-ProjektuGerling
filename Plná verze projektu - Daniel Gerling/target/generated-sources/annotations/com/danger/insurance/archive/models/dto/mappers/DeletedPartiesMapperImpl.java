package com.danger.insurance.archive.models.dto.mappers;

import com.danger.insurance.archive.data.entities.DeletedPartiesEntity;
import com.danger.insurance.archive.models.dto.DeletedPartiesDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.dto.PartiesReasonsFormDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-19T18:12:43+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class DeletedPartiesMapperImpl implements DeletedPartiesMapper {

    @Override
    public DeletedPartiesEntity toEntity(DeletedPartiesDTO source) {
        if ( source == null ) {
            return null;
        }

        DeletedPartiesEntity deletedPartiesEntity = new DeletedPartiesEntity();

        deletedPartiesEntity.setDeleteReasonId( source.getDeleteReasonId() );
        deletedPartiesEntity.setRemovalReason( source.getRemovalReason() );
        deletedPartiesEntity.setDateOfRequest( source.getDateOfRequest() );
        deletedPartiesEntity.setTodaysDate( source.getTodaysDate() );
        deletedPartiesEntity.setAdditionalInformation( source.getAdditionalInformation() );
        deletedPartiesEntity.setPartyId( source.getPartyId() );
        deletedPartiesEntity.setName( source.getName() );
        deletedPartiesEntity.setSurname( source.getSurname() );
        deletedPartiesEntity.setBirthDay( source.getBirthDay() );
        deletedPartiesEntity.setBirthNumber( source.getBirthNumber() );
        deletedPartiesEntity.setEmail( source.getEmail() );
        deletedPartiesEntity.setPhoneNumber( source.getPhoneNumber() );
        deletedPartiesEntity.setStreet( source.getStreet() );
        deletedPartiesEntity.setCity( source.getCity() );
        deletedPartiesEntity.setZipCode( source.getZipCode() );

        return deletedPartiesEntity;
    }

    @Override
    public DeletedPartiesEntity partiesCreateToEntity(DeletedPartiesDTO source) {
        if ( source == null ) {
            return null;
        }

        DeletedPartiesEntity deletedPartiesEntity = new DeletedPartiesEntity();

        deletedPartiesEntity.setDeleteReasonId( source.getDeleteReasonId() );
        deletedPartiesEntity.setRemovalReason( source.getRemovalReason() );
        deletedPartiesEntity.setDateOfRequest( source.getDateOfRequest() );
        deletedPartiesEntity.setTodaysDate( source.getTodaysDate() );
        deletedPartiesEntity.setAdditionalInformation( source.getAdditionalInformation() );
        deletedPartiesEntity.setPartyId( source.getPartyId() );
        deletedPartiesEntity.setName( source.getName() );
        deletedPartiesEntity.setSurname( source.getSurname() );
        deletedPartiesEntity.setBirthDay( source.getBirthDay() );
        deletedPartiesEntity.setBirthNumber( source.getBirthNumber() );
        deletedPartiesEntity.setEmail( source.getEmail() );
        deletedPartiesEntity.setPhoneNumber( source.getPhoneNumber() );
        deletedPartiesEntity.setStreet( source.getStreet() );
        deletedPartiesEntity.setCity( source.getCity() );
        deletedPartiesEntity.setZipCode( source.getZipCode() );

        return deletedPartiesEntity;
    }

    @Override
    public DeletedPartiesDTO toDTO(DeletedPartiesEntity source) {
        if ( source == null ) {
            return null;
        }

        DeletedPartiesDTO deletedPartiesDTO = new DeletedPartiesDTO();

        deletedPartiesDTO.setBirthDay( source.getBirthDay() );
        deletedPartiesDTO.setName( source.getName() );
        deletedPartiesDTO.setSurname( source.getSurname() );
        deletedPartiesDTO.setBirthNumber( source.getBirthNumber() );
        deletedPartiesDTO.setEmail( source.getEmail() );
        deletedPartiesDTO.setPhoneNumber( source.getPhoneNumber() );
        deletedPartiesDTO.setStreet( source.getStreet() );
        deletedPartiesDTO.setCity( source.getCity() );
        deletedPartiesDTO.setZipCode( source.getZipCode() );
        deletedPartiesDTO.setPartyId( source.getPartyId() );
        deletedPartiesDTO.setDeleteReasonId( source.getDeleteReasonId() );
        deletedPartiesDTO.setRemovalReason( source.getRemovalReason() );
        deletedPartiesDTO.setDateOfRequest( source.getDateOfRequest() );
        deletedPartiesDTO.setTodaysDate( source.getTodaysDate() );
        deletedPartiesDTO.setAdditionalInformation( source.getAdditionalInformation() );

        return deletedPartiesDTO;
    }

    @Override
    public DeletedPartiesDTO mergeToDeleteDTO(PartiesReasonsFormDTO reasonsDto, PartiesDetailsDTO partiesDTO) {
        if ( reasonsDto == null && partiesDTO == null ) {
            return null;
        }

        DeletedPartiesDTO deletedPartiesDTO = new DeletedPartiesDTO();

        if ( reasonsDto != null ) {
            deletedPartiesDTO.setDeleteReasonId( reasonsDto.getDeleteReasonId() );
            deletedPartiesDTO.setRemovalReason( reasonsDto.getRemovalReason() );
            deletedPartiesDTO.setDateOfRequest( reasonsDto.getDateOfRequest() );
            deletedPartiesDTO.setTodaysDate( reasonsDto.getTodaysDate() );
            deletedPartiesDTO.setAdditionalInformation( reasonsDto.getAdditionalInformation() );
        }
        if ( partiesDTO != null ) {
            deletedPartiesDTO.setBirthDay( partiesDTO.getBirthDay() );
            deletedPartiesDTO.setName( partiesDTO.getName() );
            deletedPartiesDTO.setSurname( partiesDTO.getSurname() );
            deletedPartiesDTO.setBirthNumber( partiesDTO.getBirthNumber() );
            deletedPartiesDTO.setEmail( partiesDTO.getEmail() );
            deletedPartiesDTO.setPhoneNumber( partiesDTO.getPhoneNumber() );
            deletedPartiesDTO.setStreet( partiesDTO.getStreet() );
            deletedPartiesDTO.setCity( partiesDTO.getCity() );
            deletedPartiesDTO.setZipCode( partiesDTO.getZipCode() );
            deletedPartiesDTO.setPartyId( partiesDTO.getPartyId() );
        }

        return deletedPartiesDTO;
    }

    @Override
    public void updatePartiesSearchDTO(DeletedPartiesDTO source, DeletedPartiesDTO target) {
        if ( source == null ) {
            return;
        }

        target.setBirthDay( source.getBirthDay() );
        target.setName( source.getName() );
        target.setSurname( source.getSurname() );
        target.setBirthNumber( source.getBirthNumber() );
        target.setEmail( source.getEmail() );
        target.setPhoneNumber( source.getPhoneNumber() );
        target.setStreet( source.getStreet() );
        target.setCity( source.getCity() );
        target.setZipCode( source.getZipCode() );
        target.setPartyId( source.getPartyId() );
        target.setDeleteReasonId( source.getDeleteReasonId() );
        target.setRemovalReason( source.getRemovalReason() );
        target.setDateOfRequest( source.getDateOfRequest() );
        target.setTodaysDate( source.getTodaysDate() );
        target.setAdditionalInformation( source.getAdditionalInformation() );
    }

    @Override
    public void updatePartiesEntity(DeletedPartiesDTO source, DeletedPartiesEntity target) {
        if ( source == null ) {
            return;
        }

        target.setDeleteReasonId( source.getDeleteReasonId() );
        target.setRemovalReason( source.getRemovalReason() );
        target.setDateOfRequest( source.getDateOfRequest() );
        target.setTodaysDate( source.getTodaysDate() );
        target.setAdditionalInformation( source.getAdditionalInformation() );
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
