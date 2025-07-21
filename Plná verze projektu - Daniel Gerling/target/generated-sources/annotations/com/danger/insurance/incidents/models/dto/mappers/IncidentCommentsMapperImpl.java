package com.danger.insurance.incidents.models.dto.mappers;

import com.danger.insurance.incidents.data.entities.IncidentCommentsEntity;
import com.danger.insurance.incidents.models.dto.IncidentCommentsDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentCommentsCreatePostDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsClosePostDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-21T08:28:48+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class IncidentCommentsMapperImpl implements IncidentCommentsMapper {

    @Override
    public IncidentCommentsEntity toEntity(IncidentCommentsDTO source) {
        if ( source == null ) {
            return null;
        }

        IncidentCommentsEntity incidentCommentsEntity = new IncidentCommentsEntity();

        incidentCommentsEntity.setIncidentCommentId( source.getIncidentCommentId() );
        incidentCommentsEntity.setTitle( source.getTitle() );
        incidentCommentsEntity.setDescription( source.getDescription() );
        incidentCommentsEntity.setCommentDate( source.getCommentDate() );
        incidentCommentsEntity.setIncidentsEntity( source.getIncidentsEntity() );

        return incidentCommentsEntity;
    }

    @Override
    public IncidentCommentsEntity newsCreateToEntity(IncidentCommentsDTO source) {
        if ( source == null ) {
            return null;
        }

        IncidentCommentsEntity incidentCommentsEntity = new IncidentCommentsEntity();

        incidentCommentsEntity.setIncidentCommentId( source.getIncidentCommentId() );
        incidentCommentsEntity.setTitle( source.getTitle() );
        incidentCommentsEntity.setDescription( source.getDescription() );
        incidentCommentsEntity.setCommentDate( source.getCommentDate() );
        incidentCommentsEntity.setIncidentsEntity( source.getIncidentsEntity() );

        return incidentCommentsEntity;
    }

    @Override
    public IncidentCommentsDTO mergeToIncidentCommentsDTO(IncidentCommentsDTO incidentCommentsDTO, IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO) {
        if ( incidentCommentsCreatePostDTO == null ) {
            return incidentCommentsDTO;
        }

        incidentCommentsDTO.setTitle( incidentCommentsCreatePostDTO.getTitle() );
        incidentCommentsDTO.setDescription( incidentCommentsCreatePostDTO.getDescription() );
        incidentCommentsDTO.setCommentDate( incidentCommentsCreatePostDTO.getCommentDate() );

        return incidentCommentsDTO;
    }

    @Override
    public IncidentCommentsDTO splitToIncidentCommentsDTO(IncidentCommentsDTO incidentCommentsDTO, IncidentsClosePostDTO incidentsClosePostDTO) {
        if ( incidentsClosePostDTO == null ) {
            return incidentCommentsDTO;
        }

        incidentCommentsDTO.setTitle( incidentsClosePostDTO.getTitle() );
        incidentCommentsDTO.setDescription( incidentsClosePostDTO.getDescription() );
        incidentCommentsDTO.setCommentDate( incidentsClosePostDTO.getCommentDate() );

        return incidentCommentsDTO;
    }

    @Override
    public IncidentCommentsDTO toDTO(IncidentCommentsEntity source) {
        if ( source == null ) {
            return null;
        }

        IncidentCommentsDTO incidentCommentsDTO = new IncidentCommentsDTO();

        incidentCommentsDTO.setIncidentCommentId( source.getIncidentCommentId() );
        incidentCommentsDTO.setTitle( source.getTitle() );
        incidentCommentsDTO.setDescription( source.getDescription() );
        incidentCommentsDTO.setCommentDate( source.getCommentDate() );
        incidentCommentsDTO.setIncidentsEntity( source.getIncidentsEntity() );

        return incidentCommentsDTO;
    }

    @Override
    public void updateNewsDTO(IncidentCommentsDTO source, IncidentCommentsDTO target) {
        if ( source == null ) {
            return;
        }

        target.setIncidentCommentId( source.getIncidentCommentId() );
        target.setTitle( source.getTitle() );
        target.setDescription( source.getDescription() );
        target.setCommentDate( source.getCommentDate() );
        target.setIncidentsEntity( source.getIncidentsEntity() );
    }

    @Override
    public void updateNewsEntity(IncidentCommentsDTO source, IncidentCommentsEntity target) {
        if ( source == null ) {
            return;
        }

        target.setIncidentCommentId( source.getIncidentCommentId() );
        target.setTitle( source.getTitle() );
        target.setDescription( source.getDescription() );
        target.setCommentDate( source.getCommentDate() );
        target.setIncidentsEntity( source.getIncidentsEntity() );
    }
}
