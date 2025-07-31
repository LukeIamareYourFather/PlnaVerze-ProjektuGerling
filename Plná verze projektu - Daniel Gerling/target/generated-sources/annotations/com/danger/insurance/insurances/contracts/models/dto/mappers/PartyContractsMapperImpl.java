package com.danger.insurance.insurances.contracts.models.dto.mappers;

import com.danger.insurance.insurances.contracts.data.entities.PartyContractsEntity;
import com.danger.insurance.insurances.contracts.models.dto.PartyContractsDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-31T23:31:48+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class PartyContractsMapperImpl implements PartyContractsMapper {

    @Override
    public PartyContractsEntity toEntity(PartyContractsDTO source) {
        if ( source == null ) {
            return null;
        }

        PartyContractsEntity partyContractsEntity = new PartyContractsEntity();

        partyContractsEntity.setId( source.getId() );
        partyContractsEntity.setPartyEntity( source.getPartyEntity() );
        partyContractsEntity.setContractEntity( source.getContractEntity() );
        partyContractsEntity.setContractRole( source.getContractRole() );
        partyContractsEntity.setTodaysDate( source.getTodaysDate() );

        return partyContractsEntity;
    }

    @Override
    public PartyContractsDTO toDTO(PartyContractsEntity source) {
        if ( source == null ) {
            return null;
        }

        PartyContractsDTO partyContractsDTO = new PartyContractsDTO();

        partyContractsDTO.setId( source.getId() );
        partyContractsDTO.setPartyEntity( source.getPartyEntity() );
        partyContractsDTO.setContractEntity( source.getContractEntity() );
        partyContractsDTO.setContractRole( source.getContractRole() );
        partyContractsDTO.setTodaysDate( source.getTodaysDate() );

        return partyContractsDTO;
    }

    @Override
    public void updatePartiesSearchDTO(PartyContractsDTO source, PartyContractsDTO target) {
        if ( source == null ) {
            return;
        }

        target.setId( source.getId() );
        target.setPartyEntity( source.getPartyEntity() );
        target.setContractEntity( source.getContractEntity() );
        target.setContractRole( source.getContractRole() );
        target.setTodaysDate( source.getTodaysDate() );
    }

    @Override
    public void updatePartiesEntity(PartyContractsDTO source, PartyContractsEntity target) {
        if ( source == null ) {
            return;
        }

        target.setId( source.getId() );
        target.setPartyEntity( source.getPartyEntity() );
        target.setContractEntity( source.getContractEntity() );
        target.setContractRole( source.getContractRole() );
        target.setTodaysDate( source.getTodaysDate() );
    }
}
