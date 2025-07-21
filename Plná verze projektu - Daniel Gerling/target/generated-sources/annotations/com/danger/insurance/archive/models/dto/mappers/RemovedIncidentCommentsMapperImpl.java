package com.danger.insurance.archive.models.dto.mappers;

import com.danger.insurance.archive.data.entities.RemovedIncidentCommentsEntity;
import com.danger.insurance.archive.models.dto.RemovedIncidentCommentsDTO;
import com.danger.insurance.incidents.models.dto.IncidentCommentsDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-21T08:28:48+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class RemovedIncidentCommentsMapperImpl implements RemovedIncidentCommentsMapper {

    @Override
    public RemovedIncidentCommentsEntity toEntity(RemovedIncidentCommentsDTO source) {
        if ( source == null ) {
            return null;
        }

        RemovedIncidentCommentsEntity removedIncidentCommentsEntity = new RemovedIncidentCommentsEntity();

        removedIncidentCommentsEntity.setRemovedIncidentCommentId( source.getRemovedIncidentCommentId() );
        removedIncidentCommentsEntity.setTitle( source.getTitle() );
        removedIncidentCommentsEntity.setDescription( source.getDescription() );
        removedIncidentCommentsEntity.setCommentDate( source.getCommentDate() );
        removedIncidentCommentsEntity.setRemovedIncidentEntity( source.getRemovedIncidentEntity() );

        return removedIncidentCommentsEntity;
    }

    @Override
    public RemovedIncidentCommentsEntity removedIncidentCommentsToEntity(RemovedIncidentCommentsDTO source) {
        if ( source == null ) {
            return null;
        }

        RemovedIncidentCommentsEntity removedIncidentCommentsEntity = new RemovedIncidentCommentsEntity();

        removedIncidentCommentsEntity.setRemovedIncidentCommentId( source.getRemovedIncidentCommentId() );
        removedIncidentCommentsEntity.setTitle( source.getTitle() );
        removedIncidentCommentsEntity.setDescription( source.getDescription() );
        removedIncidentCommentsEntity.setCommentDate( source.getCommentDate() );
        removedIncidentCommentsEntity.setRemovedIncidentEntity( source.getRemovedIncidentEntity() );

        return removedIncidentCommentsEntity;
    }

    @Override
    public RemovedIncidentCommentsDTO toDTO(RemovedIncidentCommentsEntity source) {
        if ( source == null ) {
            return null;
        }

        RemovedIncidentCommentsDTO removedIncidentCommentsDTO = new RemovedIncidentCommentsDTO();

        removedIncidentCommentsDTO.setRemovedIncidentCommentId( source.getRemovedIncidentCommentId() );
        removedIncidentCommentsDTO.setTitle( source.getTitle() );
        removedIncidentCommentsDTO.setDescription( source.getDescription() );
        removedIncidentCommentsDTO.setCommentDate( source.getCommentDate() );
        removedIncidentCommentsDTO.setRemovedIncidentEntity( source.getRemovedIncidentEntity() );

        return removedIncidentCommentsDTO;
    }

    @Override
    public RemovedIncidentCommentsDTO mergeToRemovedIncidentCommentsDTO(RemovedIncidentCommentsDTO removedIncidentCommentsDTO, IncidentCommentsDTO incidentCommentsDTO) {
        if ( incidentCommentsDTO == null ) {
            return removedIncidentCommentsDTO;
        }

        removedIncidentCommentsDTO.setTitle( incidentCommentsDTO.getTitle() );
        removedIncidentCommentsDTO.setDescription( incidentCommentsDTO.getDescription() );
        removedIncidentCommentsDTO.setCommentDate( incidentCommentsDTO.getCommentDate() );

        return removedIncidentCommentsDTO;
    }

    @Override
    public void updateRemovedIncidentCommentsDTO(RemovedIncidentCommentsDTO source, RemovedIncidentCommentsDTO target) {
        if ( source == null ) {
            return;
        }

        target.setRemovedIncidentCommentId( source.getRemovedIncidentCommentId() );
        target.setTitle( source.getTitle() );
        target.setDescription( source.getDescription() );
        target.setCommentDate( source.getCommentDate() );
        target.setRemovedIncidentEntity( source.getRemovedIncidentEntity() );
    }

    @Override
    public void updateRemovedIncidentCommentsEntity(RemovedIncidentCommentsDTO source, RemovedIncidentCommentsEntity target) {
        if ( source == null ) {
            return;
        }

        target.setRemovedIncidentCommentId( source.getRemovedIncidentCommentId() );
        target.setTitle( source.getTitle() );
        target.setDescription( source.getDescription() );
        target.setCommentDate( source.getCommentDate() );
        target.setRemovedIncidentEntity( source.getRemovedIncidentEntity() );
    }
}
