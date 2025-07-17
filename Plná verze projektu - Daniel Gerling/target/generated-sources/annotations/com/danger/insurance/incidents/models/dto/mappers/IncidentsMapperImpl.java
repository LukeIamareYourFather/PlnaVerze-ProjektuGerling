package com.danger.insurance.incidents.models.dto.mappers;

import com.danger.insurance.incidents.data.entities.IncidentCommentsEntity;
import com.danger.insurance.incidents.data.entities.IncidentsEntity;
import com.danger.insurance.incidents.models.dto.IncidentsDTO;
import com.danger.insurance.incidents.models.dto.get.IncidentDetailsGetDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsCreatePostDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-17T07:25:27+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class IncidentsMapperImpl implements IncidentsMapper {

    @Override
    public IncidentsEntity toEntity(IncidentsDTO source) {
        if ( source == null ) {
            return null;
        }

        IncidentsEntity incidentsEntity = new IncidentsEntity();

        incidentsEntity.setIncidentId( source.getIncidentId() );
        incidentsEntity.setCaseNumber( source.getCaseNumber() );
        incidentsEntity.setTitle( source.getTitle() );
        incidentsEntity.setDescription( source.getDescription() );
        incidentsEntity.setBirthNumber( source.getBirthNumber() );
        incidentsEntity.setIncidentType( source.getIncidentType() );
        incidentsEntity.setIncidentSubject( source.getIncidentSubject() );
        incidentsEntity.setCurrentStatus( source.getCurrentStatus() );
        incidentsEntity.setAccidentDate( source.getAccidentDate() );
        incidentsEntity.setReportDate( source.getReportDate() );
        incidentsEntity.setTodaysDate( source.getTodaysDate() );
        incidentsEntity.setClosureDate( source.getClosureDate() );
        List<IncidentCommentsEntity> list = source.getComments();
        if ( list != null ) {
            incidentsEntity.setComments( new ArrayList<IncidentCommentsEntity>( list ) );
        }
        incidentsEntity.setIncidentResolution( source.getIncidentResolution() );

        return incidentsEntity;
    }

    @Override
    public IncidentsEntity incidentCreateToEntity(IncidentsDTO source) {
        if ( source == null ) {
            return null;
        }

        IncidentsEntity incidentsEntity = new IncidentsEntity();

        incidentsEntity.setIncidentId( source.getIncidentId() );
        incidentsEntity.setCaseNumber( source.getCaseNumber() );
        incidentsEntity.setTitle( source.getTitle() );
        incidentsEntity.setDescription( source.getDescription() );
        incidentsEntity.setBirthNumber( source.getBirthNumber() );
        incidentsEntity.setIncidentType( source.getIncidentType() );
        incidentsEntity.setIncidentSubject( source.getIncidentSubject() );
        incidentsEntity.setCurrentStatus( source.getCurrentStatus() );
        incidentsEntity.setAccidentDate( source.getAccidentDate() );
        incidentsEntity.setReportDate( source.getReportDate() );
        incidentsEntity.setTodaysDate( source.getTodaysDate() );
        incidentsEntity.setClosureDate( source.getClosureDate() );
        List<IncidentCommentsEntity> list = source.getComments();
        if ( list != null ) {
            incidentsEntity.setComments( new ArrayList<IncidentCommentsEntity>( list ) );
        }
        incidentsEntity.setIncidentResolution( source.getIncidentResolution() );

        return incidentsEntity;
    }

    @Override
    public IncidentDetailsGetDTO toDetailsToDTO(IncidentsEntity source) {
        if ( source == null ) {
            return null;
        }

        IncidentDetailsGetDTO incidentDetailsGetDTO = new IncidentDetailsGetDTO();

        incidentDetailsGetDTO.setCaseNumber( source.getCaseNumber() );
        incidentDetailsGetDTO.setTitle( source.getTitle() );
        incidentDetailsGetDTO.setDescription( source.getDescription() );
        incidentDetailsGetDTO.setBirthNumber( source.getBirthNumber() );
        incidentDetailsGetDTO.setIncidentType( source.getIncidentType() );
        incidentDetailsGetDTO.setIncidentSubject( source.getIncidentSubject() );
        incidentDetailsGetDTO.setCurrentStatus( source.getCurrentStatus() );
        incidentDetailsGetDTO.setAccidentDate( source.getAccidentDate() );
        incidentDetailsGetDTO.setReportDate( source.getReportDate() );
        incidentDetailsGetDTO.setTodaysDate( source.getTodaysDate() );
        incidentDetailsGetDTO.setClosureDate( source.getClosureDate() );
        List<IncidentCommentsEntity> list = source.getComments();
        if ( list != null ) {
            incidentDetailsGetDTO.setComments( new ArrayList<IncidentCommentsEntity>( list ) );
        }

        return incidentDetailsGetDTO;
    }

    @Override
    public IncidentsDTO mergeToIncidentsDTO(IncidentsDTO incidentsDTO, IncidentsCreatePostDTO incidentsCreateDTO) {
        if ( incidentsCreateDTO == null ) {
            return incidentsDTO;
        }

        incidentsDTO.setCaseNumber( incidentsCreateDTO.getCaseNumber() );
        incidentsDTO.setTitle( incidentsCreateDTO.getTitle() );
        incidentsDTO.setDescription( incidentsCreateDTO.getDescription() );
        incidentsDTO.setBirthNumber( incidentsCreateDTO.getBirthNumber() );
        incidentsDTO.setIncidentType( incidentsCreateDTO.getIncidentType() );
        incidentsDTO.setIncidentSubject( incidentsCreateDTO.getIncidentSubject() );
        incidentsDTO.setAccidentDate( incidentsCreateDTO.getAccidentDate() );
        incidentsDTO.setReportDate( incidentsCreateDTO.getReportDate() );
        incidentsDTO.setTodaysDate( incidentsCreateDTO.getTodaysDate() );

        return incidentsDTO;
    }

    @Override
    public IncidentsDTO toDTO(IncidentsEntity source) {
        if ( source == null ) {
            return null;
        }

        IncidentsDTO incidentsDTO = new IncidentsDTO();

        incidentsDTO.setIncidentId( source.getIncidentId() );
        incidentsDTO.setCaseNumber( source.getCaseNumber() );
        incidentsDTO.setTitle( source.getTitle() );
        incidentsDTO.setDescription( source.getDescription() );
        incidentsDTO.setBirthNumber( source.getBirthNumber() );
        incidentsDTO.setIncidentType( source.getIncidentType() );
        incidentsDTO.setIncidentSubject( source.getIncidentSubject() );
        incidentsDTO.setCurrentStatus( source.getCurrentStatus() );
        incidentsDTO.setAccidentDate( source.getAccidentDate() );
        incidentsDTO.setReportDate( source.getReportDate() );
        incidentsDTO.setTodaysDate( source.getTodaysDate() );
        incidentsDTO.setClosureDate( source.getClosureDate() );
        List<IncidentCommentsEntity> list = source.getComments();
        if ( list != null ) {
            incidentsDTO.setComments( new ArrayList<IncidentCommentsEntity>( list ) );
        }
        incidentsDTO.setIncidentResolution( source.getIncidentResolution() );

        return incidentsDTO;
    }

    @Override
    public void updateNewsDTO(IncidentsDTO source, IncidentsDTO target) {
        if ( source == null ) {
            return;
        }

        target.setIncidentId( source.getIncidentId() );
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
        if ( target.getComments() != null ) {
            List<IncidentCommentsEntity> list = source.getComments();
            if ( list != null ) {
                target.getComments().clear();
                target.getComments().addAll( list );
            }
            else {
                target.setComments( null );
            }
        }
        else {
            List<IncidentCommentsEntity> list = source.getComments();
            if ( list != null ) {
                target.setComments( new ArrayList<IncidentCommentsEntity>( list ) );
            }
        }
        target.setIncidentResolution( source.getIncidentResolution() );
    }

    @Override
    public void updateNewsEntity(IncidentsDTO source, IncidentsEntity target) {
        if ( source == null ) {
            return;
        }

        target.setIncidentId( source.getIncidentId() );
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
        if ( target.getComments() != null ) {
            List<IncidentCommentsEntity> list = source.getComments();
            if ( list != null ) {
                target.getComments().clear();
                target.getComments().addAll( list );
            }
            else {
                target.setComments( null );
            }
        }
        else {
            List<IncidentCommentsEntity> list = source.getComments();
            if ( list != null ) {
                target.setComments( new ArrayList<IncidentCommentsEntity>( list ) );
            }
        }
        target.setIncidentResolution( source.getIncidentResolution() );
    }
}
