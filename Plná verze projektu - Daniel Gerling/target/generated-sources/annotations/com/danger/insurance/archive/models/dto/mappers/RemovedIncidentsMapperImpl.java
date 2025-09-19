package com.danger.insurance.archive.models.dto.mappers;

import com.danger.insurance.archive.data.entities.RemovedIncidentCommentsEntity;
import com.danger.insurance.archive.data.entities.RemovedIncidentsEntity;
import com.danger.insurance.archive.models.dto.RemoveIncidentReasonsDTO;
import com.danger.insurance.archive.models.dto.RemovedIncidentsDTO;
import com.danger.insurance.incidents.models.dto.IncidentsDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-19T18:12:43+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class RemovedIncidentsMapperImpl implements RemovedIncidentsMapper {

    @Override
    public RemovedIncidentsEntity toEntity(RemovedIncidentsDTO source) {
        if ( source == null ) {
            return null;
        }

        RemovedIncidentsEntity removedIncidentsEntity = new RemovedIncidentsEntity();

        removedIncidentsEntity.setDeletedIncidentId( source.getDeletedIncidentId() );
        removedIncidentsEntity.setCaseNumber( source.getCaseNumber() );
        removedIncidentsEntity.setTitle( source.getTitle() );
        removedIncidentsEntity.setDescription( source.getDescription() );
        removedIncidentsEntity.setBirthNumber( source.getBirthNumber() );
        removedIncidentsEntity.setIncidentType( source.getIncidentType() );
        removedIncidentsEntity.setIncidentSubject( source.getIncidentSubject() );
        removedIncidentsEntity.setCurrentStatus( source.getCurrentStatus() );
        removedIncidentsEntity.setAccidentDate( source.getAccidentDate() );
        removedIncidentsEntity.setReportDate( source.getReportDate() );
        removedIncidentsEntity.setTodaysDate( source.getTodaysDate() );
        removedIncidentsEntity.setClosureDate( source.getClosureDate() );
        removedIncidentsEntity.setIncidentResolution( source.getIncidentResolution() );
        removedIncidentsEntity.setIncidentRemovalReason( source.getIncidentRemovalReason() );
        removedIncidentsEntity.setRemovalDescription( source.getRemovalDescription() );
        removedIncidentsEntity.setRemovalDate( source.getRemovalDate() );
        List<RemovedIncidentCommentsEntity> list = source.getDeletedComments();
        if ( list != null ) {
            removedIncidentsEntity.setDeletedComments( new ArrayList<RemovedIncidentCommentsEntity>( list ) );
        }

        return removedIncidentsEntity;
    }

    @Override
    public RemovedIncidentsEntity removedIncidentsToEntity(RemovedIncidentsDTO source) {
        if ( source == null ) {
            return null;
        }

        RemovedIncidentsEntity removedIncidentsEntity = new RemovedIncidentsEntity();

        removedIncidentsEntity.setDeletedIncidentId( source.getDeletedIncidentId() );
        removedIncidentsEntity.setCaseNumber( source.getCaseNumber() );
        removedIncidentsEntity.setTitle( source.getTitle() );
        removedIncidentsEntity.setDescription( source.getDescription() );
        removedIncidentsEntity.setBirthNumber( source.getBirthNumber() );
        removedIncidentsEntity.setIncidentType( source.getIncidentType() );
        removedIncidentsEntity.setIncidentSubject( source.getIncidentSubject() );
        removedIncidentsEntity.setCurrentStatus( source.getCurrentStatus() );
        removedIncidentsEntity.setAccidentDate( source.getAccidentDate() );
        removedIncidentsEntity.setReportDate( source.getReportDate() );
        removedIncidentsEntity.setTodaysDate( source.getTodaysDate() );
        removedIncidentsEntity.setClosureDate( source.getClosureDate() );
        removedIncidentsEntity.setIncidentResolution( source.getIncidentResolution() );
        removedIncidentsEntity.setIncidentRemovalReason( source.getIncidentRemovalReason() );
        removedIncidentsEntity.setRemovalDescription( source.getRemovalDescription() );
        removedIncidentsEntity.setRemovalDate( source.getRemovalDate() );
        List<RemovedIncidentCommentsEntity> list = source.getDeletedComments();
        if ( list != null ) {
            removedIncidentsEntity.setDeletedComments( new ArrayList<RemovedIncidentCommentsEntity>( list ) );
        }

        return removedIncidentsEntity;
    }

    @Override
    public RemovedIncidentsDTO toDTO(RemovedIncidentsEntity source) {
        if ( source == null ) {
            return null;
        }

        RemovedIncidentsDTO removedIncidentsDTO = new RemovedIncidentsDTO();

        removedIncidentsDTO.setDeletedIncidentId( source.getDeletedIncidentId() );
        removedIncidentsDTO.setCaseNumber( source.getCaseNumber() );
        removedIncidentsDTO.setTitle( source.getTitle() );
        removedIncidentsDTO.setDescription( source.getDescription() );
        removedIncidentsDTO.setBirthNumber( source.getBirthNumber() );
        removedIncidentsDTO.setIncidentType( source.getIncidentType() );
        removedIncidentsDTO.setIncidentSubject( source.getIncidentSubject() );
        removedIncidentsDTO.setCurrentStatus( source.getCurrentStatus() );
        removedIncidentsDTO.setAccidentDate( source.getAccidentDate() );
        removedIncidentsDTO.setReportDate( source.getReportDate() );
        removedIncidentsDTO.setTodaysDate( source.getTodaysDate() );
        removedIncidentsDTO.setClosureDate( source.getClosureDate() );
        removedIncidentsDTO.setIncidentResolution( source.getIncidentResolution() );
        removedIncidentsDTO.setIncidentRemovalReason( source.getIncidentRemovalReason() );
        removedIncidentsDTO.setRemovalDescription( source.getRemovalDescription() );
        removedIncidentsDTO.setRemovalDate( source.getRemovalDate() );
        List<RemovedIncidentCommentsEntity> list = source.getDeletedComments();
        if ( list != null ) {
            removedIncidentsDTO.setDeletedComments( new ArrayList<RemovedIncidentCommentsEntity>( list ) );
        }

        return removedIncidentsDTO;
    }

    @Override
    public RemovedIncidentsDTO mergeToRemovedIncidentsDTO(IncidentsDTO incidentsDTO, RemoveIncidentReasonsDTO removeIncidentReasonsDTO) {
        if ( incidentsDTO == null && removeIncidentReasonsDTO == null ) {
            return null;
        }

        RemovedIncidentsDTO removedIncidentsDTO = new RemovedIncidentsDTO();

        if ( incidentsDTO != null ) {
            removedIncidentsDTO.setCaseNumber( incidentsDTO.getCaseNumber() );
            removedIncidentsDTO.setTitle( incidentsDTO.getTitle() );
            removedIncidentsDTO.setDescription( incidentsDTO.getDescription() );
            removedIncidentsDTO.setBirthNumber( incidentsDTO.getBirthNumber() );
            removedIncidentsDTO.setIncidentType( incidentsDTO.getIncidentType() );
            removedIncidentsDTO.setIncidentSubject( incidentsDTO.getIncidentSubject() );
            removedIncidentsDTO.setCurrentStatus( incidentsDTO.getCurrentStatus() );
            removedIncidentsDTO.setAccidentDate( incidentsDTO.getAccidentDate() );
            removedIncidentsDTO.setReportDate( incidentsDTO.getReportDate() );
            removedIncidentsDTO.setTodaysDate( incidentsDTO.getTodaysDate() );
            removedIncidentsDTO.setClosureDate( incidentsDTO.getClosureDate() );
            removedIncidentsDTO.setIncidentResolution( incidentsDTO.getIncidentResolution() );
        }
        if ( removeIncidentReasonsDTO != null ) {
            removedIncidentsDTO.setIncidentRemovalReason( removeIncidentReasonsDTO.getIncidentRemovalReason() );
            removedIncidentsDTO.setRemovalDescription( removeIncidentReasonsDTO.getRemovalDescription() );
        }

        return removedIncidentsDTO;
    }

    @Override
    public void updateRemovedIncidentsDTO(RemovedIncidentsDTO source, RemovedIncidentsDTO target) {
        if ( source == null ) {
            return;
        }

        target.setDeletedIncidentId( source.getDeletedIncidentId() );
        target.setCaseNumber( source.getCaseNumber() );
        target.setTitle( source.getTitle() );
        target.setDescription( source.getDescription() );
        target.setBirthNumber( source.getBirthNumber() );
        target.setIncidentType( source.getIncidentType() );
        target.setIncidentSubject( source.getIncidentSubject() );
        target.setCurrentStatus( source.getCurrentStatus() );
        target.setAccidentDate( source.getAccidentDate() );
        target.setReportDate( source.getReportDate() );
        target.setTodaysDate( source.getTodaysDate() );
        target.setClosureDate( source.getClosureDate() );
        target.setIncidentResolution( source.getIncidentResolution() );
        target.setIncidentRemovalReason( source.getIncidentRemovalReason() );
        target.setRemovalDescription( source.getRemovalDescription() );
        target.setRemovalDate( source.getRemovalDate() );
        if ( target.getDeletedComments() != null ) {
            List<RemovedIncidentCommentsEntity> list = source.getDeletedComments();
            if ( list != null ) {
                target.getDeletedComments().clear();
                target.getDeletedComments().addAll( list );
            }
            else {
                target.setDeletedComments( null );
            }
        }
        else {
            List<RemovedIncidentCommentsEntity> list = source.getDeletedComments();
            if ( list != null ) {
                target.setDeletedComments( new ArrayList<RemovedIncidentCommentsEntity>( list ) );
            }
        }
    }

    @Override
    public void updateRemovedIncidentsEntity(RemovedIncidentsDTO source, RemovedIncidentsEntity target) {
        if ( source == null ) {
            return;
        }

        target.setDeletedIncidentId( source.getDeletedIncidentId() );
        target.setCaseNumber( source.getCaseNumber() );
        target.setTitle( source.getTitle() );
        target.setDescription( source.getDescription() );
        target.setBirthNumber( source.getBirthNumber() );
        target.setIncidentType( source.getIncidentType() );
        target.setIncidentSubject( source.getIncidentSubject() );
        target.setCurrentStatus( source.getCurrentStatus() );
        target.setAccidentDate( source.getAccidentDate() );
        target.setReportDate( source.getReportDate() );
        target.setTodaysDate( source.getTodaysDate() );
        target.setClosureDate( source.getClosureDate() );
        target.setIncidentResolution( source.getIncidentResolution() );
        target.setIncidentRemovalReason( source.getIncidentRemovalReason() );
        target.setRemovalDescription( source.getRemovalDescription() );
        target.setRemovalDate( source.getRemovalDate() );
        if ( target.getDeletedComments() != null ) {
            List<RemovedIncidentCommentsEntity> list = source.getDeletedComments();
            if ( list != null ) {
                target.getDeletedComments().clear();
                target.getDeletedComments().addAll( list );
            }
            else {
                target.setDeletedComments( null );
            }
        }
        else {
            List<RemovedIncidentCommentsEntity> list = source.getDeletedComments();
            if ( list != null ) {
                target.setDeletedComments( new ArrayList<RemovedIncidentCommentsEntity>( list ) );
            }
        }
    }
}
